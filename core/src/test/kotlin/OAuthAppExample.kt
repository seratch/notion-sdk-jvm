import notion.api.v1.NotionClient
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import notion.api.v1.exception.NotionOAuthAPIError
import org.slf4j.LoggerFactory
import java.net.URLEncoder

fun urlEncode(v: String?): String = URLEncoder.encode(v, Charsets.UTF_8)

fun main() {
    val logger = LoggerFactory.getLogger("main")
    val client = NotionClient(
        clientId = System.getenv("NOTION_CLIENT_ID"),
        clientSecret = System.getenv("NOTION_CLIENT_SECRET"),
        redirectUri = System.getenv("NOTION_REDIRECT_URI"),
    )
    client.use {
        val server = embeddedServer(Netty, port = 3000) {
            routing {
                get("/notion/install") {
                    val authorizeUrl = "https://api.notion.com/v1/oauth/authorize?owner=user" +
                            "&client_id=${client.clientId}" +
                            "&redirect_uri=${urlEncode(client.redirectUri)}" +
                            "&response_type=code"
                    call.response.header("Location", authorizeUrl)
                    call.respond(HttpStatusCode.TemporaryRedirect)
                }
                get("/notion/callback") {
                    val code = call.request.queryParameters["code"]
                    val state = call.request.queryParameters["state"]
                    if (code != null && state != null) {
                        try {
                            val result = client.exchangeAuthCode(code = code, state = state)
                            logger.info("token API response: $result")
                            call.respond(TextContent(
                                "OK!",
                                ContentType.parse("text/html; charset=utf-8"),
                                HttpStatusCode.OK,
                            ))
                        } catch (e: NotionOAuthAPIError) {
                            call.respond(TextContent(
                                "Error: $e",
                                ContentType.parse("text/html; charset=utf-8"),
                                HttpStatusCode(e.httpResponse.status, ""),
                            ))
                        }
                    } else {
                        call.respond(TextContent(
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
