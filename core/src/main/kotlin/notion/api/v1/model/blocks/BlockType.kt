package notion.api.v1.model.blocks

import com.google.gson.annotations.SerializedName

enum class BlockType @JvmOverloads constructor(val value: String) {
  @SerializedName("paragraph") Paragraph("paragraph"),
  @SerializedName("heading_1") HeadingOne("heading_1"),
  @SerializedName("heading_2") HeadingTwo("heading_2"),
  @SerializedName("heading_3") HeadingThree("heading_3"),
  @SerializedName("bulleted_list_item") BulletedListItem("bulleted_list_item"),
  @SerializedName("numbered_list_item") NumberedListItem("numbered_list_item"),
  @SerializedName("to_do") ToDo("to_do"),
  @SerializedName("toggle") Toggle("toggle"),
  @SerializedName("child_page") ChildPage("child_page"),
  @SerializedName("unsupported") Unsupported("unsupported");

  override fun toString(): String = value
}
