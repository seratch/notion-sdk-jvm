package notion.api.v1.http

import notion.api.v1.logging.NotionLogger
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration

class JavaHttpClient(
    connectTimeoutMillis: Int = 1_000,
    private val readTimeoutMillis: Int = 10_000,
) : NotionHttpClient {
    // TODO: proxy
    private val client: HttpClient = HttpClient.newBuilder()
        .connectTimeout(Duration.ofMillis(connectTimeoutMillis.toLong()))
        .build()

    override fun get(
        logger: NotionLogger,
        url: String,
        query: Map<String, String>,
        headers: Map<String, String>
    ): NotionHttpResponse {
        val request = HttpRequest.newBuilder()
            .GET()
            .uri(URI(buildFullUrl(url, buildQueryString(query))))
            .timeout(Duration.ofMillis(readTimeoutMillis.toLong()))
        headers.forEach { (name, value) -> request.header(name, value) }
        val response = client.send(request.build(), HttpResponse.BodyHandlers.ofString())
        return NotionHttpResponse(
            status = response.statusCode(),
            headers = response.headers().map(),
            body = response.body()
        )
    }

    override fun postTextBody(
        logger: NotionLogger,
        url: String,
        query: Map<String, String>,
        body: String,
        headers: Map<String, String>
    ): NotionHttpResponse {
        val request = HttpRequest.newBuilder()
            .POST(HttpRequest.BodyPublishers.ofString(body, Charsets.UTF_8))
            .uri(URI(buildFullUrl(url, buildQueryString(query))))
            .timeout(Duration.ofMillis(readTimeoutMillis.toLong()))
        headers.forEach { (name, value) -> request.header(name, value) }
        val response = client.send(request.build(), HttpResponse.BodyHandlers.ofString())
        return NotionHttpResponse(
            status = response.statusCode(),
            headers = response.headers().map(),
            body = response.body()
        )
    }

    override fun patchTextBody(
        logger: NotionLogger,
        url: String,
        query: Map<String, String>,
        body: String,
        headers: Map<String, String>
    ): NotionHttpResponse {
        val request = HttpRequest.newBuilder()
            .method("PATCH", HttpRequest.BodyPublishers.ofString(body, Charsets.UTF_8))
            .uri(URI(buildFullUrl(url, buildQueryString(query))))
            .timeout(Duration.ofMillis(readTimeoutMillis.toLong()))
        headers.forEach { (name, value) -> request.header(name, value) }
        val response = client.send(request.build(), HttpResponse.BodyHandlers.ofString())
        return NotionHttpResponse(
            status = response.statusCode(),
            headers = response.headers().map(),
            body = response.body()
        )
    }
}