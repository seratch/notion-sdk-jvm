package notion.api.v1.model.search

import com.google.gson.annotations.SerializedName
import notion.api.v1.model.page.PageParent
import notion.api.v1.model.page.PageProperty

data class PageSearchResult(
    @SerializedName("object")
    override val objectType: String = "page",
    override val id: String,
    override val createdTime: String,
    override val lastEditedTime: String,
    val parent: PageParent? = null,
    val archived: Boolean? = false,
    val properties: Map<String, PageProperty>,
) : SearchResult