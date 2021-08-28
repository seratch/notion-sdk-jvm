package notion.api.v1.model.databases

import com.google.gson.annotations.SerializedName

enum class DatabaseParentType @JvmOverloads constructor(val value: String) {
  @SerializedName("workspace") Workspace("workspace"),
  @SerializedName("page_id") PageId("page_id");

  override fun toString(): String = value
}
