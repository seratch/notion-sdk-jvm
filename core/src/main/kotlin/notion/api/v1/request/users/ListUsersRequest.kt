package notion.api.v1.request.users

import notion.api.v1.request.common.Pagination

data class ListUsersRequest(
    override val startCursor: String?,
    override val pageSize: Int?
) : Pagination