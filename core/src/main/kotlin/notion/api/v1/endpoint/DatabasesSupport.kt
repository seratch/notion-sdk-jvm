package notion.api.v1.endpoint

import notion.api.v1.exception.NotionAPIError
import notion.api.v1.http.NotionHttpClient
import notion.api.v1.json.NotionJsonSerializer
import notion.api.v1.logging.NotionLogger
import notion.api.v1.model.common.Cover
import notion.api.v1.model.common.Icon
import notion.api.v1.model.databases.*
import notion.api.v1.model.databases.query.filter.QueryTopLevelFilter
import notion.api.v1.model.databases.query.sort.QuerySort
import notion.api.v1.request.databases.*

interface DatabasesSupport : EndpointsSupport {
  val httpClient: NotionHttpClient
  val jsonSerializer: NotionJsonSerializer
  val logger: NotionLogger
  val baseUrl: String

  // -----------------------------------------------
  // createDatabase
  // -----------------------------------------------

  fun createDatabase(
      parent: DatabaseParent,
      title: List<DatabaseProperty.RichText>,
      properties: Map<String, DatabasePropertySchema>,
      description: List<DatabaseProperty.RichText>? = null,
      isInline: Boolean? = null,
      icon: Icon? = null,
      cover: Cover? = null,
  ): Database {
    return createDatabase(
        CreateDatabaseRequest(
            parent = parent,
            title = title,
            properties = properties,
            description = description,
            isInline = isInline,
            icon = icon,
            cover = cover,
        ))
  }

  fun createDatabase(database: CreateDatabaseRequest): Database {
    val httpResponse =
        httpClient.postTextBody(
            logger = logger,
            url = "$baseUrl/databases",
            body = jsonSerializer.toJsonString(database),
            headers = buildRequestHeaders(contentTypeJson()))
    if (httpResponse.status == 200) {
      return jsonSerializer.toDatabase(httpResponse.body)
    } else {
      throw NotionAPIError(
          error = jsonSerializer.toError(httpResponse.body),
          httpResponse = httpResponse,
      )
    }
  }

  // -----------------------------------------------
  // updateDatabase
  // -----------------------------------------------

  fun updateDatabase(
      databaseId: String,
      title: List<DatabaseProperty.RichText>? = null,
      properties: Map<String, DatabasePropertySchema>? = null,
  ): Database {
    return updateDatabase(
        UpdateDatabaseRequest(
            id = databaseId,
            title = title,
            properties = properties,
        ))
  }

  fun updateDatabase(request: UpdateDatabaseRequest): Database {
    val httpResponse =
        httpClient.patchTextBody(
            logger = logger,
            url = "$baseUrl/databases/${urlEncode(request.id)}",
            body = jsonSerializer.toJsonString(request),
            headers = buildRequestHeaders(contentTypeJson()))
    if (httpResponse.status == 200) {
      return jsonSerializer.toDatabase(httpResponse.body)
    } else {
      throw NotionAPIError(
          error = jsonSerializer.toError(httpResponse.body),
          httpResponse = httpResponse,
      )
    }
  }

  // -----------------------------------------------
  // listDatabases
  // -----------------------------------------------

  @Deprecated(
      message = "This endpoint is no longer recommended, use search instead.",
      replaceWith = ReplaceWith("search(query)"))
  fun listDatabases(): Databases {
    return listDatabases(ListDatabasesRequest())
  }

  @Deprecated(
      message = "This endpoint is no longer recommended, use search instead.",
      replaceWith = ReplaceWith("search(query)"))
  fun listDatabases(pageSize: Int? = null, startCursor: String? = null): Databases {
    return listDatabases(ListDatabasesRequest(startCursor = startCursor, pageSize = pageSize))
  }

  @Deprecated(
      message = "This endpoint is no longer recommended, use search instead.",
      replaceWith = ReplaceWith("search(query)"))
  fun listDatabases(request: ListDatabasesRequest): Databases {
    val httpResponse =
        httpClient.get(
            logger = logger,
            url = "$baseUrl/databases",
            query = request.buildPaginationParams(),
            headers = buildRequestHeaders(emptyMap()))
    if (httpResponse.status == 200) {
      return jsonSerializer.toDatabases(httpResponse.body)
    } else {
      throw NotionAPIError(
          error = jsonSerializer.toError(httpResponse.body),
          httpResponse = httpResponse,
      )
    }
  }

  // -----------------------------------------------
  // retrieveDatabase
  // -----------------------------------------------

  fun retrieveDatabase(databaseId: String): Database {
    return retrieveDatabase(RetrieveDatabaseRequest(databaseId))
  }

  fun retrieveDatabase(request: RetrieveDatabaseRequest): Database {
    val httpResponse =
        httpClient.get(
            logger = logger,
            url = "$baseUrl/databases/${urlEncode(request.databaseId)}",
            headers = buildRequestHeaders(emptyMap()))
    if (httpResponse.status == 200) {
      return jsonSerializer.toDatabase(httpResponse.body)
    } else {
      throw NotionAPIError(
          error = jsonSerializer.toError(httpResponse.body),
          httpResponse = httpResponse,
      )
    }
  }

  // -----------------------------------------------
  // queryDatabase
  // -----------------------------------------------

  fun queryDatabase(
      databaseId: String,
      filter: QueryTopLevelFilter? = null,
      sorts: List<QuerySort>? = null,
      startCursor: String? = null,
      pageSize: Int? = null,
  ): QueryResults {
    return queryDatabase(
        QueryDatabaseRequest(
            databaseId = databaseId,
            filter = filter,
            sorts = sorts,
            startCursor = startCursor,
            pageSize = pageSize,
        ))
  }

  fun queryDatabase(request: QueryDatabaseRequest): QueryResults {
    val httpResponse =
        httpClient.postTextBody(
            logger = logger,
            url = "$baseUrl/databases/${urlEncode(request.databaseId)}/query",
            body = jsonSerializer.toJsonString(request),
            headers = buildRequestHeaders(contentTypeJson()))
    if (httpResponse.status == 200) {
      return jsonSerializer.toQueryResults(httpResponse.body)
    } else {
      throw NotionAPIError(
          error = jsonSerializer.toError(httpResponse.body),
          httpResponse = httpResponse,
      )
    }
  }
}
