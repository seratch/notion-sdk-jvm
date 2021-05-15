package notion.api.v1.model.database.query.filter

import kotlinx.serialization.Serializable

@Serializable
data class CheckboxPropertyFilter(
    override val property: String? = null,
    override val equals: String? = null,
    override val doesNotEqual: String? = null,
) : QueryPropertyFilter, CheckboxFilter
