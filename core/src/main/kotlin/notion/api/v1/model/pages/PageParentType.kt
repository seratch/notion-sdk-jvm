package notion.api.v1.model.pages

import com.google.gson.annotations.SerializedName

enum class PageParentType @JvmOverloads constructor(val value: String) {
  @SerializedName("database_id") DatabaseId("database_id"),
  @SerializedName("page_id") PageId("page_id"),
  @SerializedName("workspace") Workspace("workspace");

  override fun toString(): String = value
}
