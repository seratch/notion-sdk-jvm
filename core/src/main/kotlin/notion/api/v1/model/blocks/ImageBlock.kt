package notion.api.v1.model.blocks

import com.google.gson.annotations.SerializedName
import java.util.*
import notion.api.v1.model.common.ExternalFileDetails
import notion.api.v1.model.common.FileDetails
import notion.api.v1.model.common.ObjectType
import notion.api.v1.model.users.User

open class ImageBlock
@JvmOverloads
constructor(
    @SerializedName("object") override val objectType: ObjectType = ObjectType.Block,
    override val type: BlockType = BlockType.Image,
    override var id: String? = UUID.randomUUID().toString(),
    override var createdTime: String? = null,
    override var createdBy: User? = null,
    override var lastEditedTime: String? = null,
    override var lastEditedBy: User? = null,
    override var hasChildren: Boolean? = null,
    override var archived: Boolean? = null,
    val image: Element? = null,
) : Block {

  // for other JVM languages
  constructor(
      id: String? = UUID.randomUUID().toString(),
      hasChildren: Boolean? = null,
      createdTime: String? = null,
      createdBy: User? = null,
      archived: Boolean? = null,
      lastEditedTime: String? = null,
      lastEditedBy: User? = null,
  ) : this(
      ObjectType.Block,
      BlockType.Image,
      id,
      createdTime,
      createdBy,
      lastEditedTime,
      lastEditedBy,
      hasChildren,
      archived)

  open class Element
  @JvmOverloads
  constructor(
      val type: String? = null,
      val external: ExternalFileDetails? = null,
      val file: FileDetails? = null,
      val caption: List<String>? = null,
  )
}
