package notion.api.v1.model.databases.query.sort

import notion.api.v1.model.common.PropertyType

open class QuerySort
@JvmOverloads
constructor(
    var property: String? = null,
    var timestamp: QuerySortTimestamp? = null,
    var direction: QuerySortDirection? = QuerySortDirection.Ascending,
)
