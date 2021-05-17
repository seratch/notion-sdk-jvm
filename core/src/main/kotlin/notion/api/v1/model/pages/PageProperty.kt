package notion.api.v1.model.pages

import notion.api.v1.model.databases.DatabaseProperty
import notion.api.v1.model.users.User
import java.util.*

data class PageProperty(
    val id: String = UUID.randomUUID().toString(),
    var type: String? = null,
    var title: List<RichText>? = null,
    var richText: List<RichText>? = null,
    var select: DatabaseProperty.Select.Option? = null,
    var multiSelect: List<DatabaseProperty.MultiSelect.Option>? = null,
    var number: Number? = null,
    var date: Date? = null,
    var people: List<User>? = null,
    var checkbox: Boolean? = null,
    var url: String? = null,
    var phoneNumber: String? = null,
    var email: String? = null,
    var files: List<File>? = null,
    var relation: List<PageReference>? = null,
    var formula: Formula? = null,
    var rollup: Rollup? = null,
    val createdBy: User? = null,
    val lastEditedBy: User? = null,
    val createdTime: String? = null,
    val lastEditedTime: String? = null,
) {


    data class RichText(
        val type: String = "text",
        var text: Text? = null,
        var annotations: Annotations? = null,
        var plainText: String? = null,
        var href: String? = null,
    ) {

        data class Text(
            var content: String? = null,
            var link: Link? = null
        )

        data class Link(
            var type: String? = null,
            var url: String? = null
        )

        data class Annotations(
            var bold: Boolean? = null,
            var italic: Boolean? = null,
            var strikethrough: Boolean? = null,
            var underline: Boolean? = null,
            var code: Boolean? = null,
            var color: String? = null,
        )
    }

    data class PageReference(val id: String)

    data class File(
        var name: String? = null
    )

    data class Date(
        var start: String? = null,
        var end: String? = null
    )

    data class Formula(
        val type: String,
        var boolean: Boolean? = null,
        var date: Date? = null,
        var string: String? = null,
        var number: Number? = null,
    ) {
        // For other JVM languages
        constructor(type: String) : this(type, null, null, null, null)
    }

    data class Rollup(
        val type: String
    )

}