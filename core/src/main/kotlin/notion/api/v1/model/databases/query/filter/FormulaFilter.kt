package notion.api.v1.model.databases.query.filter

data class FormulaFilter(
    val text: TextFormulaFilter? = null,
    val checkbox: CheckboxFormulaFilter? = null,
    val number: NumberFormulaFilter? = null,
    val date: DateFormulaFilter? = null,
) : QueryFilter