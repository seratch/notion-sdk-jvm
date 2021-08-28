package notion.api.v1.model.databases.query.filter.condition

open class PeopleFilter
@JvmOverloads
constructor(
    var contains: String? = null,
    var doesNotContain: String? = null,
    var isEmpty: Boolean? = null,
    var isNotEmpty: Boolean? = null,
)
