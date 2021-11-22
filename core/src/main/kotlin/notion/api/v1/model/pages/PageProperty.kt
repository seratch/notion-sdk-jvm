package notion.api.v1.model.pages

import java.util.*
import notion.api.v1.model.common.*
import notion.api.v1.model.databases.Database
import notion.api.v1.model.databases.DatabaseProperty
import notion.api.v1.model.users.User

data class PageProperty
@JvmOverloads
constructor(
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

  data class RichText
  @JvmOverloads
  constructor(
      val type: RichTextType = RichTextType.Text,
      var text: Text? = null,
      var annotations: Annotations? = null,
      var plainText: String? = null,
      var href: String? = null,
      val mention: Mention? = null,
  ) {

    data class Text @JvmOverloads constructor(var content: String? = null, var link: Link? = null)

    data class Link
    @JvmOverloads
    constructor(var type: RichTextLinkType? = null, var url: String? = null)

    data class Annotations
    @JvmOverloads
    constructor(
        var bold: Boolean? = null,
        var italic: Boolean? = null,
        var strikethrough: Boolean? = null,
        var underline: Boolean? = null,
        var code: Boolean? = null,
        var color: RichTextColor? = null,
    )

    open class Mention
    @JvmOverloads
    constructor(
        var type: RichTextMentionType? = null,
        var user: User? = null,
        val page: Page? = null,
        val database: Database? = null,
        val date: Date? = null,
    )
  }

  data class PageReference @JvmOverloads constructor(val id: String)

  data class File
  @JvmOverloads
  constructor(
      var name: String? = null,
      val type: FileType? = null,
      val file: FileDetails? = null,
      val external: ExternalFileDetails? = null,
  )

  data class Date @JvmOverloads constructor(var start: String? = null, var end: String? = null)

  data class Formula
  @JvmOverloads
  constructor(
      val type: FormulaType,
      var boolean: Boolean? = null,
      var date: Date? = null,
      var string: String? = null,
      var number: Number? = null,
  )

  data class Rollup
  @JvmOverloads
  constructor(
      val type: String,
      val number: Number? = null,
      val array: List<PageProperty>? = null,
      val function: String? = null,
  )
}
