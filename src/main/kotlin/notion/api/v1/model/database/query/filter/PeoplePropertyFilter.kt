package notion.api.v1.model.database.query.filter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PeoplePropertyFilter(
    override val property: String? = null,
    @SerialName("contains")
    val contains: String? = null,
    @SerialName("does_not_contain")
    val doesNotContain: String? = null,
    @SerialName("is_empty")
    val isEmpty: Boolean? = null,
    @SerialName("is_not_empty")
    val isNotEmpty: Boolean? = null,
) : QueryPropertyFilter
