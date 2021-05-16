package notion.api.v1.model.error

import com.google.gson.annotations.SerializedName
import notion.api.v1.model.common.ObjectType

data class Error(
    @SerializedName("object")
    override val objectType: String = "error",
    val status: Int,
    val code: String,
    val message: String,
) : ObjectType
