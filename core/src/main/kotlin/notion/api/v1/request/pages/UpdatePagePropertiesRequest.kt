package notion.api.v1.request.pages

import notion.api.v1.model.common.Cover
import notion.api.v1.model.common.Icon
import notion.api.v1.model.pages.PageProperty

data class UpdatePagePropertiesRequest(
    @Transient val pageId: String,
    val properties: Map<String, PageProperty>,
    val icon: Icon? = null,
    val cover: Cover? = null,
) {

  // For other JVM languages
  constructor(
      pageId: String,
      properties: Map<String, PageProperty>,
  ) : this(pageId, properties, null, null)
}
