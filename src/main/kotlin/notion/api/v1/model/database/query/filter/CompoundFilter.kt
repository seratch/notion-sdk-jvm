package notion.api.v1.model.database.query.filter

import kotlinx.serialization.Serializable

@Serializable
data class CompoundFilter(
    val or: List<QueryPropertyFilter>? = null,
    val and: List<QueryPropertyFilter>? = null,
) : QueryFilter
