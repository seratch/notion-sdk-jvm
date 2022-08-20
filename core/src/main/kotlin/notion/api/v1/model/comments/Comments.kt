package notion.api.v1.model.comments

import com.google.gson.annotations.SerializedName
import notion.api.v1.model.common.ObjectType
import notion.api.v1.model.common.Pagination
import notion.api.v1.model.common.WithObjectType

// This data class does not have setters as developers never manually modify this
data class Comments
@JvmOverloads
constructor(
    @SerializedName("object") override val objectType: ObjectType = ObjectType.List,
    val results: List<Comment>,
    override val nextCursor: String? = null,
    override val hasMore: Boolean = false,
    val type: String = "comment",
    val comment: Any? = null, // We may revisit Any type here once the Notion API uses it in a meaningful way
) : WithObjectType, Pagination
