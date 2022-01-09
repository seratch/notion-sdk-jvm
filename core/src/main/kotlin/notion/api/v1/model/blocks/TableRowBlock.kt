package notion.api.v1.model.blocks

import com.google.gson.annotations.SerializedName
import java.util.*
import notion.api.v1.model.common.ObjectType
import notion.api.v1.model.pages.PageProperty

open class TableRowBlock
@JvmOverloads
constructor(
    @SerializedName("object") override val objectType: ObjectType = ObjectType.Block,
    override val type: BlockType = BlockType.TableRow,
    override var id: String? = UUID.randomUUID().toString(),
    override var createdTime: String? = null,
    override var lastEditedTime: String? = null,
    override var hasChildren: Boolean? = null,
    override var archived: Boolean? = null,
    val tableRow: Element,
) : Block {

  // for other JVM languages
  constructor(
      tableRow: Element,
      id: String? = UUID.randomUUID().toString(),
      hasChildren: Boolean? = null,
      createdTime: String? = null,
      lastEditedTime: String? = null,
  ) : this(
      objectType = ObjectType.Block,
      type = BlockType.TableRow,
      id = id,
      createdTime = createdTime,
      lastEditedTime = lastEditedTime,
      hasChildren = hasChildren,
      tableRow = tableRow)

  open class Element
  @JvmOverloads
  constructor(var cells: List<List<PageProperty.RichText>> = emptyList())
}
