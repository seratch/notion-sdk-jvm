package notion.api.v1.model.pages

import com.google.gson.annotations.SerializedName
import notion.api.v1.model.common.ObjectType

// This data class does not have setters as developers never manually modify this
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
