package notion.api.v1.model.pages

data class PageParent
@JvmOverloads
constructor(
    // Notion stopped using type (as of May 19, 2021)
    val type: PageParentType? = null,
    var databaseId: String? = null, // type: database
    var pageId: String? = null, // type: page
    var workspace: Boolean? = null // type: workspace
) {

  companion object {
    @JvmStatic
    fun database(databaseId: String): PageParent {
      // having the `type` property does not work as of May 19, 2021
      return PageParent(type = null, databaseId = databaseId)
    }

    @JvmStatic
    fun page(pageId: String): PageParent {
      // having the `type` property does not work as of May 19, 2021
      return PageParent(type = null, pageId = pageId)
    }

    @JvmStatic
    fun workspace(): PageParent {
      // having the `type` property does not work as of May 19, 2021
      return PageParent(type = null, workspace = true)
    }
  }
}
