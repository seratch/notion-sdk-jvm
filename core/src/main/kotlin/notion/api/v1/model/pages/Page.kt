package notion.api.v1.model.pages

import com.google.gson.annotations.SerializedName
import notion.api.v1.model.common.Cover
import notion.api.v1.model.common.Icon
import notion.api.v1.model.common.ObjectType
import notion.api.v1.model.common.WithObjectType

// This data class does not have setters as developers never manually modify this
data class Page
@JvmOverloads
constructor(
    @SerializedName("object") override val objectType: ObjectType = ObjectType.Page,
    val id: String,
    val icon: Icon,
    val cover: Cover,
    val createdTime: String,
    val lastEditedTime: String,
    val url: String,
    val parent: PageParent? = null,
    val archived: Boolean? = false,
    val properties: Map<String, PageProperty>
) : WithObjectType
