package notion.api.v1.json

import notion.api.v1.model.database.Database
import notion.api.v1.model.database.Databases
import notion.api.v1.model.error.Error
import notion.api.v1.model.search.SearchResults
import notion.api.v1.model.user.User
import notion.api.v1.model.user.Users
import notion.api.v1.request.DatabaseQueryRequest
import notion.api.v1.request.SearchRequest

interface NotionJsonSerializer {
    fun toSearchResults(body: String): SearchResults
    fun toError(body: String): Error
    fun toUser(body: String): User
    fun toUsers(body: String): Users
    fun toDatabase(body: String): Database
    fun toDatabases(body: String): Databases
    fun toJsonString(request: SearchRequest): String
    fun toJsonString(request: DatabaseQueryRequest): String
}
