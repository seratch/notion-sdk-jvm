package notion.api.v1.model.users

import com.google.gson.annotations.SerializedName
import notion.api.v1.model.common.ObjectType
import notion.api.v1.model.common.WithObjectType

data class User
@JvmOverloads
constructor(
    @SerializedName("object") override val objectType: ObjectType = ObjectType.User,
    val id: String,
    val type: UserType? = null,
    val person: Person? = null,
    val name: String? = null,
    val avatarUrl: String? = null,
    val bot: Bot? = null,
    val requestId: String? = null,
) : WithObjectType
