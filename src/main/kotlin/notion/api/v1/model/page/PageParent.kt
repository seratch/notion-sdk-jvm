package notion.api.v1.model.page

import kotlinx.serialization.Serializable

@Serializable
data class PageParent(
    val type: String,
    val database_id: String,
)
