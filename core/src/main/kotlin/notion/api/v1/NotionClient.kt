package notion.api.v1

import java.io.Closeable
import notion.api.v1.endpoint.*
import notion.api.v1.http.HttpUrlConnNotionHttpClient
import notion.api.v1.http.NotionHttpClient
import notion.api.v1.json.GsonSerializer
import notion.api.v1.json.NotionJsonSerializer
import notion.api.v1.logging.NotionLogger
import notion.api.v1.logging.StdoutLogger

class NotionClient
@JvmOverloads
constructor(
    override var token: String? = null,
    override var clientId: String? = null,
    override var clientSecret: String? = null,
    override var redirectUri: String? = null,
    override var httpClient: NotionHttpClient = defaultHttpClient,
    override var logger: NotionLogger = defaultLogger,
    override var jsonSerializer: NotionJsonSerializer = defaultJsonSerializer,
    override var baseUrl: String = defaultBaseUrl,
) :
    AutoCloseable,
    Closeable,
    DatabasesSupport,
    PagesSupport,
    BlocksSupport,
    SearchSupport,
    UsersSupport,
    OAuthSupport {

  companion object {
    @JvmStatic val defaultBaseUrl: String = "https://api.notion.com/v1"
    @JvmStatic val defaultHttpClient: NotionHttpClient = HttpUrlConnNotionHttpClient()
    @JvmStatic val defaultLogger: NotionLogger = StdoutLogger()
    @JvmStatic val defaultJsonSerializer: NotionJsonSerializer = GsonSerializer()
  }

  override fun close() {
    httpClient.close()
  }
}
