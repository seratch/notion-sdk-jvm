package notion.api.v1.request.databases

import notion.api.v1.request.common.Pagination

// var is intentional here (for the easiness in other JVM languages)
data class ListDatabasesRequest
@JvmOverloads
constructor(
    override var startCursor: String? = null,
    override var pageSize: Int? = null,
) : Pagination
