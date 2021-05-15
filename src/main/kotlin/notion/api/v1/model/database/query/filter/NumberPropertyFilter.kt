package notion.api.v1.model.database.query.filter

import kotlinx.serialization.Serializable

@Serializable
data class NumberPropertyFilter(
    override val property: String? = null,
    override val equals: Int? = null,
    override val doesNotEqual: Int? = null,
    override val greaterThan: Int? = null,
    override val lessThan: Int? = null,
    override val greaterThanOrEqualTo: Int? = null,
    override val lessThanOrEqualTo: Int? = null,
    override val isEmpty: Boolean? = null,
    override val isNotEmpty: Boolean? = null,
) : QueryPropertyFilter, NumberFilter
