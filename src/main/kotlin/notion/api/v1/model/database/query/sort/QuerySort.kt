package notion.api.v1.model.database.query.sort

data class QuerySort(
    val property: String? = null,
    val timestamp: String? = null,
    val direction: String? = null,
)