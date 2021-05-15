package notion.api.v1.model.database.query.filter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilesPropertyFilter(
    override val property: String? = null,
    @SerialName("is_empty")
    val isEmpty: Boolean? = null,
    @SerialName("is_not_empty")
    val isNotEmpty: Boolean? = null,
) : QueryPropertyFilter
