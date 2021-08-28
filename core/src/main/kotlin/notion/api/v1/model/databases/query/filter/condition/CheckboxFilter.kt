package notion.api.v1.model.databases.query.filter.condition

open class CheckboxFilter
@JvmOverloads
constructor(
    var equals: String? = null,
    var doesNotEqual: String? = null,
)
