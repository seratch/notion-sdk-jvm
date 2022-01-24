package notion.api.v1.exception

import notion.api.v1.http.NotionHttpResponse
import notion.api.v1.model.error.OAuthError

class NotionOAuthAPIError
@JvmOverloads
constructor(
    val error: OAuthError,
    val httpResponse: NotionHttpResponse,
    override val message: String = buildMessage(httpResponse, error),
    override val cause: Throwable? = null
) : RuntimeException(message, cause) {

  companion object {
    private fun buildMessage(response: NotionHttpResponse, error: OAuthError) =
        "Got an error from Notion (status: ${response.status}, error: ${error.error})"
  }
}
