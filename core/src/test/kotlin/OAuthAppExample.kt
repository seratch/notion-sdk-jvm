import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import java.net.URLEncoder
import java.util.*
import notion.api.v1.NotionClient
import notion.api.v1.exception.NotionOAuthAPIError
import org.slf4j.LoggerFactory

class StateManager {
  private val values: MutableMap<String, Long> = mutableMapOf()
  fun generate(): String {
    val state = UUID.randomUUID().toString()
    values[state] = Date().time
    return state
  }

  fun consume(state: String, cookies: RequestCookies): Boolean {
    val createdAt = values.remove(state)
    if (createdAt == null || Date().time >= (createdAt + 10 * 60 * 1000)) {
      return false
    }
    val sessionValue = cookies["notion-installation-session"]
    if (sessionValue == null || sessionValue != state) {
      return false
    }
    return true
  }
}

fun urlEncode(v: String?): String = URLEncoder.encode(v, Charsets.UTF_8)

fun main() {
  val logger = LoggerFactory.getLogger("main")
  val client =
      NotionClient(
          clientId = System.getenv("NOTION_CLIENT_ID"),
          clientSecret = System.getenv("NOTION_CLIENT_SECRET"),
          redirectUri = System.getenv("NOTION_REDIRECT_URI"),
      )
  val stateManager = StateManager()
  client.use {
    val server =
        embeddedServer(Netty, port = 3000) {
          routing {
            get("/notion/install") {
              try {
                val state = stateManager.generate()
                val authorizeUrl =
                    "https://api.notion.com/v1/oauth/authorize?owner=user" +
                        "&client_id=${client.clientId}" +
                        "&redirect_uri=${urlEncode(client.redirectUri)}" +
                        "&state=$state" +
                        "&response_type=code"
                call.response.header("Location", authorizeUrl)
                call.response.cookies.append(
                    name = "notion-installation-session",
                    value = state,
                    httpOnly = true,
                    path = "/")
                call.respond(HttpStatusCode.TemporaryRedirect)
              } catch (e: Exception) {
                logger.error(e.message, e)
                call.respond(
                    TextContent(
                        "Error: $e",
                        ContentType.parse("text/html; charset=utf-8"),
                        HttpStatusCode.InternalServerError,
                    ))
              }
            }
            get("/notion/callback") {
              val code = call.request.queryParameters["code"]
              val state = call.request.queryParameters["state"]
              if (code != null && state != null) {
                if (stateManager.consume(state, call.request.cookies)) {
                  call.response.cookies.appendExpired(
                      name = "notion-installation-session", path = "/")
                  try {
                    val result = client.exchangeAuthCode(code = code, state = state)
                    // TODO: Save the token value in your database
                    logger.info("token API response: $result")
                    call.respond(
                        TextContent(
                            "OK!",
                            ContentType.parse("text/html; charset=utf-8"),
                            HttpStatusCode.OK,
                        ))
                  } catch (e: NotionOAuthAPIError) {
                    call.respond(
                        TextContent(
                            "Error: $e",
                            ContentType.parse("text/html; charset=utf-8"),
                            HttpStatusCode(e.httpResponse.status, ""),
                        ))
                  }
                } else {
                  call.respond(
                      TextContent(
                          "Error: the state value is no longer vaild",
                          ContentType.parse("text/html; charset=utf-8"),
                          HttpStatusCode.BadRequest,
                      ))
                }
              } else {
                call.respond(
                    TextContent(
                        "Error: invalid redirection",
                        ContentType.parse("text/html; charset=utf-8"),
                        HttpStatusCode.BadRequest,
                    ))
              }
            }
          }
        }
    server.start(wait = true)
  }
}
