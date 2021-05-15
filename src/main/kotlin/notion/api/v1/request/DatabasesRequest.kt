package notion.api.v1.request

import notion.api.v1.request.common.Pagination

data class DatabasesRequest(
    override val startCursor: String? = null,
    override val pageSize: Int? = null,
) : Pagination