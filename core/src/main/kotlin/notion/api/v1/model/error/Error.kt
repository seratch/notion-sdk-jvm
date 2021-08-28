package notion.api.v1.model.error

import com.google.gson.annotations.SerializedName
import notion.api.v1.model.common.ObjectType
import notion.api.v1.model.common.WithObjectType

// This data class does not have setters as developers never manually modify this
data class Error
@JvmOverloads
constructor(
    @SerializedName("object") override val objectType: ObjectType = ObjectType.Error,
    val status: Int,
    val code: String,
    val message: String,
) : WithObjectType
