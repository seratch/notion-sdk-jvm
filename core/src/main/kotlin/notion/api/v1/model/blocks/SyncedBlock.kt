package notion.api.v1.model.blocks

import com.google.gson.annotations.SerializedName
import java.util.*
import notion.api.v1.model.common.ObjectType

open class SyncedBlock
@JvmOverloads
constructor(
    @SerializedName("object") override val objectType: ObjectType = ObjectType.Block,
    override val type: BlockType = BlockType.SyncedBlock,
    override var id: String? = UUID.randomUUID().toString(),
    override var createdTime: String? = null,
    override var lastEditedTime: String? = null,
    override var hasChildren: Boolean? = null,
    override var archived: Boolean? = null,
    val syncedBlock: Element? = null,
) : Block {

  // for other JVM languages
  constructor(
      id: String? = UUID.randomUUID().toString(),
      hasChildren: Boolean? = null,
      createdTime: String? = null,
      archived: Boolean? = null,
      lastEditedTime: String? = null,
  ) : this(
      ObjectType.Block,
      BlockType.SyncedBlock,
      id,
      createdTime,
      lastEditedTime,
      hasChildren,
      archived)

  open class Element
  @JvmOverloads
  constructor(
      val syncedFrom: SyncedFrom? = null,
      val children: List<Block>? = null,
  )
  open class SyncedFrom
  @JvmOverloads
  constructor(
      val blockId: String? = null,
  )
}
