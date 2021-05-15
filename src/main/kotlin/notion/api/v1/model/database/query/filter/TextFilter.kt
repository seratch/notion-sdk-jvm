package notion.api.v1.model.database.query.filter

import kotlinx.serialization.SerialName

interface TextFilter {
    @SerialName("equals")
    val equals: String?

    @SerialName("does_not_equal")
    val doesNotEqual: String?

    @SerialName("contains")
    val contains: String?

    @SerialName("does_not_contain")
    val doesNotContain: String?

    @SerialName("starts_with")
    val startsWith: String?

    @SerialName("ends_with")
    val endsWith: String?

    @SerialName("is_empty")
    val isEmpty: Boolean?

    @SerialName("is_not_empty")
    val isNotEmpty: Boolean?
}