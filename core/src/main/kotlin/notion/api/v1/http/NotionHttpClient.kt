package notion.api.v1.http

import notion.api.v1.logging.NotionLogger
import java.io.Closeable
import java.net.URLEncoder

interface NotionHttpClient : AutoCloseable, Closeable {

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

    fun patchTextBody(
        logger: NotionLogger,
        url: String,
        query: Map<String, String> = emptyMap(),
        body: String,
        headers: Map<String, String>
    ): NotionHttpResponse

    override fun close() {
    }

    // ------------------------------

    fun urlEncode(value: String): String = URLEncoder.encode(value, "UTF-8")

    fun buildQueryString(query: Map<String, String>) =
        query.map { "${urlEncode(it.key)}=${urlEncode(it.value)}" }.joinToString(prefix = "?", separator = "&")

    fun buildFullUrl(url: String, q: String) = url + if (q != "?") q else ""

}
