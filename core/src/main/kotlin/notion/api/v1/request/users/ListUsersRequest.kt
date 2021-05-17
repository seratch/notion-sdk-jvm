package notion.api.v1.request.users

import notion.api.v1.request.common.Pagination

data class ListUsersRequest(
    override var startCursor: String?,
    override var pageSize: Int?
) : Pagination