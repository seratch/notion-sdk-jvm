package notion.api.v1.model.databases.query.filter

data class CheckboxPropertyFilter(
    override val property: String? = null,
    override val equals: String? = null,
    override val doesNotEqual: String? = null,
) : QueryFilter, PropertyFilter, CheckboxFilter