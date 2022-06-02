package notion.api.v1.model.blocks

import com.google.gson.annotations.SerializedName
import java.util.*
import notion.api.v1.model.common.FileDetails
import notion.api.v1.model.common.ObjectType
import notion.api.v1.model.pages.PageProperty
import notion.api.v1.model.users.User

open class AudioBlock
@JvmOverloads
constructor(
    @SerializedName("object") override val objectType: ObjectType = ObjectType.Block,
    override var type: BlockType = BlockType.Audio,
    override var id: String? = UUID.randomUUID().toString(),
    override var createdTime: String? = null,
    override var createdBy: User? = null,
    override var lastEditedTime: String? = null,
    override var lastEditedBy: User? = null,
    override var hasChildren: Boolean? = null,
    override var archived: Boolean? = null,
    val audio: Element,
) : Block {

  // for other JVM languages
  constructor(
      audio: Element,
      id: String? = UUID.randomUUID().toString(),
      hasChildren: Boolean? = null,
      createdTime: String? = null,
      createdBy: User? = null,
      lastEditedTime: String? = null,
      lastEditedBy: User? = null,
  ) : this(
      objectType = ObjectType.Block,
      type = BlockType.Audio,
      id = id,
      createdTime = createdTime,
      createdBy = createdBy,
      lastEditedTime = lastEditedTime,
      lastEditedBy = lastEditedBy,
      hasChildren = hasChildren,
      audio = audio)

  open class Element
  @JvmOverloads
  constructor(
      val caption: List<PageProperty.RichText>? = null,
      val type: String = "audio",
      val file: FileDetails? = null,
  )
}
