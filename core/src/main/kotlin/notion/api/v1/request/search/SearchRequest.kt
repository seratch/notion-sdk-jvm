package notion.api.v1.request.search

import notion.api.v1.request.common.Pagination

data class SearchRequest(
    val query: String,
    val sort: SearchSort? = null,
    val filter: SearchFilter? = null,
    override val startCursor: String? = null,
    override val pageSize: Int? = null,
) : Pagination {

    data class SearchSort(
        val direction: String? = null,
        val timestamp: String? = null,
    )

    data class SearchFilter(
        val value: String? = null,
        val property: String? = null,
    )
}
