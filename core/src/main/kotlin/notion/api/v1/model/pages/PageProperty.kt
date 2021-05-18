package notion.api.v1.model.pages

import java.util.*
import notion.api.v1.model.common.*
import notion.api.v1.model.databases.Database
import notion.api.v1.model.databases.DatabaseProperty
import notion.api.v1.model.users.User

data class PageProperty(
    val id: String = UUID.randomUUID().toString(),
    var type: PropertyType? = null,
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
      val type: RichTextType = RichTextType.Text,
      var text: Text? = null,
      var annotations: Annotations? = null,
      var plainText: String? = null,
      var href: String? = null,
      val mention: Mention? = null,
  ) {

    data class Text(var content: String? = null, var link: Link? = null)

    data class Link(var type: RichTextLinkType? = null, var url: String? = null)

    data class Annotations(
        var bold: Boolean? = null,
        var italic: Boolean? = null,
        var strikethrough: Boolean? = null,
        var underline: Boolean? = null,
        var code: Boolean? = null,
        var color: RichTextColor? = null,
    )

    open class Mention(
        var type: RichTextMentionType? = null,
        var user: User? = null,
        val page: Page? = null,
        val database: Database? = null,
        val date: Date? = null,
    )
  }

  data class PageReference(val id: String)

  data class File(var name: String? = null)

  data class Date(var start: String? = null, var end: String? = null)

  data class Formula(
      val type: FormulaType,
      var boolean: Boolean? = null,
      var date: Date? = null,
      var string: String? = null,
      var number: Number? = null,
  ) {
    // For other JVM languages
    constructor(type: FormulaType) : this(type, null, null, null, null)
  }

  data class Rollup(val type: String)
}
