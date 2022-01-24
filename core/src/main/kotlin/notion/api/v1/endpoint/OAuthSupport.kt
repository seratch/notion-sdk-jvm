package notion.api.v1.endpoint

import java.util.*
import notion.api.v1.exception.NotionOAuthAPIError
import notion.api.v1.http.NotionHttpClient
import notion.api.v1.json.NotionJsonSerializer
import notion.api.v1.logging.NotionLogger
import notion.api.v1.model.oauth.OAuthTokenResult
import notion.api.v1.request.oauth.ExchangeAuthCodeRequest

interface OAuthSupport : EndpointsSupport {
  val clientId: String?
  val clientSecret: String?
  val redirectUri: String?
  val httpClient: NotionHttpClient
  val jsonSerializer: NotionJsonSerializer
  val logger: NotionLogger
  val baseUrl: String

  fun exchangeAuthCode(code: String, state: String): OAuthTokenResult {
    return exchangeAuthCode(
        ExchangeAuthCodeRequest(
            code = code,
            state = state,
            redirectUri = this.redirectUri!!,
        ))
  }

  fun exchangeAuthCode(request: ExchangeAuthCodeRequest): OAuthTokenResult {
    if (this.redirectUri == null) {
      throw IllegalStateException("Setting redirectUri to NotionClient is required")
    }
    val base64Value =
        String(
            Base64.getEncoder()
                .encode("${this.clientId}:${this.clientSecret}".toByteArray(Charsets.UTF_8)),
            Charsets.UTF_8)
    val httpResponse =
        httpClient.postTextBody(
            logger = logger,
            url = "$baseUrl/oauth/token",
            body = jsonSerializer.toJsonString(request),
            headers =
                buildRequestHeaders(
                    contentTypeJson().plus("Authorization" to "Basic $base64Value")))
    if (httpResponse.status == 200) {
      return jsonSerializer.toOAuthTokenResult(httpResponse.body)
    } else {
      throw NotionOAuthAPIError(
          error = jsonSerializer.toOAuthError(httpResponse.body),
          httpResponse = httpResponse,
      )
    }
  }
}
