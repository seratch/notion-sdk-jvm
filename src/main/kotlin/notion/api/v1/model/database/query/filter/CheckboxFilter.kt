package notion.api.v1.model.database.query.filter

import kotlinx.serialization.SerialName

interface CheckboxFilter {
    @SerialName("equals")
    val equals: String?

    @SerialName("does_not_equal")
    val doesNotEqual: String?
}
