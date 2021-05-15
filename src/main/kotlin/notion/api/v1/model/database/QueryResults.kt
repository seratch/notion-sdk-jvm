package notion.api.v1.model.database

import com.google.gson.annotations.SerializedName
import notion.api.v1.model.common.ObjectType
import notion.api.v1.model.common.Pagination
import notion.api.v1.model.page.Page

data class QueryResults(
    @SerializedName("object")
    override val objectType: String = "list",
    val results: List<Page>,
    override val nextCursor: String? = null,
    override val hasMore: Boolean = false,
) : ObjectType, Pagination
