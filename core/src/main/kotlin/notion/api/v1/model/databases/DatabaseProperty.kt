package notion.api.v1.model.databases

data class DatabaseProperty(
    val type: String,
    val id: String,
    val title: RichText? = null,
    val richText: RichText? = null,
    val select: Select? = null,
    val multiSelect: MultiSelect? = null,
    val date: Date? = null,
    val formula: Formula? = null,
    val rollup: Rollup? = null,
) {
    data class RichText(
        val type: String = "text",
        val text: Text? = null,
        val annotations: Annotations? = null,
        val plainText: String? = null,
        val href: String? = null,
    ) {

        data class Text(
            val content: String? = null,
            val link: Link? = null
        )

        data class Link(
            val type: String? = null,
            val url: String? = null
        )

        data class Annotations(
            val bold: Boolean? = null,
            val italic: Boolean? = null,
            val strikethrough: Boolean? = null,
            val underline: Boolean? = null,
            val code: Boolean? = null,
            val color: String? = null,
        )
    }

    data class Date(
        val start: String? = null,
        val end: String? = null
    )

    data class Formula(
        val type: String
    )

    data class Rollup(
        val type: String
    )

    data class MultiSelect(
        val options: List<Option>? = null
    ) {
        data class Option(
            val id: String? = null,
            val name: String? = null,
            val color: String? = null,
        )
    }

    data class Select(
        val options: List<Option>? = null
    ) {
        data class Option(
            val id: String? = null,
            val name: String? = null,
            val color: String? = null,
        )
    }
}