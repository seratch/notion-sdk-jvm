package notion.api.v1

import notion.api.v1.ScalaNotionClient._
import notion.api.v1.exception.NotionAPIError
import notion.api.v1.http.{NotionHttpClient, UserAgent}
import notion.api.v1.json.NotionJsonSerializer
import notion.api.v1.logging.NotionLogger
import notion.api.v1.model.blocks.Block
import notion.api.v1.model.common.{Cover, Icon}
import notion.api.v1.model.databases.query.filter.QueryTopLevelFilter
import notion.api.v1.model.databases.query.sort.QuerySort
import notion.api.v1.model.databases.{Database, Databases, QueryResults}
import notion.api.v1.model.pages.{Page, PageParent, PageProperty}
import notion.api.v1.model.search.SearchResults
import notion.api.v1.model.users.{User, Users}
import notion.api.v1.request.databases.QueryDatabaseRequest
import notion.api.v1.request.pages.{
  CreatePageRequest,
  UpdatePagePropertiesRequest
}
import notion.api.v1.request.search.SearchRequest
import notion.api.v1.request.search.SearchRequest._

import java.io.Closeable
import java.net.URLEncoder
import scala.jdk.CollectionConverters._

case class ScalaNotionClient(
    var token: String,
    var httpClient: NotionHttpClient = NotionClient.getDefaultHttpClient,
    var logger: NotionLogger = NotionClient.getDefaultLogger,
    var jsonSerializer: NotionJsonSerializer =
      NotionClient.getDefaultJsonSerializer,
    var baseUrl: String = NotionClient.getDefaultBaseUrl
) extends AutoCloseable
    with Closeable {

  override def close(): Unit = {
    httpClient.close()
  }

  // ---------------------------------------------------------------
  // Blocks
  // ---------------------------------------------------------------

  // TODO

  // ---------------------------------------------------------------
  // Databases
  // ---------------------------------------------------------------

  // TODO: Database creation

  def listDatabases(
      startCursor: String = "",
      pageSize: Int = 0
  ): Databases = {
    val httpResponse = httpClient.get(
      logger,
      s"${baseUrl}/databases",
      toQueryParams(
        "start_cursor" -> Option(startCursor).filterNot(_.isBlank),
        "page_size" -> Option(pageSize).filter(_ > 0)
      ),
      buildRequestHeaders(Map.empty).asJava
    )
    if (httpResponse.getStatus == 200) {
      jsonSerializer.toDatabases(httpResponse.getBody)
    } else {
      throw new NotionAPIError(
        jsonSerializer.toError(httpResponse.getBody),
        httpResponse
      )
    }
  }

  def retrieveDatabase(databaseId: String): Database = {
    val httpResponse = httpClient.get(
      logger,
      s"${baseUrl}/databases/${urlEncode(databaseId)}",
      toQueryParams(),
      buildRequestHeaders(Map.empty).asJava
    )
    if (httpResponse.getStatus == 200) {
      jsonSerializer.toDatabase(httpResponse.getBody)
    } else {
      throw new NotionAPIError(
        jsonSerializer.toError(httpResponse.getBody),
        httpResponse
      )
    }
  }

  private object NoQueryFilter extends QueryTopLevelFilter

  def queryDatabase(
      databaseId: String,
      filter: QueryTopLevelFilter = NoQueryFilter,
      sorts: List[QuerySort] = List.empty,
      startCursor: String = "",
      pageSize: Int = 0
  ): QueryResults = {
    val request = new QueryDatabaseRequest(
      databaseId,
      if (filter == NoQueryFilter) null else filter,
      if (sorts.isEmpty) null else sorts.asJava,
      if (startCursor == "") null else startCursor,
      if (pageSize > 0) pageSize else null
    )
    val httpResponse = httpClient.postTextBody(
      logger,
      s"${baseUrl}/databases/${urlEncode(databaseId)}/query",
      toQueryParams(),
      jsonSerializer.toJsonString(request),
      buildRequestHeaders(contentTypeJson).asJava
    )
    if (httpResponse.getStatus == 200) {
      jsonSerializer.toQueryResults(httpResponse.getBody)
    } else {
      throw new NotionAPIError(
        jsonSerializer.toError(httpResponse.getBody),
        httpResponse
      )
    }
  }

  // ---------------------------------------------------------------
  // Pages
  // ---------------------------------------------------------------

  def createPage(
      parent: PageParent,
      properties: Map[String, PageProperty],
      children: List[Block] = List.empty,
      icon: Icon = null,
      cover: Cover = null
  ): Page = {
    val request = new CreatePageRequest(
      parent,
      properties.asJava,
      if (children.isEmpty) null else children.asJava,
      icon,
      cover
    )
    val httpResponse = httpClient.postTextBody(
      logger,
      s"${baseUrl}/pages",
      toQueryParams(),
      jsonSerializer.toJsonString(request),
      buildRequestHeaders(contentTypeJson).asJava
    )
    if (httpResponse.getStatus == 200) {
      jsonSerializer.toPage(httpResponse.getBody)
    } else {
      throw new NotionAPIError(
        jsonSerializer.toError(httpResponse.getBody),
        httpResponse
      )
    }
  }

  def retrievePage(pageId: String): Page = {
    val httpResponse = httpClient.get(
      logger,
      s"${baseUrl}/pages/${urlEncode(pageId)}",
      toQueryParams(),
      buildRequestHeaders(Map.empty).asJava
    )
    if (httpResponse.getStatus == 200) {
      jsonSerializer.toPage(httpResponse.getBody)
    } else {
      throw new NotionAPIError(
        jsonSerializer.toError(httpResponse.getBody),
        httpResponse
      )
    }
  }

  def updatePageProperties(
      pageId: String,
      properties: Map[String, PageProperty],
      archive: Boolean = false,
      icon: Icon = null,
      cover: Cover = null
  ): Page = {
    val request = new UpdatePagePropertiesRequest(
      pageId,
      properties.asJava,
      if (archive) archive else null,
      icon,
      cover
    )
    val httpResponse = httpClient.patchTextBody(
      logger,
      s"${baseUrl}/pages/${urlEncode(pageId)}",
      toQueryParams(),
      jsonSerializer.toJsonString(request),
      buildRequestHeaders(contentTypeJson).asJava
    )
    if (httpResponse.getStatus == 200) {
      jsonSerializer.toPage(httpResponse.getBody)
    } else {
      throw new NotionAPIError(
        jsonSerializer.toError(httpResponse.getBody),
        httpResponse
      )
    }
  }

  // ---------------------------------------------------------------
  // Search
  // ---------------------------------------------------------------

  object NoSearchFilter extends SearchFilter()

  object NoSearchSort extends SearchSort()

  def search(
      query: String,
      filter: SearchFilter = NoSearchFilter,
      sort: SearchSort = NoSearchSort,
      startCursor: String = "",
      pageSize: Int = 0
  ): SearchResults = {
    val request = new SearchRequest(
      query,
      if (filter == NoSearchFilter) null else filter,
      if (sort == NoSearchSort) null else sort,
      if (startCursor == "") null else startCursor,
      if (pageSize > 0) pageSize else null
    )
    val httpResponse = httpClient.postTextBody(
      logger,
      s"${baseUrl}/search",
      toQueryParams(),
      jsonSerializer.toJsonString(request),
      buildRequestHeaders(contentTypeJson).asJava
    )
    if (httpResponse.getStatus == 200) {
      jsonSerializer.toSearchResults(httpResponse.getBody)
    } else {
      throw new NotionAPIError(
        jsonSerializer.toError(httpResponse.getBody),
        httpResponse
      )
    }
  }

  // ---------------------------------------------------------------
  // Users
  // ---------------------------------------------------------------

  def listUsers(startCursor: String = "", pageSize: Int = 0): Users = {
    val httpResponse = httpClient.get(
      logger,
      s"${baseUrl}/users",
      toQueryParams(
        "start_cursor" -> Option(startCursor).filterNot(_.isBlank),
        "page_size" -> Option(pageSize).filter(_ > 0)
      ),
      buildRequestHeaders(Map.empty).asJava
    )
    if (httpResponse.getStatus == 200) {
      jsonSerializer.toUsers(httpResponse.getBody)
    } else {
      throw new NotionAPIError(
        jsonSerializer.toError(httpResponse.getBody),
        httpResponse
      )
    }
  }

  def retrieveUser(userId: String): User = {
    val httpResponse = httpClient.get(
      logger,
      s"${baseUrl}/users/${urlEncode(userId)}",
      toQueryParams(),
      buildRequestHeaders(Map.empty).asJava
    )
    if (httpResponse.getStatus == 200) {
      jsonSerializer.toUser(httpResponse.getBody)
    } else {
      throw new NotionAPIError(
        jsonSerializer.toError(httpResponse.getBody),
        httpResponse
      )
    }
  }

  // ---------------------------------------------------------------
  // Private Methods
  // ---------------------------------------------------------------

  private[this] def buildRequestHeaders(
      additionalOnes: Map[String, String]
  ): Map[String, String] = {
    Map(
      "Authorization" -> s"Bearer ${token}",
      "Notion-Version" -> "2021-05-13",
      "User-Agent" -> UserAgent.buildUserAgent()
    ) ++ additionalOnes
  }

}

object ScalaNotionClient {

  private val contentTypeJson = Map(
    "Content-Type" -> "application/json; charset=utf-8"
  )

  private def urlEncode(v: String): String = URLEncoder.encode(v, "UTF-8")

  private def toQueryParams(
      kvs: (String, Option[_])*
  ): java.util.Map[String, String] = {
    kvs
      .filter { case (_, v) => v.isDefined }
      .map { case (k, v) => k -> v.map(_.toString).getOrElse("") }
      .toMap
      .asJava
  }

}
