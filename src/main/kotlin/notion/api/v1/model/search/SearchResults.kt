package notion.api.v1.model.search

import kotlinx.serialization.Serializable
import notion.api.v1.model.common.HasObjectType
import notion.api.v1.model.common.Pagination

@Serializable
data class SearchResults(
    override val objectType: String = "list",
    val results: List<SearchResult>,
    override val nextCursor: String? = null,
    override val hasMore: Boolean = false,
) : HasObjectType, Pagination