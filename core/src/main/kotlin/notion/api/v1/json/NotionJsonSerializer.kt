package notion.api.v1.json

import notion.api.v1.model.blocks.Block
import notion.api.v1.model.blocks.Blocks
import notion.api.v1.model.databases.Database
import notion.api.v1.model.databases.Databases
import notion.api.v1.model.databases.QueryResults
import notion.api.v1.model.error.Error
import notion.api.v1.model.pages.Page
import notion.api.v1.model.search.SearchResults
import notion.api.v1.model.users.User
import notion.api.v1.model.users.Users
import notion.api.v1.request.blocks.AppendBlockChildrenRequest
import notion.api.v1.request.databases.QueryDatabaseRequest
import notion.api.v1.request.pages.CreatePageRequest
import notion.api.v1.request.pages.UpdatePagePropertiesRequest
import notion.api.v1.request.search.SearchRequest

interface NotionJsonSerializer {

    fun toBlock(body: String): Block
    fun toBlocks(body: String): Blocks
    fun toDatabase(body: String): Database
    fun toDatabases(body: String): Databases
    fun toError(body: String): Error
    fun toPage(body: String): Page
    fun toQueryResults(body: String): QueryResults
    fun toSearchResults(body: String): SearchResults
    fun toUser(body: String): User
    fun toUsers(body: String): Users

    fun toJsonString(request: AppendBlockChildrenRequest): String
    fun toJsonString(request: CreatePageRequest): String
    fun toJsonString(request: SearchRequest): String
    fun toJsonString(request: QueryDatabaseRequest): String
    fun toJsonString(request: UpdatePagePropertiesRequest): String
}