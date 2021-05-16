package notion.api.v1.http

import notion.api.v1.logging.NotionLogger
import okhttp3.*
import java.util.concurrent.TimeUnit


class Okhttp3Client : NotionHttpClient {
    private val client: OkHttpClient

    companion object {
        private const val DEFAULT_CONNECT_TIMEOUT_MILLIS = 1_000
        private const val DEFAULT_READ_TIMEOUT_MILLIS = 20_000
        private const val DEFAULT_WRITE_TIMEOUT_MILLIS = 20_000
        private val MEDIA_TYPE_APPLICATION_JSON = MediaType.parse("application/json; charset=utf-8")

        fun buildOkHttpClient(
            connectTimeoutMillis: Int = DEFAULT_CONNECT_TIMEOUT_MILLIS,
            writeTimeoutMillis: Int = DEFAULT_CONNECT_TIMEOUT_MILLIS,
            readTimeoutMillis: Int = DEFAULT_CONNECT_TIMEOUT_MILLIS,
        ): OkHttpClient {
            val client: OkHttpClient.Builder = OkHttpClient.Builder()
            client.addInterceptor(UserAgentInterceptor)
            client.connectTimeout(connectTimeoutMillis.toLong(), TimeUnit.MILLISECONDS)
            client.writeTimeout(writeTimeoutMillis.toLong(), TimeUnit.MILLISECONDS)
            client.readTimeout(readTimeoutMillis.toLong(), TimeUnit.MILLISECONDS)
            return client.build()
        }
    }

    object UserAgentInterceptor : Interceptor {
        private val userAgent = UserAgent.buildUserAgent()

        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder().header("User-Agent", userAgent).build()
            return chain.proceed(request)
        }

    }

    constructor() {
        this.client = buildOkHttpClient()
    }

    constructor(
        connectTimeoutMillis: Int = DEFAULT_CONNECT_TIMEOUT_MILLIS,
        writeTimeoutMillis: Int = DEFAULT_WRITE_TIMEOUT_MILLIS,
        readTimeoutMillis: Int = DEFAULT_READ_TIMEOUT_MILLIS,
    ) {
        this.client = buildOkHttpClient(
            connectTimeoutMillis = connectTimeoutMillis,
            writeTimeoutMillis = writeTimeoutMillis,
            readTimeoutMillis = readTimeoutMillis,
        )
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
        val req = Request.Builder().url(fullUrl).post(RequestBody.create(MEDIA_TYPE_APPLICATION_JSON, body))
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
        val req = Request.Builder().url(fullUrl).patch(RequestBody.create(MEDIA_TYPE_APPLICATION_JSON, body))
        headers.forEach { (name, value) -> req.header(name, value) }
        return perform(req, body, logger)
    }

    @Throws(Exception::class)
    override fun close() {
        this.client.dispatcher().executorService().shutdown()
        this.client.connectionPool().evictAll()
        client.cache()?.close()
    }

    // ---------------------------------------

    private fun perform(
        req: Request.Builder,
        body: String,
        logger: NotionLogger
    ): NotionHttpResponse {
        val request = req.build()
        debugLogStart(logger, request, body)
        val resp = client.newCall(req.build()).execute()
        try {
            val response = NotionHttpResponse(
                status = resp.code(),
                headers = resp.headers().toMultimap(),
                body = resp.body()?.string() ?: ""
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
        request: Request,
        body: String,
    ) {
        val b = if (body.isBlank()) "" else "body $body\n"
        logger.debug("Sending a request:\n${request.method()} ${request.url()}\n$b")
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