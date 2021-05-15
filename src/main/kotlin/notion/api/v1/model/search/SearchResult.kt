package notion.api.v1.model.search

import notion.api.v1.model.common.ObjectType

interface SearchResult : ObjectType {
    override val objectType: String
    val id: String
    val createdTime: String
    val lastEditedTime: String
}
