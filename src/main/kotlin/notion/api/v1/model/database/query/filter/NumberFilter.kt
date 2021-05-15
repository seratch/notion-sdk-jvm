package notion.api.v1.model.database.query.filter

import kotlinx.serialization.SerialName

interface NumberFilter {
    @SerialName("equals")
    val equals: Int?

    @SerialName("does_not_equal")
    val doesNotEqual: Int?

    @SerialName("greater_than")
    val greaterThan: Int?

    @SerialName("less_than")
    val lessThan: Int?

    @SerialName("greater_than_or_equal_to")
    val greaterThanOrEqualTo: Int?

    @SerialName("less_than_or_equal_to")
    val lessThanOrEqualTo: Int?

    @SerialName("is_empty")
    val isEmpty: Boolean?

    @SerialName("is_not_empty")
    val isNotEmpty: Boolean?
}
