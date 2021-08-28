package notion.api.v1.exception

import notion.api.v1.http.NotionHttpResponse
import notion.api.v1.model.error.Error

class NotionAPIError
@JvmOverloads
constructor(
    val error: Error,
    val httpResponse: NotionHttpResponse,
    override val message: String = buildMessage(error),
    override val cause: Throwable? = null
) : RuntimeException(message, cause) {

  companion object {
    private fun buildMessage(error: Error) =
        "Got an error from Notion (status: ${error.status}, code: ${error.code}, message: ${error.message})"
  }
}
