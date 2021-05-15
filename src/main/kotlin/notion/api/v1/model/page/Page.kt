package notion.api.v1.model.page

import com.google.gson.annotations.SerializedName
import notion.api.v1.model.common.ObjectType

data class Page(
    @SerializedName("object")
    override val objectType: String = "page",
    val id: String,
    val createdTime: String,
    val lastEditedTime: String,
    val parent: PageParent? = null,
    val archived: Boolean? = false,
    val properties: Map<String, PageProperty>
) : ObjectType
