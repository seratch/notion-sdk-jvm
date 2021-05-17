package notion.api.v1.model.pages

data class PageParent(
    val type: String, // database / workspace
    var databaseId: String? = null, // type: database
    var workspace: Boolean? = null // type: workspace
) {

    // for other JVM languages
    constructor(type: String) : this(type, null, null)

    companion object {
        @JvmStatic
        fun database(databaseId: String): PageParent {
            return PageParent(type = "database", databaseId = databaseId)
        }

        @JvmStatic
        fun page(pageId: String): PageParent {
            return PageParent(type = "page", databaseId = pageId)
        }
    }
}
