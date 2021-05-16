package notion.api.v1.request

import notion.api.v1.model.block.Block
import notion.api.v1.model.page.PagePropertyArg

data class CreatePageRequest(
    val parent: Parent,
    val properties: Map<String, PagePropertyArg>,
    val children: List<Block>? = null,
) {

    data class Parent(
        val type: String,
        val databaseId: String? = null,
        val pageId: String? = null,
    )
}
