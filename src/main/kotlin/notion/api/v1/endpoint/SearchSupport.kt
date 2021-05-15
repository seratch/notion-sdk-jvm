package notion.api.v1.endpoint

import notion.api.v1.exception.NotionAPIError
import notion.api.v1.http.NotionHttpClient
import notion.api.v1.json.NotionJsonSerializer
import notion.api.v1.logging.NotionLogger
import notion.api.v1.model.search.SearchResults
import notion.api.v1.request.SearchRequest

interface SearchSupport : EndpointsSupport {
    val httpClient: NotionHttpClient
    val jsonSerializer: NotionJsonSerializer
    val logger: NotionLogger
    val baseUrl: String

    // -----------------------------------------------
    // search
    // -----------------------------------------------

    fun search(query: String): SearchResults {
        return search(SearchRequest(query = query))
    }

    fun search(request: SearchRequest): SearchResults {
        val httpResponse = httpClient.postTextBody(
            logger = logger,
            url = "$baseUrl/search",
            body = jsonSerializer.toJsonString(request),
            headers = buildRequestHeaders(
                mapOf(
                    "Content-Type" to "application/json"
                )
            )
        )
        if (httpResponse.status == 200) {
            return jsonSerializer.toSearchResults(httpResponse.body)
        } else {
            throw NotionAPIError(
                error = jsonSerializer.toError(httpResponse.body),
                httpResponse = httpResponse,
            )
        }
    }
}