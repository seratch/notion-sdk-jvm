package notion.api.v1.request.blocks

import notion.api.v1.model.blocks.Block

data class AppendBlockChildrenRequest
@JvmOverloads
constructor(
    @Transient val blockId: String,
    val children: List<Block>,
)
