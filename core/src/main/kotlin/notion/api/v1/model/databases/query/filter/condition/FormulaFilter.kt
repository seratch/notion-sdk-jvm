package notion.api.v1.model.databases.query.filter.condition

open class FormulaFilter
@JvmOverloads
constructor(
    var property: String? = null,
    var text: TextFilter? = null,
    var checkbox: CheckboxFilter? = null,
    var number: NumberFilter? = null,
    var date: DateFilter? = null,
)
