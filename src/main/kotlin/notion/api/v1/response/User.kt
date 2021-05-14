package notion.api.v1.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
) : NotionObject {
    @Serializable
    data class Person(val email: String)

    @Serializable
    class Bot
}

