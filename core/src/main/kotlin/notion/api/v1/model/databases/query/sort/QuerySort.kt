package notion.api.v1.model.databases.query.sort

data class QuerySort(
    val property: String? = null,
    val timestamp: String? = null,
    val direction: String? = null,
)