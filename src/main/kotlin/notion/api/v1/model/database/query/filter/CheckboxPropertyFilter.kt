package notion.api.v1.model.database.query.filter

data class CheckboxPropertyFilter(
    override val property: String? = null,
    override val equals: String? = null,
    override val doesNotEqual: String? = null,
) : QueryFilter, PropertyFilter, CheckboxFilter