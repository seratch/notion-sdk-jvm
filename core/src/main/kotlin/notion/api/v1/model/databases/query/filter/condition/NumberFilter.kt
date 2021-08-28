package notion.api.v1.model.databases.query.filter.condition

open class NumberFilter
@JvmOverloads
constructor(
    var equals: Int? = null,
    var doesNotEqual: Int? = null,
    var greaterThan: Int? = null,
    var lessThan: Int? = null,
    var greaterThanOrEqualTo: Int? = null,
    var lessThanOrEqualTo: Int? = null,
    var isEmpty: Boolean? = null,
    var isNotEmpty: Boolean? = null,
)
