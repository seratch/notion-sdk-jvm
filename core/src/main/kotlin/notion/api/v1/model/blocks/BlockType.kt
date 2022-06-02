package notion.api.v1.model.blocks

import com.google.gson.annotations.SerializedName

enum class BlockType @JvmOverloads constructor(val value: String) {
  @SerializedName("paragraph") Paragraph("paragraph"),
  @SerializedName("heading_1") HeadingOne("heading_1"),
  @SerializedName("heading_2") HeadingTwo("heading_2"),
  @SerializedName("heading_3") HeadingThree("heading_3"),
  @SerializedName("bulleted_list_item") BulletedListItem("bulleted_list_item"),
  @SerializedName("numbered_list_item") NumberedListItem("numbered_list_item"),
  @SerializedName("link_to_page") LinkToPage("link_to_page"),
  @SerializedName("link_preview") LinkPreview("link_preview"),
  @SerializedName("equation") Equation("equation"),
  @SerializedName("bookmark") Bookmark("bookmark"),
  @SerializedName("callout") Callout("callout"),
  @SerializedName("column") Column("column"),
  @SerializedName("column_list") ColumnList("column_list"),
  @SerializedName("breadcrumb") Breadcrumb("breadcrumb"),
  @SerializedName("table_of_contents") TableOfContents("table_of_contents"),
  @SerializedName("divider") Divider("divider"),
  @SerializedName("video") Video("video"),
  @SerializedName("quote") Quote("quote"),
  @SerializedName("to_do") ToDo("to_do"),
  @SerializedName("toggle") Toggle("toggle"),
  @SerializedName("code") Code("code"),
  @SerializedName("embed") Embed("embed"),
  @SerializedName("image") Image("image"),
  @SerializedName("file") File("file"),
  @SerializedName("pdf") PDF("pdf"),
  @SerializedName("child_page") ChildPage("child_page"),
  @SerializedName("child_database") ChildDatabase("child_database"),
  @SerializedName("synced_block") SyncedBlock("synced_block"),
  @SerializedName("table") Table("table"),
  @SerializedName("table_row") TableRow("table_row"),
  @SerializedName("template") Template("template"),
  @SerializedName("audio") Audio("audio"),
  @SerializedName("unsupported") Unsupported("unsupported");

  override fun toString(): String = value
}
