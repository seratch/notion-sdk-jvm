package notion.api.v1.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Database(
    override val objectType: String = "database",
    val id: String,
    @SerialName("created_time")
    val createdTime: String,
    @SerialName("last_edited_time")
    val lastEditedTime: String,
    val title: List<Property.RichText>?,
    val properties: Map<String, Property>
) : NotionObject
