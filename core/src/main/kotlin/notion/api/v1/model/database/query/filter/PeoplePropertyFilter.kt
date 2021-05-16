package notion.api.v1.model.database.query.filter

data class PeoplePropertyFilter(
    override val property: String? = null,
    val contains: String? = null,
    val doesNotContain: String? = null,
    val isEmpty: Boolean? = null,
    val isNotEmpty: Boolean? = null,
) : QueryFilter, PropertyFilter