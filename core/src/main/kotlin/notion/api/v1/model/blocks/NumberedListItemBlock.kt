package notion.api.v1.model.blocks

import com.google.gson.annotations.SerializedName
import java.util.*
import notion.api.v1.model.common.ObjectType
import notion.api.v1.model.pages.PageProperty
import notion.api.v1.model.users.User

open class NumberedListItemBlock
@JvmOverloads
constructor(
    @SerializedName("object") override val objectType: ObjectType = ObjectType.Block,
    override val type: BlockType = BlockType.NumberedListItem,
    override var id: String? = UUID.randomUUID().toString(),
    override var createdTime: String? = null,
    override var createdBy: User? = null,
    override var lastEditedTime: String? = null,
    override var lastEditedBy: User? = null,
    override var hasChildren: Boolean? = null,
    override var archived: Boolean? = null,
    val numberedListItem: Element,
) : Block {

  // for other JVM languages
  constructor(
      numberedListItem: Element,
      id: String? = UUID.randomUUID().toString(),
      hasChildren: Boolean? = null,
      createdTime: String? = null,
      createdBy: User? = null,
      lastEditedTime: String? = null,
      lastEditedBy: User? = null,
  ) : this(
      objectType = ObjectType.Block,
      type = BlockType.NumberedListItem,
      id = id,
      createdTime = createdTime,
      createdBy = createdBy,
      lastEditedTime = lastEditedTime,
      lastEditedBy = lastEditedBy,
      hasChildren = hasChildren,
      numberedListItem = numberedListItem)

  open class Element
  @JvmOverloads
  constructor(var text: List<PageProperty.RichText>, var children: List<Block>? = null)
}
