package notion.api.v1.model.user

import com.google.gson.annotations.SerializedName
import notion.api.v1.model.common.ObjectType

data class User(
    @SerializedName("object")
    override val objectType: String = "user",
    val id: String,
    val type: String,
    val person: Person? = null,
    val name: String,
    val avatarUrl: String? = null,
    val bot: Bot? = null,
) : ObjectType