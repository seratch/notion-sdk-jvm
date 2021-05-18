package notion.api.v1.model.databases

import com.google.gson.annotations.SerializedName
import notion.api.v1.model.common.ObjectType
import notion.api.v1.model.common.Pagination
import notion.api.v1.model.common.WithObjectType
import notion.api.v1.model.pages.Page

// This data class does not have setters as developers never manually modify this
data class QueryResults(
    @SerializedName("object") override val objectType: ObjectType = ObjectType.List,
    val results: List<Page>,
    override val nextCursor: String? = null,
    override val hasMore: Boolean = false,
) : WithObjectType, Pagination
