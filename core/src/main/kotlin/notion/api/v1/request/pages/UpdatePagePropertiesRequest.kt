package notion.api.v1.request.pages

import notion.api.v1.model.pages.PageProperty

data class UpdatePagePropertiesRequest(
    val pageId: String,
    val properties: Map<String, PageProperty>
)