package notion.api.v1.endpoint

import notion.api.v1.exception.NotionAPIError
import notion.api.v1.http.NotionHttpClient
import notion.api.v1.json.NotionJsonSerializer
import notion.api.v1.logging.NotionLogger
import notion.api.v1.model.page.Page
import notion.api.v1.request.CreatePageRequest
import notion.api.v1.request.RetrievePageRequest
import notion.api.v1.request.UpdatePagePropertiesRequest

interface PagesSupport : EndpointsSupport {
    val httpClient: NotionHttpClient
    val jsonSerializer: NotionJsonSerializer
    val logger: NotionLogger
    val baseUrl: String

    // -----------------------------------------------
    // createPage
    // -----------------------------------------------

    fun createPage(page: CreatePageRequest): Page {
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

    // -----------------------------------------------
    // retrievePage
    // -----------------------------------------------

    fun retrievePage(pageId: String): Page {
        return retrievePage(RetrievePageRequest(pageId))
    }

    fun retrievePage(request: RetrievePageRequest): Page {
        val httpResponse = httpClient.get(
            logger = logger,
            url = "$baseUrl/pages/${urlEncode(request.pageId)}",
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

    // -----------------------------------------------
    // updatePageProperties
    // -----------------------------------------------

    fun updatePageProperties(request: UpdatePagePropertiesRequest): Page {
        val httpResponse = httpClient.patchTextBody(
            logger = logger,
            url = "$baseUrl/pages/${request.pageId}",
            body = jsonSerializer.toJsonString(request),
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

}