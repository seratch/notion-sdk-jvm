package notion.api.v1.model.common

import com.google.gson.annotations.SerializedName

enum class ObjectType(val value: String) {
  @SerializedName("block") Block("block"),
  @SerializedName("database") Database("database"),
  @SerializedName("error") Error("error"),
  @SerializedName("page") Page("page"),
  @SerializedName("list") List("list"),
  @SerializedName("user") User("user");

  override fun toString(): String = value
}
