package notion.api.v1.model.databases.query.filter.condition

open class DateFilter
@JvmOverloads
constructor(
    var equals: String? = null,
    var before: String? = null,
    var after: String? = null,
    var onOrBefore: String? = null,
    var onOrAfter: String? = null,
    var pastWeek: DateCondition? = null,
    var pastMonth: DateCondition? = null,
    var pastYear: DateCondition? = null,
    var nextWeek: DateCondition? = null,
    var nextMonth: DateCondition? = null,
    var nextYear: DateCondition? = null,
    var isEmpty: Boolean? = null,
    var isNotEmpty: Boolean? = null,
) {
  open class DateCondition
  object EmptyDateCondition : DateCondition()
}
