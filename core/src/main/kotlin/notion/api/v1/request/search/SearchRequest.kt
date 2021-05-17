package notion.api.v1.request.search

import notion.api.v1.request.common.Pagination

data class SearchRequest(
    val query: String,
    val filter: SearchFilter? = null,
    val sort: SearchSort? = null,
    override var startCursor: String? = null,
    override var pageSize: Int? = null,
) : Pagination {

    // For other JVM languages
    constructor(query: String) : this(query, null, null, null, null)
    constructor(query: String, filter: SearchFilter) : this(query, filter, null, null, null)
    constructor(query: String, filter: SearchFilter, sort: SearchSort) : this(
        query,
        filter,
        sort,
        null,
        null
    )

    data class SearchFilter(
        val value: String? = null,
        val property: String? = null,
    )

    data class SearchSort(
        val direction: String? = null,
        val timestamp: String? = null,
    )
}
