package notion.api.v1.model.common.property

data class RichTextAnnotations(
    val bold: Boolean? = null,
    val italic: Boolean? = null,
    val strikethrough: Boolean? = null,
    val underline: Boolean? = null,
    val code: Boolean? = null,
    val color: String? = null,
)