package notion.api.v1.request

import kotlinx.serialization.Serializable

@Serializable
data class SearchRequest(
    val query: String,
    val sort: Sort? = null,
    val filter: Filter? = null,
    val startCursor: String? = null,
    val pageSize: Int? = null,
) {
    @Serializable
    data class Sort(val direction: String? = null, val timestamp: String? = null)

    @Serializable
    data class Filter(val value: String? = null, val property: String? = null)
}
