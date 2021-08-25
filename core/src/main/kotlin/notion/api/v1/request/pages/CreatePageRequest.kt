package notion.api.v1.request.pages

import notion.api.v1.model.blocks.Block
import notion.api.v1.model.common.Cover
import notion.api.v1.model.common.Icon
import notion.api.v1.model.pages.PageParent
import notion.api.v1.model.pages.PageProperty

data class CreatePageRequest(
    val parent: PageParent,
    val properties: Map<String, PageProperty>,
    var children: List<Block>? = null,
    val icon: Icon? = null,
    val cover: Cover? = null,
) {

  // For other JVM languages
  constructor(
      parent: PageParent,
      properties: Map<String, PageProperty>
  ) : this(parent, properties, null, null, null)
}
