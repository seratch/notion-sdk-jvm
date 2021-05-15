package notion.api.v1.model.database.query.filter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface DateFilter {
    @SerialName("equals")
    val equals: String? // TODO: ISO 8601 date and time

    @SerialName("before")
    val before: String? // TODO: ISO 8601 date and time

    @SerialName("after")
    val after: String? // TODO: ISO 8601 date and time

    @SerialName("on_or_before")
    val onOrBefore: String? // TODO: ISO 8601 date and time

    @SerialName("on_or_after")
    val onOrAfter: String? // TODO: ISO 8601 date and time

    @SerialName("past_week")
    val pastWeek: DateCondition?

    @SerialName("past_month")
    val pastMonth: DateCondition?

    @SerialName("past_year")
    val pastYear: DateCondition?

    @SerialName("next_week")
    val nextWeek: DateCondition?

    @SerialName("next_month")
    val nextMonth: DateCondition?

    @SerialName("next_year")
    val nextYear: DateCondition?

    @SerialName("is_empty")
    val isEmpty: Boolean?

    @SerialName("is_not_empty")
    val isNotEmpty: Boolean?

    @Serializable
    open class DateCondition
    object EmptyDateCondition : DateCondition()
}

