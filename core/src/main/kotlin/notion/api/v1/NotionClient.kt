package notion.api.v1

import notion.api.v1.endpoint.*
import notion.api.v1.http.HttpUrlConnNotionHttpClient
import notion.api.v1.http.NotionHttpClient
import notion.api.v1.json.GsonSerializer
import notion.api.v1.json.NotionJsonSerializer
import notion.api.v1.logging.NotionLogger
import notion.api.v1.logging.StdoutNotionLogger
import java.io.Closeable

class NotionClient(
    override var token: String,
    override var httpClient: NotionHttpClient = HttpUrlConnNotionHttpClient(),
    override var logger: NotionLogger = StdoutNotionLogger(),
    override var jsonSerializer: NotionJsonSerializer = GsonSerializer(),
    override var baseUrl: String = "https://api.notion.com/v1",
) : AutoCloseable, Closeable, DatabasesSupport, PagesSupport, BlocksSupport, SearchSupport, UsersSupport {

    constructor(token: String) : this(token = token, httpClient = HttpUrlConnNotionHttpClient())
    constructor(token: String, httpClient: NotionHttpClient, logger: NotionLogger) : this(
        token = token,
        httpClient = httpClient,
        logger = logger,
        jsonSerializer = GsonSerializer(),
    )

    override fun close() {
        httpClient.close()
    }
}
