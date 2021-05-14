package notion.api.v1.json.impl

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import notion.api.v1.json.NotionJsonParser
import notion.api.v1.request.SearchRequest
import notion.api.v1.response.*

class KotlinxJsonParser(private val strict: Boolean = false) : NotionJsonParser {
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
}