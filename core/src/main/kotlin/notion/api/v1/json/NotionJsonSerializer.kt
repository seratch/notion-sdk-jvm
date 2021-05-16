package notion.api.v1.json

import notion.api.v1.model.database.Database
import notion.api.v1.model.database.Databases
import notion.api.v1.model.database.QueryResults
import notion.api.v1.model.error.Error
import notion.api.v1.model.page.Page
import notion.api.v1.model.search.SearchResults
import notion.api.v1.model.user.User
import notion.api.v1.model.user.Users
import notion.api.v1.request.CreatePageRequest
import notion.api.v1.request.QueryDatabaseRequest
import notion.api.v1.request.SearchRequest
import notion.api.v1.request.UpdatePagePropertiesRequest

interface NotionJsonSerializer {

    fun toDatabase(body: String): Database
    fun toDatabases(body: String): Databases
    fun toError(body: String): Error
    fun toPage(body: String): Page
    fun toQueryResults(body: String): QueryResults
    fun toSearchResults(body: String): SearchResults
    fun toUser(body: String): User
    fun toUsers(body: String): Users

    fun toJsonString(request: CreatePageRequest): String
    fun toJsonString(request: SearchRequest): String
    fun toJsonString(request: QueryDatabaseRequest): String
    fun toJsonString(request: UpdatePagePropertiesRequest): String
}
