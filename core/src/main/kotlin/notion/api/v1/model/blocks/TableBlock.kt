package notion.api.v1.model.blocks

import com.google.gson.annotations.SerializedName
import java.util.*
import notion.api.v1.model.common.ObjectType

open class TableBlock
@JvmOverloads
constructor(
    @SerializedName("object") override val objectType: ObjectType = ObjectType.Block,
    override val type: BlockType = BlockType.Table,
    override var id: String? = UUID.randomUUID().toString(),
    override var createdTime: String? = null,
    override var lastEditedTime: String? = null,
    override var hasChildren: Boolean? = null,
    override var archived: Boolean? = null,
    val table: Element,
) : Block {

  // for other JVM languages
  constructor(
      table: Element,
      id: String? = UUID.randomUUID().toString(),
      hasChildren: Boolean? = null,
      createdTime: String? = null,
      lastEditedTime: String? = null,
  ) : this(
      objectType = ObjectType.Block,
      type = BlockType.Table,
      id = id,
      createdTime = createdTime,
      lastEditedTime = lastEditedTime,
      hasChildren = hasChildren,
      table = table)

  open class Element
  @JvmOverloads
  constructor(var tableWidth: Int, var hasColumnHeader: Boolean, var hasRowHeader: Boolean)
}
