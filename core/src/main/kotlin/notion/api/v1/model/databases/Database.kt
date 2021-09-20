package notion.api.v1.model.databases

import com.google.gson.annotations.SerializedName
import notion.api.v1.model.common.*

// This data class does not have setters as developers never manually modify this
data class Database
@JvmOverloads
constructor(
    @SerializedName("object") override val objectType: ObjectType = ObjectType.Database,
    val id: String,
    val icon: Icon,
    val cover: Cover,
    val url: String,
    val createdTime: String,
    val lastEditedTime: String,
    val parent: DatabaseParent? = null,
    val title: List<DatabaseProperty.RichText>,
    val properties: Map<String, DatabaseProperty>
) : WithObjectType
