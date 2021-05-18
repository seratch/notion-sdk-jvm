package notion.api.v1.model.pages

import com.google.gson.annotations.SerializedName

enum class PageParentType(val value: String) {
  @SerializedName("database") Database("database"),
  @SerializedName("page") Page("page"),
  @SerializedName("workspace") Workspace("workspace");

  override fun toString(): String = value
}
