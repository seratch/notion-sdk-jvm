package notion.api.v1.model.common

import com.google.gson.annotations.SerializedName

enum class ObjectType @JvmOverloads constructor(val value: String) {
  @SerializedName("block") Block("block"),
  @SerializedName("database") Database("database"),
  @SerializedName("error") Error("error"),
  @SerializedName("page") Page("page"),
  @SerializedName("comment") Comment("comment"),
  @SerializedName("property_item") PropertyItem("property_item"),
  @SerializedName("list") List("list"),
  @SerializedName("user") User("user");

  override fun toString(): String = value
}
