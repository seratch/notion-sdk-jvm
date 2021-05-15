package notion.api.v1.model.page

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import notion.api.v1.model.common.HasObjectType
import notion.api.v1.model.property.Property

@Serializable
data class Page(
    override val objectType: String = "page",
    val id: String,
    @SerialName("created_time")
    val createdTime: String,
    @SerialName("last_edited_time")
    val lastEditedTime: String,
    val parent: PageParent? = null,
    val archived: Boolean? = false,
    val properties: Map<String, Property>
) : HasObjectType
