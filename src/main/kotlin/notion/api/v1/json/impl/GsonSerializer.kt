package notion.api.v1.json.impl

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import notion.api.v1.json.NotionJsonSerializer
import notion.api.v1.model.database.Database
import notion.api.v1.model.database.Databases
import notion.api.v1.model.database.QueryResults
import notion.api.v1.model.error.Error
import notion.api.v1.model.page.Page
import notion.api.v1.model.search.SearchResult
import notion.api.v1.model.search.SearchResults
import notion.api.v1.model.user.User
import notion.api.v1.model.user.Users
import notion.api.v1.request.QueryDatabaseRequest
import notion.api.v1.request.CreatePageRequest
import notion.api.v1.request.SearchRequest

class GsonSerializer : NotionJsonSerializer {
    private val gson: Gson

    constructor(unknownPropertyDetection: Boolean = true) {
        val builder = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .registerTypeAdapter(SearchResult::class.java, SearchResultParser())

        gson = if (unknownPropertyDetection) {
            builder.registerTypeAdapterFactory(GsonUnknownFieldDetection()).create()
        } else {
            builder.create()
        }
    }

    override fun toDatabase(body: String): Database = gson.fromJson(body, Database::class.java)
    override fun toDatabases(body: String): Databases = gson.fromJson(body, Databases::class.java)
    override fun toError(body: String): Error = gson.fromJson(body, Error::class.java)
    override fun toPage(body: String): Page = gson.fromJson(body, Page::class.java)
    override fun toQueryResults(body: String): QueryResults = gson.fromJson(body, QueryResults::class.java)
    override fun toSearchResults(body: String): SearchResults = gson.fromJson(body, SearchResults::class.java)
    override fun toUser(body: String): User = gson.fromJson(body, User::class.java)
    override fun toUsers(body: String): Users = gson.fromJson(body, Users::class.java)
    override fun toJsonString(request: CreatePageRequest): String = gson.toJson(request)
    override fun toJsonString(request: SearchRequest): String = gson.toJson(request)
    override fun toJsonString(request: QueryDatabaseRequest): String = gson.toJson(request)
}