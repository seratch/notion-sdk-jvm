package notion.api.v1.model.blocks

import com.google.gson.annotations.SerializedName
import notion.api.v1.model.common.ObjectType
import notion.api.v1.model.common.WithObjectType
import notion.api.v1.model.pages.PageProperty

interface Block : WithObjectType {
  val id: String
  val type: BlockType
  val createdTime: String
  val lastEditedTime: String
  val hasChildren: Boolean

  fun asParagraph(): ParagraphBlock =
      if (type == BlockType.Paragraph) this as ParagraphBlock
      else throw IllegalStateException("Failed to cast $type block to ParagraphBlock")

  fun asHeadingOne(): HeadingOneBlock =
      if (type == BlockType.HeadingOne) this as HeadingOneBlock
      else throw IllegalStateException("Failed to cast $type block to HeadingOneBlock")

  fun asHeadingTwo(): HeadingTwoBlock =
      if (type == BlockType.HeadingTwo) this as HeadingTwoBlock
      else throw IllegalStateException("Failed to cast $type block to HeadingTwoBlock")

  fun asHeadingThree(): HeadingThreeBlock =
      if (type == BlockType.HeadingThree) this as HeadingThreeBlock
      else throw IllegalStateException("Failed to cast $type block to HeadingThreeBlock")

  fun asBulletedListItem(): BulletedListItemBlock =
      if (type == BlockType.BulletedListItem) this as BulletedListItemBlock
      else throw IllegalStateException("Failed to cast $type block to BulletedListItemBlock")

  fun asNumberedListItem(): NumberedListItemBlock =
      if (type == BlockType.NumberedListItem) this as NumberedListItemBlock
      else throw IllegalStateException("Failed to cast $type block to NumberedListItemBlock")

  fun asToDo(): ToDoBlock =
      if (type == BlockType.ToDo) this as ToDoBlock
      else throw IllegalStateException("Failed to cast $type block to ToDoBlock")

  fun asToggle(): ToggleBlock =
      if (type == BlockType.Toggle) this as ToggleBlock
      else throw IllegalStateException("Failed to cast $type block to ToggleBlock")

  fun asChildPage(): ChildPageBlock =
      if (type == BlockType.ChildPage) this as ChildPageBlock
      else throw IllegalStateException("Failed to cast $type block to ChildPageBlock")

  fun asUnsupported(): UnsupportedBlock =
      if (type == BlockType.Unsupported) this as UnsupportedBlock
      else throw IllegalStateException("Failed to cast $type block to UnsupportedBlock")
}

open class ParagraphBlock(
    @SerializedName("object") override val objectType: ObjectType = ObjectType.Block,
    override val type: BlockType = BlockType.Paragraph,
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
  ) : this(
      ObjectType.Block,
      BlockType.Paragraph,
      id,
      createdTime,
      lastEditedTime,
      hasChildren,
      paragraph)

  open class Element(var text: List<PageProperty.RichText>, var children: List<Block>? = null)
}

open class HeadingOneBlock(
    @SerializedName("object") override val objectType: ObjectType = ObjectType.Block,
    override val type: BlockType = BlockType.HeadingOne,
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
  ) : this(
      ObjectType.Block,
      BlockType.HeadingOne,
      id,
      createdTime,
      lastEditedTime,
      hasChildren,
      heading1)

  open class Element(
      var text: List<PageProperty.RichText>,
  )
}

open class HeadingTwoBlock(
    @SerializedName("object") override val objectType: ObjectType = ObjectType.Block,
    override val type: BlockType = BlockType.HeadingTwo,
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
  ) : this(
      ObjectType.Block,
      BlockType.HeadingTwo,
      id,
      createdTime,
      lastEditedTime,
      hasChildren,
      heading2)

  open class Element(
      var text: List<PageProperty.RichText>,
  )
}

open class HeadingThreeBlock(
    @SerializedName("object") override val objectType: ObjectType = ObjectType.Block,
    override val type: BlockType = BlockType.HeadingThree,
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
  ) : this(
      ObjectType.Block,
      BlockType.HeadingThree,
      id,
      createdTime,
      lastEditedTime,
      hasChildren,
      heading3)

  open class Element(
      var text: List<PageProperty.RichText>,
  )
}

open class BulletedListItemBlock(
    @SerializedName("object") override val objectType: ObjectType = ObjectType.Block,
    override val type: BlockType = BlockType.BulletedListItem,
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
      ObjectType.Block,
      BlockType.BulletedListItem,
      id,
      createdTime,
      lastEditedTime,
      hasChildren,
      bulletedListItem)

  open class Element(var text: List<PageProperty.RichText>, var children: List<Block>? = null)
}

open class NumberedListItemBlock(
    @SerializedName("object") override val objectType: ObjectType = ObjectType.Block,
    override val type: BlockType = BlockType.NumberedListItem,
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
      ObjectType.Block,
      BlockType.NumberedListItem,
      id,
      createdTime,
      lastEditedTime,
      hasChildren,
      numberedListItem)

  open class Element(var text: List<PageProperty.RichText>, var children: List<Block>? = null)
}

open class ToDoBlock(
    @SerializedName("object") override val objectType: ObjectType = ObjectType.Block,
    override val type: BlockType = BlockType.ToDo,
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
  ) : this(ObjectType.Block, BlockType.ToDo, id, createdTime, lastEditedTime, hasChildren, toDo)

  open class Element(var checked: Boolean, var children: List<Block>? = null)
}

open class ToggleBlock(
    @SerializedName("object") override val objectType: ObjectType = ObjectType.Block,
    override val type: BlockType = BlockType.Toggle,
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
  ) : this(ObjectType.Block, BlockType.Toggle, id, createdTime, lastEditedTime, hasChildren, toggle)

  open class Element(var text: List<PageProperty.RichText>, var children: List<Block>? = null)
}

open class ChildPageBlock(
    @SerializedName("object") override val objectType: ObjectType = ObjectType.Block,
    override val type: BlockType = BlockType.ChildPage,
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
  ) : this(
      ObjectType.Block,
      BlockType.ChildPage,
      id,
      createdTime,
      lastEditedTime,
      hasChildren,
      childPage)

  open class Element(var title: String)
}

open class UnsupportedBlock(
    @SerializedName("object") override val objectType: ObjectType = ObjectType.Block,
    override val type: BlockType = BlockType.Unsupported,
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
  ) : this(ObjectType.Block, BlockType.Unsupported, id, createdTime, lastEditedTime, hasChildren)
}
