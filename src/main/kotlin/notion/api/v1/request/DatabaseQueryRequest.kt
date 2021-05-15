package notion.api.v1.request

import notion.api.v1.model.database.query.filter.QueryFilter
import notion.api.v1.model.database.query.sort.QuerySort
import notion.api.v1.request.common.Pagination

data class DatabaseQueryRequest(
    val databaseId: String,
    val filter: QueryFilter? = null,
    val sorts: List<QuerySort>? = null,
    override val startCursor: String? = null,
    override val pageSize: Int? = null,
) : Pagination