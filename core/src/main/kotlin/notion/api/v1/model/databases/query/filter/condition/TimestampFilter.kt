package notion.api.v1.model.databases.query.filter.condition

open class TimestampFilter
@JvmOverloads
constructor(
    var equals: String? = null,
    var before: String? = null,
    var after: String? = null,
    var onOrBefore: String? = null,
    var onOrAfter: String? = null,
    var pastWeek: TimestampCondition? = null,
    var pastMonth: TimestampCondition? = null,
    var pastYear: TimestampCondition? = null,
    var nextWeek: TimestampCondition? = null,
    var nextMonth: TimestampCondition? = null,
    var nextYear: TimestampCondition? = null,
    var isEmpty: Boolean? = null,
    var isNotEmpty: Boolean? = null,
) {
  open class TimestampCondition
  object EmptyDateCondition : TimestampCondition()
}
