package notion.api.v1.http.impl

import notion.api.v1.Utilities.urlEncode
import notion.api.v1.http.NotionHttpClient
import notion.api.v1.http.NotionHttpResponse
import notion.api.v1.logging.NotionLogger
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class JavaNotionHttpClient : NotionHttpClient {

    override fun get(
        logger: NotionLogger,
        url: String,
        query: Map<String, String>,
        headers: Map<String, String>
    ): NotionHttpResponse {
        val q = query.map { "${urlEncode(it.key)}=${urlEncode(it.value)}" }.joinToString(prefix = "?", separator = "&")
        val fullUrl = url + if (q != "?") q else ""
        logger.debug("Sending a request - GET $fullUrl")
        val conn = URL(fullUrl).openConnection() as HttpURLConnection
        conn.setRequestProperty("Connection", "close")
        try {
            conn.requestMethod = "GET"
            // TODO: configurable
            conn.connectTimeout = 1_000
            conn.readTimeout = 10_000
            headers.forEach { (name, value) -> conn.setRequestProperty(name, value) }
            var input: InputStream = try {
                conn.connect()
                conn.inputStream
            } catch (e: IOException) {
                conn.errorStream
            }
            input.use {
                val response = NotionHttpResponse(
                    status = conn.responseCode,
                    body = input.bufferedReader(Charsets.UTF_8).use { it.readText() },
                    headers = conn.headerFields
                )
                logger.debug("Received a response (status: ${response.status}, body: ${response.body})")
                return response
            }
        } finally {
            try {
                conn.disconnect()
            } catch (e: Exception) {
                logger.info("Failed to disconnect from Notion: ${e.message}", e)
            }
        }
    }

    override fun postTextBody(
        logger: NotionLogger,
        url: String,
        query: Map<String, String>,
        body: String,
        headers: Map<String, String>
    ): NotionHttpResponse {
        val q = query.map { "${urlEncode(it.key)}=${urlEncode(it.value)}" }.joinToString(prefix = "?", separator = "&")
        val fullUrl = url + if (q != "?") q else ""
        logger.debug("Sending a request - POST $fullUrl with $body")
        val conn = URL(fullUrl).openConnection() as HttpURLConnection
        conn.setRequestProperty("Connection", "close")
        try {
            conn.requestMethod = "POST"
            // TODO: configurable
            conn.connectTimeout = 1_000
            conn.readTimeout = 10_000
            headers.forEach { (name, value) -> conn.setRequestProperty(name, value) }

            conn.doOutput = true
            conn.outputStream.use { out -> out.write(body.toByteArray(Charsets.UTF_8)) }

            (try {
                conn.connect()
                conn.inputStream
            } catch (e: IOException) {
                conn.errorStream
            }).use { input ->
                val response = NotionHttpResponse(
                    status = conn.responseCode,
                    body = input.bufferedReader(Charsets.UTF_8).use { it.readText() },
                    headers = conn.headerFields
                )
                logger.debug("Received a response (status: ${response.status}, body: ${response.body})")
                return response
            }
        } finally {
            try {
                conn.disconnect()
            } catch (e: Exception) {
                logger.info("Failed to disconnect from Notion: ${e.message}", e)
            }
        }
    }
}