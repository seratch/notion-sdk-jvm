package notion.api.v1.model.blocks

import com.google.gson.annotations.SerializedName
import notion.api.v1.model.common.ObjectType
import notion.api.v1.model.common.Pagination
import notion.api.v1.model.common.WithObjectType

// This data class does not have setters as developers never manually modify this
data class Blocks
@JvmOverloads
constructor(
    @SerializedName("object") override val objectType: ObjectType = ObjectType.List,
    val results: List<Block>,
    override val nextCursor: String? = null,
    override val hasMore: Boolean = false,
    val type: String = "block",
    val block: Object = Object(),
    val requestId: String? = null,
) : WithObjectType, Pagination
