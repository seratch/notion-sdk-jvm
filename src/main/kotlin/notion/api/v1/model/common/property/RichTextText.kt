package notion.api.v1.model.common.property

data class RichTextText(
    val content: String? = null,
    val link: RichTextTextLink? = null
)