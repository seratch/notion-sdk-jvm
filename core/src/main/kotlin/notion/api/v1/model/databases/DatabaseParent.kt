package notion.api.v1.model.databases

data class DatabaseParent(
    val type: DatabaseParentType? = null,
    var pageId: String? = null, // type: page
    var workspace: Boolean? = null // type: workspace
) {

  // for other JVM languages
  constructor() : this(null, null, null)

  companion object {
    @JvmStatic
    fun page(pageId: String): DatabaseParent {
      return DatabaseParent(type = DatabaseParentType.PageId, pageId = pageId)
    }

    @JvmStatic
    fun workspace(): DatabaseParent {
      return DatabaseParent(type = DatabaseParentType.Workspace, workspace = true)
    }
  }
}
