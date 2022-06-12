package notion.api.v1.model.common

data class LinkToPage
@JvmOverloads
constructor(
    val type: String? = null,
    val pageId: String? = null,
    val databaseId: String? = null,
) : Icon
