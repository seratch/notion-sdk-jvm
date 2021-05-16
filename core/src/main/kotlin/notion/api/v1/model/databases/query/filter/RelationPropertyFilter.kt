package notion.api.v1.model.databases.query.filter

data class RelationPropertyFilter(
    override val property: String? = null,
    val contains: String? = null,
    val doesNotContain: String? = null,
    val isEmpty: Boolean? = null,
    val isNotEmpty: Boolean? = null,
) : QueryFilter, PropertyFilter