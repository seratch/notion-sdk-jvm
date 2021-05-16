package notion.api.v1.request

import notion.api.v1.model.page.PagePropertyArg

data class UpdatePagePropertiesRequest(
    val pageId: String,
    val properties: Map<String, PagePropertyArg>
)