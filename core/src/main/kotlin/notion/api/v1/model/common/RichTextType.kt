package notion.api.v1.model.common

import com.google.gson.annotations.SerializedName

enum class RichTextType(val value: String) {
  @SerializedName("text") Text("text"),
  @SerializedName("equation") Equation("equation"),
  @SerializedName("mention") Mention("mention");

  override fun toString(): String = value
}
