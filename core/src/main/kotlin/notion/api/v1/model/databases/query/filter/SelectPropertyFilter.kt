package notion.api.v1.model.databases.query.filter

data class SelectPropertyFilter(
    override val property: String? = null,
    val equals: String? = null,
    val doesNotEqual: String? = null,
    val isEmpty: Boolean? = null,
    val isNotEmpty: Boolean? = null,
) : QueryFilter, PropertyFilter