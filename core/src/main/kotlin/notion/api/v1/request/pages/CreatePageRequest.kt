package notion.api.v1.request.pages

import notion.api.v1.model.blocks.Block
import notion.api.v1.model.pages.PageProperty

data class CreatePageRequest(
    val parent: Parent,
    val properties: Map<String, PageProperty>,
    var children: List<Block>? = null,
) {

  // For other JVM languages
  constructor(
      parent: Parent,
      properties: Map<String, PageProperty>
  ) : this(parent, properties, null)

  data class Parent(
      val type: String,
      var databaseId: String? = null,
      var pageId: String? = null,
  ) {
    // for other JVM languages
    constructor(type: String) : this(type, null, null)

    companion object {
      @JvmStatic
      fun database(databaseId: String): Parent {
        return Parent(type = "database", databaseId = databaseId)
      }

      @JvmStatic
      fun page(pageId: String): Parent {
        return Parent(type = "page", databaseId = pageId)
      }
    }
  }
}
