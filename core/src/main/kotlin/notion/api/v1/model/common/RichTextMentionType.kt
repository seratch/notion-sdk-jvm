package notion.api.v1.model.common

import com.google.gson.annotations.SerializedName

enum class RichTextMentionType @JvmOverloads constructor(val value: String) {
  @SerializedName("user") User("user"),
  @SerializedName("page") Page("page"),
  @SerializedName("database") Database("database"),
  @SerializedName("date") Date("date");

  override fun toString(): String = value
}
