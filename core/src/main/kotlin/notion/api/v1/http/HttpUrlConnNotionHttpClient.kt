package notion.api.v1.http

import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import notion.api.v1.http.HttpUrlConnPatchMethodWorkaround.setPatchRequestMethod
import notion.api.v1.logging.NotionLogger

// TODO: proxy support
class HttpUrlConnNotionHttpClient
@JvmOverloads
constructor(
    private val connectTimeoutMillis: Int = 3_000,
    private val readTimeoutMillis: Int = 30_000,
) : NotionHttpClient {

  override fun get(
      logger: NotionLogger,
      url: String,
      query: Map<String, List<String>>,
      headers: Map<String, String>
  ): NotionHttpResponse {
    val startTimeMillis = System.currentTimeMillis()
    val q = buildQueryString(query)
    val fullUrl = buildFullUrl(url, q)
    val conn = buildConnectionObject(fullUrl, headers)
    try {
      conn.requestMethod = "GET"
      debugLogStart(logger, conn.requestMethod, fullUrl, null)
      connect(conn).use { input ->
        val response =
            NotionHttpResponse(
                status = conn.responseCode,
                body = readResponseBody(input),
                headers = conn.headerFields)
        debugLogSuccess(logger, startTimeMillis, response)
        return response
      }
    } finally {
      disconnect(conn, logger)
    }
  }

  override fun postTextBody(
      logger: NotionLogger,
      url: String,
      query: Map<String, List<String>>,
      body: String,
      headers: Map<String, String>
  ): NotionHttpResponse {
    val startTimeMillis = System.currentTimeMillis()
    val q = buildQueryString(query)
    val fullUrl = buildFullUrl(url, q)
    val conn = buildConnectionObject(fullUrl, headers)
    try {
      conn.requestMethod = "POST"
      debugLogStart(logger, conn.requestMethod, fullUrl, body)
      setRequestBody(conn, body)
      connect(conn).use { input ->
        val response =
            NotionHttpResponse(
                status = conn.responseCode,
                body = readResponseBody(input),
                headers = conn.headerFields)
        debugLogSuccess(logger, startTimeMillis, response)
        return response
      }
    } finally {
      disconnect(conn, logger)
    }
  }

  override fun patchTextBody(
      logger: NotionLogger,
      url: String,
      query: Map<String, List<String>>,
      body: String,
      headers: Map<String, String>
  ): NotionHttpResponse {
    val startTimeMillis = System.currentTimeMillis()
    val q = buildQueryString(query)
    val fullUrl = buildFullUrl(url, q)
    val conn = buildConnectionObject(fullUrl, headers)
    try {
      setPatchRequestMethod(conn)
      debugLogStart(logger, conn.requestMethod, fullUrl, body)
      setRequestBody(conn, body)
      connect(conn).use { input ->
        val response =
            NotionHttpResponse(
                status = conn.responseCode,
                body = readResponseBody(input),
                headers = conn.headerFields)
        debugLogSuccess(logger, startTimeMillis, response)
        return response
      }
    } finally {
      disconnect(conn, logger)
    }
  }

  override fun delete(
      logger: NotionLogger,
      url: String,
      query: Map<String, List<String>>,
      headers: Map<String, String>
  ): NotionHttpResponse {
    val startTimeMillis = System.currentTimeMillis()
    val q = buildQueryString(query)
    val fullUrl = buildFullUrl(url, q)
    val conn = buildConnectionObject(fullUrl, headers)
    try {
      conn.requestMethod = "DELETE"
      debugLogStart(logger, conn.requestMethod, fullUrl, null)
      connect(conn).use { input ->
        val response =
            NotionHttpResponse(
                status = conn.responseCode,
                body = readResponseBody(input),
                headers = conn.headerFields)
        debugLogSuccess(logger, startTimeMillis, response)
        return response
      }
    } finally {
      disconnect(conn, logger)
    }
  }

  // -----------------------------------------------

  private fun buildConnectionObject(
      fullUrl: String,
      headers: Map<String, String>
  ): HttpURLConnection {
    val conn = URL(fullUrl).openConnection() as HttpURLConnection
    conn.setRequestProperty("Connection", "close")
    conn.connectTimeout = connectTimeoutMillis
    conn.readTimeout = readTimeoutMillis
    headers.forEach { (name, value) -> conn.setRequestProperty(name, value) }
    return conn
  }

  private fun setRequestBody(conn: HttpURLConnection, body: String) {
    conn.doOutput = true
    conn.outputStream.use { out -> out.write(body.toByteArray(Charsets.UTF_8)) }
  }

  private fun connect(conn: HttpURLConnection): InputStream =
      try {
        conn.connect()
        conn.inputStream
      } catch (e: IOException) {
        conn.errorStream
      }

  private fun readResponseBody(input: InputStream?): String {
    return input?.bufferedReader(Charsets.UTF_8).use { it?.readText() } ?: ""
  }

  private fun disconnect(conn: HttpURLConnection, logger: NotionLogger) {
    try {
      conn.disconnect()
    } catch (e: Exception) {
      warnLogFailure(logger, e)
    }
  }
}
