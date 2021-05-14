package notion.api.v1.request

import kotlinx.serialization.Serializable

@Serializable
data class DatabasesRequest(
    override val startCursor: String?,
    override val pageSize: Int?
) : PaginationRequest