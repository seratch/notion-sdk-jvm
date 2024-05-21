package notion.api.v1.model.search

import com.google.gson.annotations.SerializedName
import notion.api.v1.model.common.Cover
import notion.api.v1.model.common.Icon
import notion.api.v1.model.common.ObjectType
import notion.api.v1.model.databases.DatabaseParent
import notion.api.v1.model.databases.DatabaseProperty
import notion.api.v1.model.users.User

data class DatabaseSearchResult
@JvmOverloads
constructor(
    @SerializedName("object") override val objectType: ObjectType = ObjectType.Database,
    override val id: String,
    override val icon: Icon,
    override val cover: Cover,
    override val createdTime: String,
    override val createdBy: User,
    override val lastEditedTime: String,
    override val lastEditedBy: User,
    override val archived: Boolean,
    val url: String,
    val publicUrl: String? = null,
    val parent: DatabaseParent,
    val title: List<DatabaseProperty.RichText>?,
    val description: List<DatabaseProperty.RichText>?,
    @SerializedName("is_inline") val inline: Boolean,
    override var inTrash: Boolean? = null,
    val properties: Map<String, DatabaseProperty>,
    override val requestId: String? = null,
) : SearchResult
