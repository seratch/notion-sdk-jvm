package notion.api.v1.request

import kotlinx.serialization.Serializable
import notion.api.v1.request.cmmon.Pagination

@Serializable
data class DatabasesRequest(
    override val startCursor: String? = null,
    override val pageSize: Int? = null,
) : Pagination