package notion.api.v1.request.databases

import notion.api.v1.model.databases.query.filter.QueryTopLevelFilter
import notion.api.v1.model.databases.query.sort.QuerySort
import notion.api.v1.request.common.Pagination

// TODO: The document says filter_properties works in this request body, but it didn't work for us
// as of Jan 2023.
data class QueryDatabaseRequest
@JvmOverloads
constructor(
    @Transient val databaseId: String,
    var filter: QueryTopLevelFilter? = null,
    var sorts: List<QuerySort>? = null,
    override var startCursor: String? = null,
    override var pageSize: Int? = null,
) : Pagination
