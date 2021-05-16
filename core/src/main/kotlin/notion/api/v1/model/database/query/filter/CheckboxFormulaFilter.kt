package notion.api.v1.model.database.query.filter

data class CheckboxFormulaFilter(
    override val equals: String? = null,
    override val doesNotEqual: String? = null,
) : QueryFilter, CheckboxFilter