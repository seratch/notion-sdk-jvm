package notion.api.v1.json.impl

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import notion.api.v1.json.NotionJsonSerializer
import notion.api.v1.model.database.Database
import notion.api.v1.model.database.Databases
import notion.api.v1.model.error.Error
import notion.api.v1.model.search.SearchResults
import notion.api.v1.model.user.User
import notion.api.v1.model.user.Users
import notion.api.v1.request.DatabaseQueryRequest
import notion.api.v1.request.SearchRequest

class KotlinxJsonSerializer(private val strict: Boolean = false) : NotionJsonSerializer {
    private val json = Json {
        // false for this library development only
        ignoreUnknownKeys = !strict
    }

    override fun toSearchResults(body: String): SearchResults = json.decodeFromString(body)
    override fun toError(body: String): Error = json.decodeFromString(body)
    override fun toUser(body: String): User = json.decodeFromString(body)
    override fun toUsers(body: String): Users = json.decodeFromString(body)
    override fun toDatabase(body: String): Database = json.decodeFromString(body)
    override fun toDatabases(body: String): Databases = json.decodeFromString(body)
    override fun toJsonString(request: SearchRequest): String = json.encodeToJsonElement(request).toString()
    override fun toJsonString(request: DatabaseQueryRequest): String = json.encodeToJsonElement(request).toString()
}