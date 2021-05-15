package notion.api.v1.model.page

import notion.api.v1.model.common.property.MultiSelect
import notion.api.v1.model.common.property.RichText

data class PageProperty(
    val id: String,
    val type: String,
    val title: List<RichText>? = null,
    val multiSelect: MultiSelect? = null
    // TODO: add others
)