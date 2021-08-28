package notion.api.v1.model.search

import com.google.gson.annotations.SerializedName
import notion.api.v1.model.common.Cover
import notion.api.v1.model.common.Icon
import notion.api.v1.model.common.ObjectType
import notion.api.v1.model.pages.PageParent
import notion.api.v1.model.pages.PageProperty

data class PageSearchResult
@JvmOverloads
constructor(
    @SerializedName("object") override val objectType: ObjectType = ObjectType.Page,
    override val id: String,
    override val icon: Icon,
    override val cover: Cover,
    override val createdTime: String,
    override val lastEditedTime: String,
    val url: String,
    val parent: PageParent? = null,
    val archived: Boolean? = false,
    val properties: Map<String, PageProperty>,
) : SearchResult
