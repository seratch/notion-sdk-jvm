package notion.api.v1.http

import java.io.Closeable
import java.net.URLEncoder
import notion.api.v1.logging.NotionLogger

interface NotionHttpClient : AutoCloseable, Closeable {

  fun get(
      logger: NotionLogger,
      url: String,
      query: Map<String, List<String>> = emptyMap(),
      headers: Map<String, String>,
  ): NotionHttpResponse

  fun postTextBody(
      logger: NotionLogger,
      url: String,
      query: Map<String, List<String>> = emptyMap(),
      body: String,
      headers: Map<String, String>
  ): NotionHttpResponse

  fun patchTextBody(
      logger: NotionLogger,
      url: String,
      query: Map<String, List<String>> = emptyMap(),
      body: String,
      headers: Map<String, String>
  ): NotionHttpResponse

  fun delete(
      logger: NotionLogger,
      url: String,
      query: Map<String, List<String>> = emptyMap(),
      headers: Map<String, String>,
  ): NotionHttpResponse

  override fun close() {}

  // ------------------------------

  fun urlEncode(value: String): String = URLEncoder.encode(value, "UTF-8")

  fun buildQueryString(query: Map<String, List<String>>) =
      query
          .map { params ->
            params.value.joinToString(separator = "&") {
              "${urlEncode(params.key)}=${urlEncode(it)}"
            }
          }
          .joinToString(prefix = "?", separator = "&")

  fun buildFullUrl(url: String, q: String) = url + if (q != "?") q else ""

  fun debugLogStart(
      logger: NotionLogger,
      method: String,
      fullUrl: String,
      body: String?,
  ) {
    if (logger.isDebugEnabled()) {
      val b = if (body == null || body.isEmpty()) "" else "body   $body\n"
      logger.debug("""Sending a request:
$method $fullUrl
$b
""".trimIndent().trimMargin())
    }
  }

  fun debugLogSuccess(logger: NotionLogger, startTimeMillis: Long, response: NotionHttpResponse) {
    if (logger.isDebugEnabled()) {
      val responseTimeMillis = System.currentTimeMillis() - startTimeMillis
      logger.debug(
          """Received a response ($responseTimeMillis millis):
status  ${response.status}
body    ${response.body}
"""
              .trimIndent()
              .trimMargin())
    }
  }

  fun warnLogFailure(logger: NotionLogger, e: Exception) {
    logger.warn("Failed to disconnect from Notion: ${e.message}", e)
  }
}
