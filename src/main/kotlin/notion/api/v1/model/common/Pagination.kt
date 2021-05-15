package notion.api.v1.model.common

import kotlinx.serialization.SerialName

interface Pagination {
    @SerialName("next_cursor")
    val nextCursor: String?

    @SerialName("has_more")
    val hasMore: Boolean?
}