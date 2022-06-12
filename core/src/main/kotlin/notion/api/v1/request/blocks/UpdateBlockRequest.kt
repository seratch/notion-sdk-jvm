package notion.api.v1.request.blocks

import notion.api.v1.model.blocks.BlockElementUpdate
import notion.api.v1.model.blocks.BlockType

data class UpdateBlockRequest
@JvmOverloads
constructor(
    val blockId: String, // path param
    val elements: Map<BlockType, BlockElementUpdate>, // in body
    val type: String? = null, // in body
    val archived: Boolean? = null, // in body
)
