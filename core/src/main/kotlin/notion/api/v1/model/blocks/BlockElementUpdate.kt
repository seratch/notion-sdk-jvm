package notion.api.v1.model.blocks

import notion.api.v1.model.pages.PageProperty

data class BlockElementUpdate
@JvmOverloads
constructor(
    val richText: List<PageProperty.RichText>? = null,
    val checked: Boolean? = null,
    val cells: List<List<PageProperty.RichText>>? = null,
)
