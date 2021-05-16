package notion.api.v1.http

import notion.api.v1.logging.NotionLogger
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.util.concurrent.TimeUnit

class Okhttp4Client : NotionHttpClient {
    private val client: OkHttpClient

    companion object {
        private val MEDIA_TYPE_APPLICATION_JSON = "application/json; charset=utf-8".toMediaType()
    }

    object UserAgentInterceptor : Interceptor {
        private val userAgent = UserAgent.buildUserAgent()

        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder().header("User-Agent", userAgent).build()
            return chain.proceed(request)
        }

    }

    constructor(
        connectTimeoutMillis: Int = 1_000,
        writeTimeoutMillis: Int = 10_000,
        readTimeoutMillis: Int = 10_000,
    ) {
        val client: OkHttpClient.Builder = OkHttpClient.Builder()
        client.addInterceptor(UserAgentInterceptor)
        client.connectTimeout(connectTimeoutMillis.toLong(), TimeUnit.MILLISECONDS)
        client.writeTimeout(writeTimeoutMillis.toLong(), TimeUnit.MILLISECONDS)
        client.readTimeout(readTimeoutMillis.toLong(), TimeUnit.MILLISECONDS)
        this.client = client.build()
    }

    override fun get(
        logger: NotionLogger,
        url: String,
        query: Map<String, String>,
        headers: Map<String, String>
    ): NotionHttpResponse {
        val fullUrl = buildFullUrl(url, buildQueryString(query))
        val req = Request.Builder().url(fullUrl).get()
        headers.forEach { (name, value) -> req.header(name, value) }
        return perform(req, "", logger)
    }

    override fun postTextBody(
        logger: NotionLogger,
        url: String,
        query: Map<String, String>,
        body: String,
        headers: Map<String, String>
    ): NotionHttpResponse {
        val fullUrl = buildFullUrl(url, buildQueryString(query))
        val req = Request.Builder().url(fullUrl).post(body.toRequestBody(MEDIA_TYPE_APPLICATION_JSON))
        headers.forEach { (name, value) -> req.header(name, value) }
        return perform(req, body, logger)
    }

    override fun patchTextBody(
        logger: NotionLogger,
        url: String,
        query: Map<String, String>,
        body: String,
        headers: Map<String, String>
    ): NotionHttpResponse {
        val fullUrl = buildFullUrl(url, buildQueryString(query))
        val req = Request.Builder().url(fullUrl).patch(body.toRequestBody(MEDIA_TYPE_APPLICATION_JSON))
        headers.forEach { (name, value) -> req.header(name, value) }
        return perform(req, body, logger)
    }

    @Throws(Exception::class)
    override fun close() {
        this.client.dispatcher.executorService.shutdown()
        this.client.connectionPool.evictAll()
        client.cache?.close()
    }

    // ---------------------------------------

    private fun perform(
        req: Request.Builder,
        body: String,
        logger: NotionLogger
    ): NotionHttpResponse {
        val request = req.build()
        debugLogStart(logger, body, request)
        val resp = client.newCall(req.build()).execute()
        try {
            val response = NotionHttpResponse(
                status = resp.code,
                headers = resp.headers.toMultimap(),
                body = resp.body?.string() ?: ""
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
        body: String,
        request: Request,
    ) {
        val b = if (body.isBlank()) "" else "body: $body\n"
        logger.debug("Sending a request:\n${request.method} ${request.url}\n$b")
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