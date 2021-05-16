package notion.api.v1.request.pages

import notion.api.v1.model.blocks.Block
import notion.api.v1.model.pages.PageProperty

data class CreatePageRequest(
    val parent: Parent,
    val properties: Map<String, PageProperty>,
    val children: List<Block>? = null,
) {

    data class Parent(
        val type: String,
        val databaseId: String? = null,
        val pageId: String? = null,
    )
}
