package notion.api.v1.model.database

import com.google.gson.annotations.SerializedName
import notion.api.v1.model.common.ObjectType
import notion.api.v1.model.common.Pagination

data class Databases(
    @SerializedName("object")
    override val objectType: String = "list",
    val results: List<Database>,
    override val nextCursor: String? = null,
    override val hasMore: Boolean = false,
) : ObjectType, Pagination
