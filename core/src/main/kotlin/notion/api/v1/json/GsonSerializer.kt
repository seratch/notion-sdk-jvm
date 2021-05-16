package notion.api.v1.json

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import notion.api.v1.model.blocks.Block
import notion.api.v1.model.blocks.Blocks
import notion.api.v1.model.databases.Database
import notion.api.v1.model.databases.Databases
import notion.api.v1.model.databases.QueryResults
import notion.api.v1.model.error.Error
import notion.api.v1.model.pages.Page
import notion.api.v1.model.search.SearchResult
import notion.api.v1.model.search.SearchResults
import notion.api.v1.model.users.User
import notion.api.v1.model.users.Users
import notion.api.v1.request.blocks.AppendBlockChildrenRequest
import notion.api.v1.request.databases.QueryDatabaseRequest
import notion.api.v1.request.pages.CreatePageRequest
import notion.api.v1.request.pages.UpdatePagePropertiesRequest
import notion.api.v1.request.search.SearchRequest

class GsonSerializer : NotionJsonSerializer {
    private val gson: Gson

    constructor(unknownPropertyDetection: Boolean = true) {
        val builder = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .registerTypeAdapter(SearchResult::class.java, SearchResultParser())
            .registerTypeAdapter(Block::class.java, BlockParser())

        gson = if (unknownPropertyDetection) {
            builder.registerTypeAdapterFactory(GsonUnknownFieldDetection()).create()
        } else {
            builder.create()
        }
    }

    override fun toBlock(body: String): Block = gson.fromJson(body, Block::class.java)
    override fun toBlocks(body: String): Blocks = gson.fromJson(body, Blocks::class.java)
    override fun toDatabase(body: String): Database = gson.fromJson(body, Database::class.java)
    override fun toDatabases(body: String): Databases = gson.fromJson(body, Databases::class.java)
    override fun toError(body: String): Error = gson.fromJson(body, Error::class.java)
    override fun toPage(body: String): Page = gson.fromJson(body, Page::class.java)
    override fun toQueryResults(body: String): QueryResults = gson.fromJson(body, QueryResults::class.java)
    override fun toSearchResults(body: String): SearchResults = gson.fromJson(body, SearchResults::class.java)
    override fun toUser(body: String): User = gson.fromJson(body, User::class.java)
    override fun toUsers(body: String): Users = gson.fromJson(body, Users::class.java)

    override fun toJsonString(request: AppendBlockChildrenRequest): String = gson.toJson(request)
    override fun toJsonString(request: CreatePageRequest): String = gson.toJson(request)
    override fun toJsonString(request: SearchRequest): String = gson.toJson(request)
    override fun toJsonString(request: QueryDatabaseRequest): String = gson.toJson(request)
    override fun toJsonString(request: UpdatePagePropertiesRequest): String = gson.toJson(request)
}