package notion.api.v1.request.blocks

import notion.api.v1.model.blocks.BlockElementUpdate
import notion.api.v1.model.blocks.BlockType

// the request body must have only the properties at top level
data class UpdateBlockRequest
@JvmOverloads
constructor(
    val blockId: String,
    val elements: Map<BlockType, BlockElementUpdate>,
)
