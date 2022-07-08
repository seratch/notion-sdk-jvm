package notion.api.v1.model.search

data class PageSearchResultParent
@JvmOverloads
constructor(
    val type: PageSearchResultParentType? = null,
    var databaseId: String? = null, // type: database
    var pageId: String? = null, // type: page
    var blockId: String? = null, // type: block
    var workspace: Boolean? = null // type: workspace
) {

  companion object {
    @JvmStatic
    fun database(databaseId: String): PageSearchResultParent {
      // having the `type` property does not work as of May 19, 2021
      return PageSearchResultParent(type = null, databaseId = databaseId)
    }

    @JvmStatic
    fun page(pageId: String): PageSearchResultParent {
      // having the `type` property does not work as of May 19, 2021
      return PageSearchResultParent(type = null, pageId = pageId)
    }

    @JvmStatic
    fun block(blockId: String): PageSearchResultParent {
      // having the `type` property does not work as of May 19, 2021
      return PageSearchResultParent(type = null, blockId = blockId)
    }

    @JvmStatic
    fun workspace(): PageSearchResultParent {
      // having the `type` property does not work as of May 19, 2021
      return PageSearchResultParent(type = null, workspace = true)
    }
  }
}
