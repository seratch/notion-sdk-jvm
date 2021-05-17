package notion.api.v1

import notion.api.v1.endpoint.*
import notion.api.v1.http.HttpUrlConnNotionHttpClient
import notion.api.v1.http.NotionHttpClient
import notion.api.v1.json.GsonSerializer
import notion.api.v1.json.NotionJsonSerializer
import notion.api.v1.logging.NotionLogger
import notion.api.v1.logging.StdoutLogger
import java.io.Closeable

class NotionClient(
    override var token: String,
    override var httpClient: NotionHttpClient = defaultHttpClient,
    override var logger: NotionLogger = defaultLogger,
    override var jsonSerializer: NotionJsonSerializer = defaultJsonSerializer,
    override var baseUrl: String = "https://api.notion.com/v1",
) : AutoCloseable, Closeable, DatabasesSupport, PagesSupport, BlocksSupport, SearchSupport, UsersSupport {

    companion object {
        private val defaultHttpClient: NotionHttpClient = HttpUrlConnNotionHttpClient()
        private val defaultLogger: NotionLogger = StdoutLogger()
        private val defaultJsonSerializer: NotionJsonSerializer = GsonSerializer()
    }

    constructor(token: String) : this(token = token, httpClient = defaultHttpClient)
    constructor(token: String, httpClient: NotionHttpClient, logger: NotionLogger) : this(
        token = token,
        httpClient = httpClient,
        logger = logger,
        jsonSerializer = defaultJsonSerializer,
    )

    override fun close() {
        httpClient.close()
    }
}
