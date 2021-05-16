package notion.api.v1.request.databases

import notion.api.v1.model.databases.query.filter.QueryFilter
import notion.api.v1.model.databases.query.sort.QuerySort
import notion.api.v1.request.common.Pagination

data class QueryDatabaseRequest(
    val databaseId: String,
    val filter: QueryFilter? = null,
    val sorts: List<QuerySort>? = null,
    override val startCursor: String? = null,
    override val pageSize: Int? = null,
) : Pagination