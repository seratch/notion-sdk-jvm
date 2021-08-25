package notion.api.v1.model.search

import notion.api.v1.model.common.Cover
import notion.api.v1.model.common.Icon
import notion.api.v1.model.common.ObjectType
import notion.api.v1.model.common.WithObjectType

interface SearchResult : WithObjectType {
  override val objectType: ObjectType
  val id: String
  val icon: Icon
  val cover: Cover
  val createdTime: String
  val lastEditedTime: String
}
