package notion.api.v1.model.pages

import notion.api.v1.model.databases.DatabaseProperty
import notion.api.v1.model.users.User
import java.util.*

data class PageProperty(
    val id: String = UUID.randomUUID().toString(),
    val type: String? = null,
    val title: List<RichText>? = null,
    val richText: List<RichText>? = null,
    val select: DatabaseProperty.Select.Option? = null,
    val multiSelect: List<DatabaseProperty.MultiSelect.Option>? = null,
    val number: Number? = null,
    val date: Date? = null,
    val people: List<User>? = null,
    val checkbox: Boolean? = null,
    val url: String? = null,
    val phoneNumber: String? = null,
    val email: String? = null,
    val files: List<File>? = null,
    val relation: List<PageReference>? = null,
    val formula: Formula? = null,
    val rollup: Rollup? = null,
    val createdBy: User? = null,
    val lastEditedBy: User? = null,
    val createdTime: String? = null,
    val lastEditedTime: String? = null,
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

    data class PageReference(val id: String)

    data class File(
        val name: String? = null
    )

    data class Date(
        val start: String? = null,
        val end: String? = null
    )

    data class Formula(
        val type: String,
        val boolean: Boolean? = null,
        val date: Date? = null,
        val string: String? = null,
        val number: Number? = null,
    )

    data class Rollup(
        val type: String
    )

}