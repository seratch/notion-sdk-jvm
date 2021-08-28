package notion.api.v1.model.common

import com.google.gson.annotations.SerializedName

enum class RichTextLinkType @JvmOverloads constructor(val value: String) {
  @SerializedName("url") Url("url");

  override fun toString(): String = value
}
