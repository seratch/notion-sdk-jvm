package notion.api.v1.model.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import notion.api.v1.model.common.HasObjectType

@Serializable
data class User(
    override val objectType: String = "user",
    val id: String,
    val type: String,
    val person: Person? = null,
    val name: String,
    @SerialName("avatar_url")
    val avatarUrl: String? = null,
    val bot: Bot? = null,
) : HasObjectType