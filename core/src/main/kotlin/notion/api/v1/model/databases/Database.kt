package notion.api.v1.model.databases

import com.google.gson.annotations.SerializedName
import notion.api.v1.model.common.ObjectType

data class Database(
    @SerializedName("object")
    override val objectType: String = "database",
    val id: String,
    val createdTime: String,
    val lastEditedTime: String,
    val title: List<DatabaseProperty.RichText>?,
    val properties: Map<String, DatabaseProperty>
) : ObjectType
