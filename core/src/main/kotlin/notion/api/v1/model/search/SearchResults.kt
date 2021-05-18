package notion.api.v1.model.search

import com.google.gson.annotations.SerializedName
import notion.api.v1.model.common.ObjectType
import notion.api.v1.model.common.Pagination
import notion.api.v1.model.common.WithObjectType

data class SearchResults(
    @SerializedName("object") override val objectType: ObjectType = ObjectType.List,
    val results: List<SearchResult>,
    override val nextCursor: String? = null,
    override val hasMore: Boolean = false,
) : WithObjectType, Pagination
