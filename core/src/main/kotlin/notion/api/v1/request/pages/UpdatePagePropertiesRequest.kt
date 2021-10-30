package notion.api.v1.request.pages

import notion.api.v1.model.common.Cover
import notion.api.v1.model.common.Icon
import notion.api.v1.model.pages.PageProperty

data class UpdatePagePropertiesRequest
@JvmOverloads
constructor(
    @Transient val pageId: String,
    val properties: Map<String, PageProperty>,
    val archived: Boolean? = null,
    val icon: Icon? = null,
    val cover: Cover? = null,
)
