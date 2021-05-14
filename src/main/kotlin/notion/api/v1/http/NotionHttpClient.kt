package notion.api.v1.http

import notion.api.v1.logging.NotionLogger

interface NotionHttpClient {
    fun get(
        logger: NotionLogger,
        url: String,
        query: Map<String, String> = emptyMap(),
        headers: Map<String, String>,
    ): NotionHttpResponse

    fun postTextBody(
        logger: NotionLogger,
        url: String,
        query: Map<String, String> = emptyMap(),
        body: String,
        headers: Map<String, String>
    ): NotionHttpResponse
}
