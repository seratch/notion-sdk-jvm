package notion.api.v1.model.blocks

import com.google.gson.annotations.SerializedName
import notion.api.v1.model.common.ObjectType
import notion.api.v1.model.pages.PageProperty

interface Block : ObjectType {
  val id: String
  val type: String
  val createdTime: String
  val lastEditedTime: String
  val hasChildren: Boolean

  fun asParagraph(): ParagraphBlock =
      if (type == "paragraph") this as ParagraphBlock
      else throw IllegalStateException("Failed to cast $type block to ParagraphBlock")

  fun asHeadingOne(): HeadingOneBlock =
      if (type == "heading_1") this as HeadingOneBlock
      else throw IllegalStateException("Failed to cast $type block to HeadingOneBlock")

  fun asHeadingTwo(): HeadingTwoBlock =
      if (type == "heading_2") this as HeadingTwoBlock
      else throw IllegalStateException("Failed to cast $type block to HeadingTwoBlock")

  fun asHeadingThree(): HeadingThreeBlock =
      if (type == "heading_3") this as HeadingThreeBlock
      else throw IllegalStateException("Failed to cast $type block to HeadingThreeBlock")

  fun asBulletedListItem(): BulletedListItemBlock =
      if (type == "bulleted_list_item") this as BulletedListItemBlock
      else throw IllegalStateException("Failed to cast $type block to BulletedListItemBlock")

  fun asNumberedListItem(): NumberedListItemBlock =
      if (type == "numbered_list_item") this as NumberedListItemBlock
      else throw IllegalStateException("Failed to cast $type block to NumberedListItemBlock")

  fun asToDo(): ToDoBlock =
      if (type == "to_do") this as ToDoBlock
      else throw IllegalStateException("Failed to cast $type block to ToDoBlock")

  fun asToggle(): ToggleBlock =
      if (type == "toggle") this as ToggleBlock
      else throw IllegalStateException("Failed to cast $type block to ToggleBlock")

  fun asChildPage(): ChildPageBlock =
      if (type == "child_page") this as ChildPageBlock
      else throw IllegalStateException("Failed to cast $type block to ChildPageBlock")

  fun asUnsupported(): UnsupportedBlock =
      if (type == "unsupported") this as UnsupportedBlock
      else throw IllegalStateException("Failed to cast $type block to UnsupportedBlock")
}

open class ParagraphBlock(
    @SerializedName("object") override val objectType: String = "block",
    override val type: String = "paragraph",
    override val id: String,
    override val createdTime: String,
    override val lastEditedTime: String,
    override val hasChildren: Boolean,
    val paragraph: Element,
) : Block {

  // for other JVM languages
  constructor(
      id: String,
      paragraph: Element,
      hasChildren: Boolean,
      createdTime: String,
      lastEditedTime: String,
  ) : this("block", "paragraph", id, createdTime, lastEditedTime, hasChildren, paragraph)

  open class Element(var text: List<PageProperty.RichText>, var children: List<Block>? = null)
}

open class HeadingOneBlock(
    @SerializedName("object") override val objectType: String = "block",
    override val type: String = "heading_1",
    override val id: String,
    override val createdTime: String,
    override val lastEditedTime: String,
    override val hasChildren: Boolean = false,
    @SerializedName("heading_1") val heading1: Element,
) : Block {

  // for other JVM languages
  constructor(
      id: String,
      heading1: Element,
      hasChildren: Boolean,
      createdTime: String,
      lastEditedTime: String,
  ) : this("block", "heading_1", id, createdTime, lastEditedTime, hasChildren, heading1)

  open class Element(
      var text: List<PageProperty.RichText>,
  )
}

open class HeadingTwoBlock(
    @SerializedName("object") override val objectType: String = "block",
    override val type: String = "heading_2",
    override val id: String,
    override val createdTime: String,
    override val lastEditedTime: String,
    override val hasChildren: Boolean = false,
    @SerializedName("heading_2") val heading2: Element,
) : Block {

  // for other JVM languages
  constructor(
      id: String,
      heading2: Element,
      hasChildren: Boolean,
      createdTime: String,
      lastEditedTime: String,
  ) : this("block", "heading_2", id, createdTime, lastEditedTime, hasChildren, heading2)

  open class Element(
      var text: List<PageProperty.RichText>,
  )
}

open class HeadingThreeBlock(
    @SerializedName("object") override val objectType: String = "block",
    override val type: String = "heading_3",
    override val id: String,
    override val createdTime: String,
    override val lastEditedTime: String,
    override val hasChildren: Boolean = false,
    @SerializedName("heading_3") val heading3: Element,
) : Block {

  // for other JVM languages
  constructor(
      id: String,
      heading3: Element,
      hasChildren: Boolean,
      createdTime: String,
      lastEditedTime: String,
  ) : this("block", "heading_3", id, createdTime, lastEditedTime, hasChildren, heading3)

  open class Element(
      var text: List<PageProperty.RichText>,
  )
}

open class BulletedListItemBlock(
    @SerializedName("object") override val objectType: String = "block",
    override val type: String = "bulleted_list_item",
    override val id: String,
    override val createdTime: String,
    override val lastEditedTime: String,
    override val hasChildren: Boolean,
    val bulletedListItem: Element,
) : Block {

  // for other JVM languages
  constructor(
      id: String,
      bulletedListItem: Element,
      hasChildren: Boolean,
      createdTime: String,
      lastEditedTime: String,
  ) : this(
      "block", "bulleted_list_item", id, createdTime, lastEditedTime, hasChildren, bulletedListItem)

  open class Element(var text: List<PageProperty.RichText>, var children: List<Block>? = null)
}

open class NumberedListItemBlock(
    @SerializedName("object") override val objectType: String = "block",
    override val type: String = "numbered_list_item",
    override val id: String,
    override val createdTime: String,
    override val lastEditedTime: String,
    override val hasChildren: Boolean,
    val numberedListItem: Element,
) : Block {

  // for other JVM languages
  constructor(
      id: String,
      numberedListItem: Element,
      hasChildren: Boolean,
      createdTime: String,
      lastEditedTime: String,
  ) : this(
      "block", "numbered_list_item", id, createdTime, lastEditedTime, hasChildren, numberedListItem)

  open class Element(var text: List<PageProperty.RichText>, var children: List<Block>? = null)
}

open class ToDoBlock(
    @SerializedName("object") override val objectType: String = "block",
    override val type: String = "to_do",
    override val id: String,
    override val createdTime: String,
    override val lastEditedTime: String,
    override val hasChildren: Boolean,
    val toDo: Element,
) : Block {

  // for other JVM languages
  constructor(
      id: String,
      toDo: Element,
      hasChildren: Boolean,
      createdTime: String,
      lastEditedTime: String,
  ) : this("block", "to_do", id, createdTime, lastEditedTime, hasChildren, toDo)

  open class Element(var checked: Boolean, var children: List<Block>? = null)
}

open class ToggleBlock(
    @SerializedName("object") override val objectType: String = "block",
    override val type: String = "toggle",
    override val id: String,
    override val createdTime: String,
    override val lastEditedTime: String,
    override val hasChildren: Boolean,
    val toggle: Element,
) : Block {

  // for other JVM languages
  constructor(
      id: String,
      toggle: Element,
      hasChildren: Boolean,
      createdTime: String,
      lastEditedTime: String,
  ) : this("block", "toggle", id, createdTime, lastEditedTime, hasChildren, toggle)

  open class Element(var text: List<PageProperty.RichText>, var children: List<Block>? = null)
}

open class ChildPageBlock(
    @SerializedName("object") override val objectType: String = "block",
    override val type: String = "child_page",
    override val id: String,
    override val createdTime: String,
    override val lastEditedTime: String,
    override val hasChildren: Boolean,
    val childPage: Element,
) : Block {

  // for other JVM languages
  constructor(
      id: String,
      childPage: Element,
      hasChildren: Boolean,
      createdTime: String,
      lastEditedTime: String,
  ) : this("block", "child_page", id, createdTime, lastEditedTime, hasChildren, childPage)

  open class Element(var title: String)
}

open class UnsupportedBlock(
    @SerializedName("object") override val objectType: String = "block",
    override val type: String = "unsupported",
    override val id: String,
    override val createdTime: String,
    override val lastEditedTime: String,
    override val hasChildren: Boolean,
) : Block {
  // for other JVM languages
  constructor(
      id: String,
      hasChildren: Boolean,
      createdTime: String,
      lastEditedTime: String,
  ) : this("block", "unsupported", id, createdTime, lastEditedTime, hasChildren)
}
