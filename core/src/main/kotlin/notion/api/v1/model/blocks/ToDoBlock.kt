package notion.api.v1.model.blocks

import com.google.gson.annotations.SerializedName
import java.util.*
import notion.api.v1.model.common.BlockColor
import notion.api.v1.model.common.ObjectType
import notion.api.v1.model.pages.PageProperty
import notion.api.v1.model.users.User

open class ToDoBlock
@JvmOverloads
constructor(
    @SerializedName("object") override val objectType: ObjectType = ObjectType.Block,
    override val type: BlockType = BlockType.ToDo,
    override var id: String? = UUID.randomUUID().toString(),
    override var createdTime: String? = null,
    override var createdBy: User? = null,
    override var lastEditedTime: String? = null,
    override var lastEditedBy: User? = null,
    override var hasChildren: Boolean? = null,
    override var archived: Boolean? = null,
    val toDo: Element,
) : Block {

  // for other JVM languages
  constructor(
      toDo: Element,
      id: String? = UUID.randomUUID().toString(),
      hasChildren: Boolean? = null,
      createdTime: String? = null,
      createdBy: User? = null,
      lastEditedTime: String? = null,
      lastEditedBy: User? = null,
  ) : this(
      objectType = ObjectType.Block,
      type = BlockType.ToDo,
      id = id,
      createdTime = createdTime,
      createdBy = createdBy,
      lastEditedTime = lastEditedTime,
      lastEditedBy = lastEditedBy,
      hasChildren = hasChildren,
      toDo = toDo)

  open class Element
  @JvmOverloads
  constructor(
      var checked: Boolean = false,
      var richText: List<PageProperty.RichText>? = null,
      var children: List<Block>? = null,
      var color: BlockColor? = null
  )
}
