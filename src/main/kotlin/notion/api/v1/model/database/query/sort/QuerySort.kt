package notion.api.v1.model.database.query.sort

import kotlinx.serialization.Serializable

@Serializable
data class QuerySort(
    val property: String? = null,
    val timestamp: String? = null,
    val direction: String? = null,
)