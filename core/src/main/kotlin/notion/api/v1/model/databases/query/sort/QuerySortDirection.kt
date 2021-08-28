package notion.api.v1.model.databases.query.sort

import com.google.gson.annotations.SerializedName

enum class QuerySortDirection @JvmOverloads constructor(val value: String) {
  @SerializedName("ascending") Ascending("ascending"),
  @SerializedName("descending") Descending("descending");

  override fun toString(): String = value
}
