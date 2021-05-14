package notion.api.v1.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Page(
    override val objectType: String = "page",
    val id: String,
    @SerialName("created_time")
    val createdTime: String,
    @SerialName("last_edited_time")
    val lastEditedTime: String,
    val parent: Parent? = null,
    val archived: Boolean? = false,
    val properties: Map<String, Property>
) : NotionObject {

    @Serializable
    data class Parent(
        val type: String,
        val database_id: String,
    )
}

