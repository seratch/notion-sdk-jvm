package notion.api.v1.model.search

import com.google.gson.annotations.SerializedName
import notion.api.v1.model.pages.PageParent
import notion.api.v1.model.pages.PageProperty

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