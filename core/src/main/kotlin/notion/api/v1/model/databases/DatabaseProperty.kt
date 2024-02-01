package notion.api.v1.model.databases

import java.util.*
import notion.api.v1.model.common.*
import notion.api.v1.model.pages.Page
import notion.api.v1.model.users.User

open class DatabaseProperty
@JvmOverloads
constructor(
    val type: PropertyType,
    val id: String = UUID.randomUUID().toString(),
    val name: String? = null,
    var title: RichText? = null,
    var richText: RichText? = null,
    var number: Number? = null,
    var select: Select? = null,
    var status: Status? = null,
    var multiSelect: MultiSelect? = null,
    var date: Date? = null,
    var people: People? = null,
    var checkbox: Checkbox? = null,
    var url: Url? = null,
    var phoneNumber: PhoneNumber? = null,
    var relation: Relation? = null,
    var rollup: Rollup? = null,
    var email: Email? = null,
    var files: Files? = null,
    var formula: Formula? = null,
    val createdBy: CreatedBy? = null,
    val lastEditedBy: LastEditedBy? = null,
    val createdTime: CreatedTime? = null,
    val lastEditedTime: LastEditedTime? = null,
    val uniqueId: UniqueId? = null,
) {

  open class UniqueId @JvmOverloads constructor(var prefix: String?)

  open class RichText
  @JvmOverloads
  constructor(
      var type: RichTextType = RichTextType.Text,
      var text: Text? = null,
      var annotations: Annotations? = null,
      var plainText: String = "",
      var href: String? = null,
      val mention: Mention? = null,
  ) {

    open class Text @JvmOverloads constructor(var content: String? = null, var link: Link? = null)

    open class Link
    @JvmOverloads
    constructor(var type: RichTextLinkType? = null, var url: String? = null)

    open class Annotations
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

  open class Number
  @JvmOverloads
  constructor(
      var format: String? = null,
  )

  open class Date @JvmOverloads constructor(var start: String? = null, var end: String? = null)

  open class Relation
  @JvmOverloads
  constructor(
      var databaseId: String? = null,
      var syncedPropertyName: String? = null,
      var syncedPropertyId: String? = null,
  )

  open class Rollup
  @JvmOverloads
  constructor(
      var relationPropertyName: String? = null,
      var relationPropertyId: String? = null,
      var rollupPropertyName: String? = null,
      var rollupPropertyId: String? = null,
      var function: String? = null,
  )

  class People

  class Checkbox

  class Url

  class Files

  class PhoneNumber

  class Email

  open class Formula @JvmOverloads constructor(var expression: String? = null)

  open class MultiSelect @JvmOverloads constructor(var options: List<Option>? = null) {
    open class Option(
        var id: String? = null,
        var name: String? = null,
        var color: OptionColor? = null,
        var description: String? = null,
    )
  }

  data class Select @JvmOverloads constructor(val options: List<Option>? = null) {
    data class Option(
        val id: String? = null,
        val name: String? = null,
        val color: OptionColor? = null,
        val description: String? = null,
    )
  }

  data class Status
  @JvmOverloads
  constructor(
      val options: List<Option>? = null,
      val groups: List<OptionGroup>? = null,
  ) {
    data class Option(
        val id: String? = null,
        val name: String? = null,
        val color: OptionColor? = null,
        val description: String? = null,
    )
    data class OptionGroup(
        val id: String? = null,
        val name: String? = null,
        val color: OptionColor? = null,
        val optionIds: List<String>? = null,
    )
  }

  class CreatedBy

  class LastEditedBy

  class CreatedTime

  class LastEditedTime
}
