package notion.api.v1.model.database.query.filter

data class FormulaFilter(
    val text: TextFormulaFilter? = null,
    val checkbox: CheckboxFormulaFilter? = null,
    val number: NumberFormulaFilter? = null,
    val date: DateFormulaFilter? = null,
) : QueryFilter