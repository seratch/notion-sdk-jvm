package notion.api.v1.model.pages

data class PageParent(
    val type: String, // database / workspace
    val databaseId: String, // type: database
    val workspace: Boolean? = null // type: workspace
)
