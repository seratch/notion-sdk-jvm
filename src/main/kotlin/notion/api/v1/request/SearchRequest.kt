package notion.api.v1.request

import kotlinx.serialization.Serializable
import notion.api.v1.request.cmmon.Pagination

@Serializable
data class SearchRequest(
    val query: String,
    val sort: SearchSort? = null,
    val filter: SeachFilter? = null,
    override val startCursor: String? = null,
    override val pageSize: Int? = null,
) : Pagination {

    @Serializable
    data class SearchSort(
        val direction: String? = null,
        val timestamp: String? = null,
    )

    @Serializable
    data class SeachFilter(
        val value: String? = null,
        val property: String? = null,
    )
}
