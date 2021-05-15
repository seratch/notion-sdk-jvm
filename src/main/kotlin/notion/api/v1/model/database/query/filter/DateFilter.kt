package notion.api.v1.model.database.query.filter

interface DateFilter {
    val equals: String? // TODO: ISO 8601 date and time
    val before: String? // TODO: ISO 8601 date and time
    val after: String? // TODO: ISO 8601 date and time
    val onOrBefore: String? // TODO: ISO 8601 date and time
    val onOrAfter: String? // TODO: ISO 8601 date and time
    val pastWeek: DateCondition?
    val pastMonth: DateCondition?
    val pastYear: DateCondition?
    val nextWeek: DateCondition?
    val nextMonth: DateCondition?
    val nextYear: DateCondition?
    val isEmpty: Boolean?
    val isNotEmpty: Boolean?

    open class DateCondition
    object EmptyDateCondition : DateCondition()
}

