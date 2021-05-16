package notion.api.v1.model.common.property

data class RichText(
    val type: String = "text",
    val text: RichTextText? = null,
    val annotations: RichTextAnnotations? = null,
    val plainText: String? = null,
    val href: String? = null,
)
