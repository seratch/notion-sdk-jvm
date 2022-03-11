package notion.api.v1.model.databases.query.filter

open class CompoundFilter
@JvmOverloads
constructor(
    var or: List<CompoundFilterElement>? = null,
    var and: List<CompoundFilterElement>? = null,
) : QueryTopLevelFilter, CompoundFilterElement
