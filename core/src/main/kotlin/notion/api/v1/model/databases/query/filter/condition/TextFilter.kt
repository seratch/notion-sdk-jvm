package notion.api.v1.model.databases.query.filter.condition

open class TextFilter
@JvmOverloads
constructor(
    var equals: String? = null,
    var doesNotEqual: String? = null,
    var contains: String? = null,
    var doesNotContain: String? = null,
    var startsWith: String? = null,
    var endsWith: String? = null,
    var isEmpty: Boolean? = null,
    var isNotEmpty: Boolean? = null,
)
