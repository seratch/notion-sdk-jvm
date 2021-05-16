package notion.api.v1.model.databases.query.filter

data class CompoundFilter(
    val or: List<QueryFilter>? = null,
    val and: List<QueryFilter>? = null,
) : QueryFilter