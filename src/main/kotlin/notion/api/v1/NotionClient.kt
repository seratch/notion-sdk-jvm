package notion.api.v1

import notion.api.v1.Utilities.urlEncode
import notion.api.v1.exception.NotionAPIError
import notion.api.v1.http.NotionHttpClient
import notion.api.v1.http.impl.JavaNotionHttpClient
import notion.api.v1.json.NotionJsonSerializer
import notion.api.v1.json.impl.KotlinxJsonSerializer
import notion.api.v1.logging.NotionLogger
import notion.api.v1.logging.impl.StdoutNotionLogger
import notion.api.v1.model.database.Database
import notion.api.v1.model.database.Databases
import notion.api.v1.model.database.query.filter.QueryFilter
import notion.api.v1.model.database.query.sort.QuerySort
import notion.api.v1.model.search.SearchResults
import notion.api.v1.model.user.User
import notion.api.v1.model.user.Users
import notion.api.v1.request.DatabaseQueryRequest
import notion.api.v1.request.DatabasesRequest
import notion.api.v1.request.SearchRequest
import notion.api.v1.request.UsersRequest

class NotionClient(
    private val token: String,
    var httpClient: NotionHttpClient = JavaNotionHttpClient(),
    var jsonSerializer: NotionJsonSerializer = KotlinxJsonSerializer(),
    var logger: NotionLogger = StdoutNotionLogger(),
    var baseUrl: String = "https://api.notion.com/v1",
) {
    constructor(token: String) : this(
        token = token,
        httpClient = JavaNotionHttpClient(),
        jsonSerializer = KotlinxJsonSerializer(),
        logger = StdoutNotionLogger(),
    )

    // ---------------------------------------
    // Databases
    // ---------------------------------------

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

    fun findDatabase(databaseId: String): Database {
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
    ): Database {
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

    fun queryDatabase(request: DatabaseQueryRequest): Database {
        val httpResponse = httpClient.postTextBody(
            logger = logger,
            url = "$baseUrl/databases/${urlEncode(request.databaseId)}/query",
            body = jsonSerializer.toJsonString(request),
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

    // ---------------------------------------
    // Pages
    // ---------------------------------------

    // TODO: retrieve a page
    // TODO: create a page
    // TODO: update page properties

    // ---------------------------------------
    // Blocks
    // ---------------------------------------

    // TODO: retrieve block children
    // TODO: append block children

    // ---------------------------------------
    // Search
    // ---------------------------------------

    fun search(query: String): SearchResults {
        return search(SearchRequest(query = query))
    }

    fun search(request: SearchRequest): SearchResults {
        val httpResponse = httpClient.postTextBody(
            logger = logger,
            url = "$baseUrl/search",
            body = jsonSerializer.toJsonString(request),
            headers = buildRequestHeaders(
                mapOf(
                    "Content-Type" to "application/json"
                )
            )
        )
        if (httpResponse.status == 200) {
            return jsonSerializer.toSearchResults(httpResponse.body)
        } else {
            throw NotionAPIError(
                error = jsonSerializer.toError(httpResponse.body),
                httpResponse = httpResponse,
            )
        }
    }

    // ---------------------------------------
    // Users
    // ---------------------------------------

    fun findUser(userId: String): User {
        val httpResponse = httpClient.get(
            logger = logger,
            url = "$baseUrl/users/${urlEncode(userId)}",
            headers = buildRequestHeaders(emptyMap())
        )
        if (httpResponse.status == 200) {
            return jsonSerializer.toUser(httpResponse.body)
        } else {
            throw NotionAPIError(
                error = jsonSerializer.toError(httpResponse.body),
                httpResponse = httpResponse,
            )
        }
    }

    fun listUsers(): Users {
        return listUsers(UsersRequest(null, null))
    }

    fun listUsers(pageSize: Int, startCursor: String): Users {
        return listUsers(UsersRequest(startCursor = startCursor, pageSize = pageSize))
    }

    fun listUsers(request: UsersRequest): Users {
        val httpResponse = httpClient.get(
            logger = logger,
            url = "$baseUrl/users",
            query = request.buildPaginationParams(),
            headers = buildRequestHeaders(emptyMap())
        )
        if (httpResponse.status == 200) {
            return jsonSerializer.toUsers(httpResponse.body)
        } else {
            throw NotionAPIError(
                error = jsonSerializer.toError(httpResponse.body),
                httpResponse = httpResponse,
            )
        }
    }

    // ---------------------------------------

    private fun buildRequestHeaders(additionalOnes: Map<String, String>): Map<String, String> {
        return mapOf(
            "Authorization" to "Bearer $token",
            "User-Agent" to "Notion-SDK-JVM by @seratch" // TODO: finalize
        ).plus(additionalOnes)
    }
}
