package notion.api.v1.model.users

import com.google.gson.annotations.SerializedName
import notion.api.v1.model.common.ObjectType

data class User(
    @SerializedName("object") override val objectType: String = "user",
    val id: String,
    val type: String? = null,
    val person: Person? = null,
    val name: String? = null,
    val avatarUrl: String? = null,
    val bot: Bot? = null,
) : ObjectType
