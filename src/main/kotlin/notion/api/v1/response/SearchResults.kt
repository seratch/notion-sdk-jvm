package notion.api.v1.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResults(
    override val objectType: String = "list",
    val results: List<SearchResult>,
    @SerialName("next_cursor")
    val nextCursor: String? = null,
    @SerialName("has_more")
    val hasMore: Boolean = false,
) : NotionObject {
    @Serializable
    data class SearchResult(
        override val objectType: String,
        val id: String,
        @SerialName("created_time")
        val createdTime: String,
        @SerialName("last_edited_time")
        val lastEditedTime: String,
        val title: List<Property.RichText>?, // only for Database
        val parent: Page.Parent? = null, // only for Page
        val archived: Boolean? = false,
        val properties: Map<String, Property>
    ) : NotionObject {

        fun isPage(): Boolean = objectType == "page"
        fun isDatabase(): Boolean = objectType == "database"

        fun toPage(): Page {
            if (isPage()) {
                return Page(
                    id = id,
                    createdTime = createdTime,
                    lastEditedTime = lastEditedTime,
                    parent = parent,
                    archived = archived,
                    properties = properties,
                )
            } else {
                throw RuntimeException("TODO: change this")
            }
        }

        fun toDatabase(): Database {
            if (isDatabase()) {
                return Database(
                    id = id,
                    createdTime = createdTime,
                    lastEditedTime = lastEditedTime,
                    title = title,
                    properties = properties,
                )
            } else {
                throw RuntimeException("TODO: change this")
            }
        }
    }
}