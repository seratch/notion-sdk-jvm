package notion.api.v1.model.blocks

import notion.api.v1.model.common.ExternalFileDetails
import notion.api.v1.model.common.Icon
import notion.api.v1.model.common.LinkToPage
import notion.api.v1.model.common.SyncedFrom
import notion.api.v1.model.pages.PageProperty

data class BlockElementUpdate
@JvmOverloads
constructor(
    val richText: List<PageProperty.RichText>? = null,
    val caption: List<PageProperty.RichText>? = null,
    val checked: Boolean? = null,
    val url: String? = null,
    var external: ExternalFileDetails? = null,
    var language: String? = null,
    var expression: String? = null, // equation
    var color: String? = null,
    var linkToPage: LinkToPage? = null,
    val icon: Icon? = null,
    var syncedFrom: SyncedFrom? = null,
    val hasColumnHeader: Boolean? = null, // table
    val hasRowHeader: Boolean? = null, // table
    val cells: List<List<PageProperty.RichText>>? = null,
)
