package notion.api.v1.model.database

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import notion.api.v1.model.common.HasObjectType
import notion.api.v1.model.property.Property

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
) : HasObjectType
