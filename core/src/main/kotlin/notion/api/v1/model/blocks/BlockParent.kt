package notion.api.v1.model.blocks

data class BlockParent
@JvmOverloads
constructor(
    val type: BlockParentType? = null,
    var databaseId: String? = null, // type: database
    var pageId: String? = null, // type: page
    var blockId: String? = null, // type: block
    var workspace: Boolean? = null // type: workspace
) {

  companion object {
    @JvmStatic
    fun database(databaseId: String): BlockParent {
      // having the `type` property does not work as of May 19, 2021
      return BlockParent(type = null, databaseId = databaseId)
    }

    @JvmStatic
    fun page(pageId: String): BlockParent {
      // having the `type` property does not work as of May 19, 2021
      return BlockParent(type = null, pageId = pageId)
    }

    @JvmStatic
    fun block(blockId: String): BlockParent {
      // having the `type` property does not work as of May 19, 2021
      return BlockParent(type = null, blockId = blockId)
    }

    @JvmStatic
    fun workspace(): BlockParent {
      // having the `type` property does not work as of May 19, 2021
      return BlockParent(type = null, workspace = true)
    }
  }
}
