package notion.api.v1.endpoint

import notion.api.v1.exception.NotionAPIError
import notion.api.v1.http.NotionHttpClient
import notion.api.v1.json.NotionJsonSerializer
import notion.api.v1.logging.NotionLogger
import notion.api.v1.model.database.Database
import notion.api.v1.model.database.Databases
import notion.api.v1.model.database.QueryResults
import notion.api.v1.model.database.query.filter.QueryFilter
import notion.api.v1.model.database.query.sort.QuerySort
import notion.api.v1.request.DatabaseQueryRequest
import notion.api.v1.request.DatabasesRequest

interface DatabasesSupport : EndpointsSupport {
    val httpClient: NotionHttpClient
    val jsonSerializer: NotionJsonSerializer
    val logger: NotionLogger
    val baseUrl: String

    fun listDatabases(): Databases {
        return listDatabases(DatabasesRequest())
    }

    fun listDatabases(pageSize: Int, startCursor: String): Databases {
        return listDatabases(DatabasesRequest(startCursor = startCursor, pageSize = pageSize))
    }

    fun listDatabases(request: DatabasesRequest): Databases {
        val httpResponse = httpClient.get(
            logger = logger,
            url = "$baseUrl/databases",
            query = request.buildPaginationParams(),
            headers = buildRequestHeaders(emptyMap())
        )
        if (httpResponse.status == 200) {
            return jsonSerializer.toDatabases(httpResponse.body)
        } else {
            throw NotionAPIError(
                error = jsonSerializer.toError(httpResponse.body),
                httpResponse = httpResponse,
            )
        }
    }

    fun retrieveDatabase(databaseId: String): Database {
        val httpResponse = httpClient.get(
            logger = logger,
            url = "$baseUrl/databases/${urlEncode(databaseId)}",
            headers = buildRequestHeaders(emptyMap())
        )
        if (httpResponse.status == 200) {
            return jsonSerializer.toDatabase(httpResponse.body)
        } else {
            throw NotionAPIError(
                error = jsonSerializer.toError(httpResponse.body),
                httpResponse = httpResponse,
            )
        }
    }

    fun queryDatabase(
        databaseId: String,
        filter: QueryFilter? = null,
        sorts: List<QuerySort>? = null,
        startCursor: String? = null,
        pageSize: Int? = null,
    ): QueryResults {
        return queryDatabase(
            DatabaseQueryRequest(
                databaseId = databaseId,
                filter = filter,
                sorts = sorts,
                startCursor = startCursor,
                pageSize = pageSize,
            )
        )
    }

    fun queryDatabase(request: DatabaseQueryRequest): QueryResults {
        val httpResponse = httpClient.postTextBody(
            logger = logger,
            url = "$baseUrl/databases/${urlEncode(request.databaseId)}/query",
            body = jsonSerializer.toJsonString(request),
            headers = buildRequestHeaders(emptyMap())
        )
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