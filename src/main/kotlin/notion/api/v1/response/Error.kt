package notion.api.v1.response

import kotlinx.serialization.Serializable

@Serializable
data class Error(
    override val objectType: String = "error",
    val status: Int,
    val code: String,
    val message: String,
) : NotionObject
