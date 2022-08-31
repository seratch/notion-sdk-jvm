package notion.api.v1.model.search

import com.google.gson.annotations.SerializedName
import notion.api.v1.model.common.Cover
import notion.api.v1.model.common.Icon
import notion.api.v1.model.common.ObjectType
import notion.api.v1.model.pages.PageProperty
import notion.api.v1.model.users.User

data class PageSearchResult
@JvmOverloads
constructor(
    @SerializedName("object") override val objectType: ObjectType = ObjectType.Page,
    override val id: String,
    override val icon: Icon,
    override val cover: Cover,
    override val createdTime: String,
    override val createdBy: User,
    override val lastEditedTime: String,
    override val lastEditedBy: User,
    val url: String,
    val parent: PageSearchResultParent? = null,
    override val archived: Boolean = false,
    val properties: Map<String, PageProperty>,
) : SearchResult
