package notion.api.v1.endpoint

import notion.api.v1.exception.NotionAPIError
import notion.api.v1.http.NotionHttpClient
import notion.api.v1.json.NotionJsonSerializer
import notion.api.v1.logging.NotionLogger
import notion.api.v1.model.page.Page
import notion.api.v1.request.NewPageRequest

interface PagesSupport : EndpointsSupport {
    val httpClient: NotionHttpClient
    val jsonSerializer: NotionJsonSerializer
    val logger: NotionLogger
    val baseUrl: String

    fun createPage(page: NewPageRequest): Page {
        val httpResponse = httpClient.postTextBody(
            logger = logger,
            url = "$baseUrl/pages",
            body = jsonSerializer.toJsonString(page),
            headers = buildRequestHeaders(
                mapOf(
                    "Content-Type" to "application/json"
                )
            )
        )
        if (httpResponse.status == 200) {
            return jsonSerializer.toPage(httpResponse.body)
        } else {
            throw NotionAPIError(
                error = jsonSerializer.toError(httpResponse.body),
                httpResponse = httpResponse,
            )
        }
    }

    fun retrievePage(pageId: String): Page {
        val httpResponse = httpClient.get(
            logger = logger,
            url = "$baseUrl/pages/${urlEncode(pageId)}",
            headers = buildRequestHeaders(emptyMap())
        )
        if (httpResponse.status == 200) {
            return jsonSerializer.toPage(httpResponse.body)
        } else {
            throw NotionAPIError(
                error = jsonSerializer.toError(httpResponse.body),
                httpResponse = httpResponse,
            )
        }
    }
    // TODO: update page properties

}