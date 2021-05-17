package notion.api.v1.model.databases.query.filter

open class CompoundFilter(
    var or: List<PropertyFilter>? = null,
    var and: List<PropertyFilter>? = null,
) : QueryTopLevelFilter
