package notion.api.v1.model.search

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import notion.api.v1.model.common.HasObjectType
import notion.api.v1.model.database.Database
import notion.api.v1.model.page.Page
import notion.api.v1.model.page.PageParent
import notion.api.v1.model.property.Property

@Serializable
data class SearchResult(
    override val objectType: String,
    val id: String,
    @SerialName("created_time")
    val createdTime: String,
    @SerialName("last_edited_time")
    val lastEditedTime: String,
    val title: List<Property.RichText>?, // only for Database
    val parent: PageParent? = null, // only for Page
    val archived: Boolean? = false,
    val properties: Map<String, Property>
) : HasObjectType {

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