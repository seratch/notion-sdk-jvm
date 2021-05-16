package notion.api.v1.model.database

import com.google.gson.annotations.SerializedName
import notion.api.v1.model.common.ObjectType
import notion.api.v1.model.common.property.RichText

data class Database(
    @SerializedName("object")
    override val objectType: String = "database",
    val id: String,
    val createdTime: String,
    val lastEditedTime: String,
    val title: List<RichText>?,
    val properties: Map<String, DatabaseProperty>
) : ObjectType
