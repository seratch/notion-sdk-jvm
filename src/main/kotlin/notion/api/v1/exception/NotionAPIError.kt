package notion.api.v1.exception

import notion.api.v1.http.NotionHttpResponse
import notion.api.v1.response.Error

class NotionAPIError(
    val error: Error,
    val httpResponse: NotionHttpResponse,
    override val message: String? = "Got an error from Notion (status: ${error.status}, code: ${error.code}, message: ${error.message})",
    override val cause: Throwable? = null
) : RuntimeException(message, cause)