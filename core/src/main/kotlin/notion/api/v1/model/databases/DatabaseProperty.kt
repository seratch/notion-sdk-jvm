package notion.api.v1.model.databases

data class DatabaseProperty(
    val type: String,
    val id: String,
    val title: RichText? = null,
    val richText: RichText? = null,
    val number: Number? = null,
    val select: Select? = null,
    val multiSelect: MultiSelect? = null,
    val date: Date? = null,
    val people: People? = null,
    val checkbox: Checkbox? = null,
    val url: Url? = null,
    val phoneNumber: PhoneNumber? = null,
    val relation: Relation? = null,
    val rollup: Rollup? = null,
    val email: Email? = null,
    val files: Files? = null,
    val formula: Formula? = null,
    val createdBy: CreatedBy? = null,
    val lastEditedBy: LastEditedBy? = null,
    val createdTime: CreatedTime? = null,
    val lastEditedTime: LastEditedTime? = null,
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

    data class Number(
        val format: String? = null,
    )

    data class Date(
        val start: String? = null,
        val end: String? = null
    )

    data class Relation(
        val databaseId: String? = null,
        val syncedPropertyName: String? = null,
        val syncedPropertyId: String? = null,
    )

    data class Rollup(
        val relationPropertyName: String? = null,
        val relationPropertyId: String? = null,
        val rollupPropertyName: String? = null,
        val rollupPropertyId: String? = null,
        val function: String? = null,
    )

    class People

    class Checkbox

    class Url

    class Files

    class PhoneNumber

    class Email

    data class Formula(
        val expression: String? = null
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

    class CreatedBy

    class LastEditedBy

    class CreatedTime

    class LastEditedTime
}