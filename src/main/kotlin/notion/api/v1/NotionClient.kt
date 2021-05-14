package notion.api.v1

import notion.api.v1.exception.NotionAPIError
import notion.api.v1.http.NotionHttpClient
import notion.api.v1.http.impl.JavaNotionHttpClient
import notion.api.v1.http.util.QueryStringUtil.urlEncode
import notion.api.v1.json.NotionJsonParser
import notion.api.v1.json.impl.KotlinxJsonParser
import notion.api.v1.logging.NotionLogger
import notion.api.v1.logging.impl.StdoutNotionLogger
import notion.api.v1.request.DatabasesRequest
import notion.api.v1.request.SearchRequest
import notion.api.v1.request.UsersRequest
import notion.api.v1.response.*

class NotionClient(
    private val token: String,
    var httpClient: NotionHttpClient = JavaNotionHttpClient(),
    var jsonParser: NotionJsonParser = KotlinxJsonParser(),
    var logger: NotionLogger = StdoutNotionLogger(),
    var baseUrl: String = "https://api.notion.com/v1",
) {
    constructor(token: String) : this(
        token = token,
        httpClient = JavaNotionHttpClient(),
        jsonParser = KotlinxJsonParser(),
        logger = StdoutNotionLogger(),
    )

    // ---------------------------------------
    // Databases
    // ---------------------------------------

    fun listDatabases(): Databases {
        return listDatabases(DatabasesRequest(null, null))
    }

    fun listDatabases(pageSize: Int, startCursor: String): Databases {
        return listDatabases(DatabasesRequest(startCursor = startCursor, pageSize = pageSize))
    }

    fun listDatabases(request: DatabasesRequest): Databases {
        val httpResponse = httpClient.get(
            logger = logger,
            url = "$baseUrl/databases",
            query = request.toQueryString(),
            headers = buildRequestHeaders(emptyMap())
        )
        if (httpResponse.status == 200) {
            return jsonParser.toDatabases(httpResponse.body)
        } else {
            throw NotionAPIError(
                error = jsonParser.toError(httpResponse.body),
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
            return jsonParser.toDatabase(httpResponse.body)
        } else {
            throw NotionAPIError(
                error = jsonParser.toError(httpResponse.body),
                httpResponse = httpResponse,
            )
        }
    }

    // TODO: query database

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
            body = jsonParser.toJsonString(request),
            headers = buildRequestHeaders(
                mapOf(
                    "Content-Type" to "application/json"
                )
            )
        )
        if (httpResponse.status == 200) {
            return jsonParser.toSearchResults(httpResponse.body)
        } else {
            throw NotionAPIError(
                error = jsonParser.toError(httpResponse.body),
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
            return jsonParser.toUser(httpResponse.body)
        } else {
            throw NotionAPIError(
                error = jsonParser.toError(httpResponse.body),
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
            query = request.toQueryString(),
            headers = buildRequestHeaders(emptyMap())
        )
        if (httpResponse.status == 200) {
            return jsonParser.toUsers(httpResponse.body)
        } else {
            throw NotionAPIError(
                error = jsonParser.toError(httpResponse.body),
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
