package notion.api.v1.request.databases

import notion.api.v1.model.databases.query.filter.QueryFilter
import notion.api.v1.model.databases.query.sort.QuerySort
import notion.api.v1.request.common.Pagination

data class QueryDatabaseRequest(
    val databaseId: String,
    var filter: QueryFilter? = null,
    var sorts: List<QuerySort>? = null,
    override var startCursor: String? = null,
    override var pageSize: Int? = null,
) : Pagination {

    // For other JVM languages
    constructor(databaseId: String) : this(databaseId, null, null, null, null)
    constructor(databaseId: String, filter: QueryFilter) : this(databaseId, filter, null, null, null)
    constructor(databaseId: String, filter: QueryFilter, sorts: List<QuerySort>) : this(
        databaseId,
        filter,
        sorts,
        null,
        null
    )

}