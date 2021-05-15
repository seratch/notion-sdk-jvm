package notion.api.v1.endpoint

import notion.api.v1.exception.NotionAPIError
import notion.api.v1.http.NotionHttpClient
import notion.api.v1.json.NotionJsonSerializer
import notion.api.v1.logging.NotionLogger
import notion.api.v1.model.user.User
import notion.api.v1.model.user.Users
import notion.api.v1.request.UsersRequest

interface UsersSupport : EndpointsSupport {
    val httpClient: NotionHttpClient
    val jsonSerializer: NotionJsonSerializer
    val logger: NotionLogger
    val baseUrl: String

    fun retrieveUser(userId: String): User {
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
}