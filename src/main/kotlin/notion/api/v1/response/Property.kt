package notion.api.v1.response

import kotlinx.serialization.Serializable

@Serializable
data class Property(
    val type: String,
    val id: String,
    val title: List<RichText>? = null,
    // TODO: add others
) {
    @Serializable
    data class RichText(
        val href: String? = null,
        val plain_text: String? = null,
        val text: RichTextText? = null,
        val annotations: RichTextAnnotations? = null,
    )

    @Serializable
    data class RichTextText(
        val content: String? = null,
        val link: RichTextTextLink? = null
    )

    @Serializable
    data class RichTextTextLink(
        val type: String? = null,
        val url: String? = null
    )

    @Serializable
    data class RichTextAnnotations(
        val bold: Boolean? = null,
        val code: Boolean? = null,
        val color: String? = null,
        val italic: Boolean? = null,
        val strikethrough: Boolean? = null,
        val underline: Boolean? = null,
    )
}
