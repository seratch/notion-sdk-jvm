package notion.api.v1.model.pages

data class PageParent(
    val type: PageParentType,
    var databaseId: String? = null, // type: database
    var pageId: String? = null, // type: page
    var workspace: Boolean? = null // type: workspace
) {

  // for other JVM languages
  constructor(type: PageParentType) : this(type, null, null)

  companion object {
    @JvmStatic
    fun database(databaseId: String): PageParent {
      return PageParent(type = PageParentType.Database, databaseId = databaseId)
    }

    @JvmStatic
    fun page(pageId: String): PageParent {
      return PageParent(type = PageParentType.Page, pageId = pageId)
    }

    @JvmStatic
    fun workspace(): PageParent {
      return PageParent(type = PageParentType.Workspace, workspace = true)
    }
  }
}
