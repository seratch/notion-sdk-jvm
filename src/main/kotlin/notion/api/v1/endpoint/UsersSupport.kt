package notion.api.v1.endpoint

import notion.api.v1.exception.NotionAPIError
import notion.api.v1.http.NotionHttpClient
import notion.api.v1.json.NotionJsonSerializer
import notion.api.v1.logging.NotionLogger
import notion.api.v1.model.user.User
import notion.api.v1.model.user.Users
import notion.api.v1.request.RetrieveUserRequest
import notion.api.v1.request.ListUsersRequest

interface UsersSupport : EndpointsSupport {
    val httpClient: NotionHttpClient
    val jsonSerializer: NotionJsonSerializer
    val logger: NotionLogger
    val baseUrl: String

    // -----------------------------------------------
    // retrieveUser
    // -----------------------------------------------

    fun retrieveUser(userId: String): User {
        return retrieveUser(RetrieveUserRequest(userId))
    }

    fun retrieveUser(request: RetrieveUserRequest): User {
        val httpResponse = httpClient.get(
            logger = logger,
            url = "$baseUrl/users/${urlEncode(request.userId)}",
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

    // -----------------------------------------------
    // listUsers
    // -----------------------------------------------

    fun listUsers(): Users {
        return listUsers(ListUsersRequest(null, null))
    }

    fun listUsers(pageSize: Int, startCursor: String): Users {
        return listUsers(ListUsersRequest(startCursor = startCursor, pageSize = pageSize))
    }

    fun listUsers(request: ListUsersRequest): Users {
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
}