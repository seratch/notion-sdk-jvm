package notion.api.v1.model.common

import com.google.gson.annotations.SerializedName

enum class PropertyType @JvmOverloads constructor(val value: String) {
  @SerializedName("rich_text") RichText("rich_text"),
  @SerializedName("number") Number("number"),
  @SerializedName("select") Select("select"),
  @SerializedName("multi_select") MultiSelect("multi_select"),
  @SerializedName("date") Date("date"),
  @SerializedName("formula") Formula("formula"),
  @SerializedName("relation") Relation("relation"),
  @SerializedName("rollup") Rollup("rollup"),
  @SerializedName("title") Title("title"),
  @SerializedName("people") People("people"),
  @SerializedName("files") Files("files"),
  @SerializedName("checkbox") Checkbox("checkbox"),
  @SerializedName("url") Url("url"),
  @SerializedName("email") Email("email"),
  @SerializedName("phone_number") PhoneNumber("phone_number"),
  @SerializedName("created_time") CreatedTime("created_time"),
  @SerializedName("created_by") CreatedBy("created_by"),
  @SerializedName("last_edited_time") LastEditedTime("last_edited_time"),
  @SerializedName("last_edited_by") LastEditedBy("last_edited_by"),
  @SerializedName("unique_id") UniqueId("unique_id"),
  @SerializedName("property_item") PropertyItem("property_item");

  override fun toString(): String = value
}
