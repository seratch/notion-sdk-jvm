package notion.api.v1.http

import notion.api.v1.logging.NotionLogger
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration

// TODO: proxy support
class JavaNetHttpClient(
    connectTimeoutMillis: Int = 1_000,
    private val readTimeoutMillis: Int = 10_000,
) : NotionHttpClient {
    private val client: HttpClient = HttpClient.newBuilder()
        .connectTimeout(Duration.ofMillis(connectTimeoutMillis.toLong()))
        .build()

    override fun get(
        logger: NotionLogger,
        url: String,
        query: Map<String, String>,
        headers: Map<String, String>
    ): NotionHttpResponse {
        val fullUrl = buildFullUrl(url, buildQueryString(query))
        val req = HttpRequest.newBuilder()
            .GET()
            .uri(URI(fullUrl))
            .timeout(Duration.ofMillis(readTimeoutMillis.toLong()))
        headers.forEach { (name, value) -> req.header(name, value) }
        val request = req.build()
        debugLogStart(logger, request, fullUrl, "")
        try {
            val resp = client.send(request, HttpResponse.BodyHandlers.ofString())
            val response = NotionHttpResponse(
                status = resp.statusCode(),
                headers = resp.headers().map(),
                body = resp.body()
            )
            debugLogSuccess(logger, response)
            return response
        } catch (e: Exception) {
            debugLogFailure(logger, e)
            throw e
        }
    }

    override fun postTextBody(
        logger: NotionLogger,
        url: String,
        query: Map<String, String>,
        body: String,
        headers: Map<String, String>
    ): NotionHttpResponse {
        val fullUrl = buildFullUrl(url, buildQueryString(query))
        val req = HttpRequest.newBuilder()
            .POST(HttpRequest.BodyPublishers.ofString(body, Charsets.UTF_8))
            .uri(URI(fullUrl))
            .timeout(Duration.ofMillis(readTimeoutMillis.toLong()))
        headers.forEach { (name, value) -> req.header(name, value) }
        val request = req.build()
        debugLogStart(logger, request, fullUrl, body)
        try {
            val resp = client.send(request, HttpResponse.BodyHandlers.ofString())
            val response = NotionHttpResponse(
                status = resp.statusCode(),
                headers = resp.headers().map(),
                body = resp.body()
            )
            debugLogSuccess(logger, response)
            return response
        } catch (e: Exception) {
            debugLogFailure(logger, e)
            throw e
        }
    }

    override fun patchTextBody(
        logger: NotionLogger,
        url: String,
        query: Map<String, String>,
        body: String,
        headers: Map<String, String>
    ): NotionHttpResponse {
        val fullUrl = buildFullUrl(url, buildQueryString(query))
        val req = HttpRequest.newBuilder()
            .method("PATCH", HttpRequest.BodyPublishers.ofString(body, Charsets.UTF_8))
            .uri(URI(buildFullUrl(url, buildQueryString(query))))
            .timeout(Duration.ofMillis(readTimeoutMillis.toLong()))
        headers.forEach { (name, value) -> req.header(name, value) }
        val request = req.build()
        debugLogStart(logger, request, fullUrl, body)
        try {
            val resp = client.send(request, HttpResponse.BodyHandlers.ofString())
            val response = NotionHttpResponse(
                status = resp.statusCode(),
                headers = resp.headers().map(),
                body = resp.body()
            )
            debugLogSuccess(logger, response)
            return response
        } catch (e: Exception) {
            debugLogFailure(logger, e)
            throw e
        }
    }

    private fun debugLogStart(
        logger: NotionLogger,
        request: HttpRequest,
        fullUrl: String,
        body: String?,
    ) {
        val b = if (body == null || body.isEmpty()) "" else "body $body\n"
        logger.debug("Sending a request:\n${request.method()} $fullUrl\n$b")
    }

    private fun debugLogFailure(logger: NotionLogger, e: Exception) {
        logger.info("Failed to disconnect from Notion: ${e.message}", e)
    }

    private fun debugLogSuccess(
        logger: NotionLogger,
        response: NotionHttpResponse
    ) {
        logger.debug("Received a response:\nstatus ${response.status}\nbody ${response.body}\n")
    }
}