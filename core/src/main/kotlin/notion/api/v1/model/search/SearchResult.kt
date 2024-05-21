package notion.api.v1.model.search

import notion.api.v1.model.common.Cover
import notion.api.v1.model.common.Icon
import notion.api.v1.model.common.ObjectType
import notion.api.v1.model.common.WithObjectType
import notion.api.v1.model.users.User

interface SearchResult : WithObjectType {
  override val objectType: ObjectType
  val id: String
  val icon: Icon
  val cover: Cover
  val createdTime: String
  val lastEditedTime: String
  val createdBy: User
  val lastEditedBy: User
  val archived: Boolean
  val requestId: String?
  var inTrash: Boolean?

  fun asDatabase(): DatabaseSearchResult =
      if (objectType == ObjectType.Database) this as DatabaseSearchResult
      else
          throw IllegalStateException(
              "Failed to cast $objectType search result to DatabaseSearchResult")

  fun asPage(): PageSearchResult =
      if (objectType == ObjectType.Page) this as PageSearchResult
      else
          throw IllegalStateException(
              "Failed to cast $objectType search result to PageSearchResult")
}
