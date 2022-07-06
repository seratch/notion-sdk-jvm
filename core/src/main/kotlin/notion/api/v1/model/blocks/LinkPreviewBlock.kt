package notion.api.v1.model.blocks

import com.google.gson.annotations.SerializedName
import java.util.*
import notion.api.v1.model.common.ObjectType
import notion.api.v1.model.users.User

open class LinkPreviewBlock
@JvmOverloads
constructor(
    @SerializedName("object") override val objectType: ObjectType = ObjectType.Block,
    override val type: BlockType = BlockType.LinkPreview,
    override var id: String? = UUID.randomUUID().toString(),
    override var createdTime: String? = null,
    override var createdBy: User? = null,
    override var lastEditedTime: String? = null,
    override var lastEditedBy: User? = null,
    override var hasChildren: Boolean? = null,
    override var archived: Boolean? = null,
    override var parent: BlockParent? = null,
    val linkPreview: Element? = null,
) : Block {

  // for other JVM languages
  constructor(
      linkPreview: Element,
      id: String? = UUID.randomUUID().toString(),
      hasChildren: Boolean? = null,
      createdTime: String? = null,
      createdBy: User? = null,
      archived: Boolean? = null,
      lastEditedTime: String? = null,
      lastEditedBy: User? = null,
      parent: BlockParent? = null,
  ) : this(
      ObjectType.Block,
      BlockType.LinkPreview,
      id,
      createdTime,
      createdBy,
      lastEditedTime,
      lastEditedBy,
      hasChildren,
      archived,
      parent,
      linkPreview)

  open class Element
  @JvmOverloads
  constructor(
      val url: String? = null,
  )
}
