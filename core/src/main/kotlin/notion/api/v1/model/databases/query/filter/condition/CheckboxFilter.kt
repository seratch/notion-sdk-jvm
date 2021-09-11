package notion.api.v1.model.databases.query.filter.condition

open class CheckboxFilter
@JvmOverloads
constructor(
    var equals: Boolean? = null,
    var doesNotEqual: Boolean? = null,
)
