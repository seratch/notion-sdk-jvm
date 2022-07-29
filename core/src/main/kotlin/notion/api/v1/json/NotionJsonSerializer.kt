package notion.api.v1.json

import notion.api.v1.model.blocks.Block
import notion.api.v1.model.blocks.Blocks
import notion.api.v1.model.comments.Comment
import notion.api.v1.model.comments.Comments
import notion.api.v1.model.databases.Database
import notion.api.v1.model.databases.Databases
import notion.api.v1.model.databases.QueryResults
import notion.api.v1.model.error.Error
import notion.api.v1.model.error.OAuthError
import notion.api.v1.model.oauth.OAuthTokenResult
import notion.api.v1.model.pages.Page
import notion.api.v1.model.pages.PagePropertyItem
import notion.api.v1.model.search.SearchResults
import notion.api.v1.model.users.User
import notion.api.v1.model.users.Users
import notion.api.v1.request.blocks.AppendBlockChildrenRequest
import notion.api.v1.request.comments.CreateCommentRequest
import notion.api.v1.request.databases.CreateDatabaseRequest
import notion.api.v1.request.databases.QueryDatabaseRequest
import notion.api.v1.request.databases.UpdateDatabaseRequest
import notion.api.v1.request.oauth.ExchangeAuthCodeRequest
import notion.api.v1.request.pages.CreatePageRequest
import notion.api.v1.request.pages.UpdatePageRequest
import notion.api.v1.request.search.SearchRequest

interface NotionJsonSerializer {

  fun toBlock(body: String): Block
  fun toBlocks(body: String): Blocks
  fun toComment(body: String): Comment
  fun toComments(body: String): Comments
  fun toDatabase(body: String): Database
  fun toDatabases(body: String): Databases
  fun toError(body: String): Error
  fun toOAuthError(body: String): OAuthError
  fun toPage(body: String): Page
  fun toPagePropertyItem(body: String): PagePropertyItem
  fun toQueryResults(body: String): QueryResults
  fun toSearchResults(body: String): SearchResults
  fun toOAuthTokenResult(body: String): OAuthTokenResult
  fun toUser(body: String): User
  fun toUsers(body: String): Users

  fun toJsonString(request: CreateDatabaseRequest): String
  fun toJsonString(request: UpdateDatabaseRequest): String
  fun toJsonString(blockProperties: Map<String, Any>): String
  fun toJsonString(request: AppendBlockChildrenRequest): String
  fun toJsonString(request: CreatePageRequest): String
  fun toJsonString(request: CreateCommentRequest): String
  fun toJsonString(request: SearchRequest): String
  fun toJsonString(request: QueryDatabaseRequest): String
  fun toJsonString(request: UpdatePageRequest): String
  fun toJsonString(request: ExchangeAuthCodeRequest): String
}
