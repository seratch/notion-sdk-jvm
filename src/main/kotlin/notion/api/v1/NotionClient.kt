package notion.api.v1

import notion.api.v1.endpoint.*
import notion.api.v1.http.NotionHttpClient
import notion.api.v1.http.impl.JavaNotionHttpClient
import notion.api.v1.json.NotionJsonSerializer
import notion.api.v1.json.impl.GsonSerializer
import notion.api.v1.logging.NotionLogger
import notion.api.v1.logging.impl.StdoutNotionLogger

class NotionClient(
    override val token: String,
    override var httpClient: NotionHttpClient = JavaNotionHttpClient(),
    override var jsonSerializer: NotionJsonSerializer = GsonSerializer(),
    override var logger: NotionLogger = StdoutNotionLogger(),
    override var baseUrl: String = "https://api.notion.com/v1",
) : DatabasesSupport, PagesSupport, BlocksSupport, SearchSupport, UsersSupport {
    constructor(token: String) : this(
        token = token,
        httpClient = JavaNotionHttpClient(),
        jsonSerializer = GsonSerializer(),
        logger = StdoutNotionLogger(),
    )
}
