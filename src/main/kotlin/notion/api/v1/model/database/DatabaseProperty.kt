package notion.api.v1.model.database

import notion.api.v1.model.common.property.MultiSelect
import notion.api.v1.model.common.property.RichText

data class DatabaseProperty(
    val type: String,
    val id: String,
    val title: RichText? = null,
    val multiSelect: MultiSelect? = null,
    // TODO: add others
)