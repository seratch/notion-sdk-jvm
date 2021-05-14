package notion.api.v1.request

data class UsersRequest(
    override val startCursor: String?,
    override val pageSize: Int?
) : PaginationRequest