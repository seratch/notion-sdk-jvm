package notion.api.v1.model.error

import com.google.gson.annotations.SerializedName
import notion.api.v1.model.common.ObjectType

// This data class does not have setters as developers never manually modify this
data class Error(
    @SerializedName("object")
    override val objectType: String = "error",
    val status: Int,
    val code: String,
    val message: String,
) : ObjectType
