package notion.api.v1.json

import notion.api.v1.request.SearchRequest
import notion.api.v1.response.*

interface NotionJsonParser {
    fun toSearchResults(body: String): SearchResults
    fun toError(body: String): Error
    fun toUser(body: String): User
    fun toUsers(body: String): Users
    fun toDatabase(body: String): Database
    fun toDatabases(body: String): Databases
    fun toJsonString(request: SearchRequest): String
}
