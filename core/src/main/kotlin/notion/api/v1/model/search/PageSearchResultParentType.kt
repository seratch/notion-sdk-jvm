package notion.api.v1.model.search

import com.google.gson.annotations.SerializedName

enum class PageSearchResultParentType @JvmOverloads constructor(val value: String) {
  @SerializedName("database_id") DatabaseId("database_id"),
  @SerializedName("page_id") PageId("page_id"),
  @SerializedName("block_id") BlockId("block_id"),
  @SerializedName("workspace") Workspace("workspace");

  override fun toString(): String = value
}
