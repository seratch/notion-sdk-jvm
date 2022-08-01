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
// We don't intentionally use @JvmOverloads here. Refer to the following constructors for details.
// @JvmOverloads
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
    CommentsSupport,
    SearchSupport,
    UsersSupport,
    OAuthSupport {

  // Internal app initialization
  // This constructor is for Java and other languages
  constructor(
      token: String
  ) : this(
      token = token,
      clientId = null,
      clientSecret = null,
      redirectUri = null,
  )

  // OAuth wired app initialization
  // This constructor is for Java and other languages
  constructor(
      clientId: String,
      clientSecret: String,
      redirectUri: String
  ) : this(
      token = null,
      clientId = clientId,
      clientSecret = clientSecret,
      redirectUri = redirectUri,
  )

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
