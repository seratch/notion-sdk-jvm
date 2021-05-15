package notion.api.v1.model.error

import kotlinx.serialization.Serializable
import notion.api.v1.model.common.HasObjectType

@Serializable
data class Error(
    override val objectType: String = "error",
    val status: Int,
    val code: String,
    val message: String,
) : HasObjectType
