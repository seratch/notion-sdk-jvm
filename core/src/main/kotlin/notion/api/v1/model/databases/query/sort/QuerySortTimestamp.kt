package notion.api.v1.model.databases.query.sort

import com.google.gson.annotations.SerializedName

enum class QuerySortTimestamp @JvmOverloads constructor(val value: String) {
  @SerializedName("created_time") CreatedTime("created_time"),
  @SerializedName("last_edited_time") LastEditedTime("last_edited_time");

  override fun toString(): String = value
}
