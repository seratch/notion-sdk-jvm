package notion.api.v1.model.database.query.filter

data class NumberFormulaFilter(
    override val equals: Int? = null,
    override val doesNotEqual: Int? = null,
    override val greaterThan: Int? = null,
    override val lessThan: Int? = null,
    override val greaterThanOrEqualTo: Int? = null,
    override val lessThanOrEqualTo: Int? = null,
    override val isEmpty: Boolean? = null,
    override val isNotEmpty: Boolean? = null,
) : QueryFilter, NumberFilter