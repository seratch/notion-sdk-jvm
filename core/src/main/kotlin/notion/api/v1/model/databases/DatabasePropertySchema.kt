package notion.api.v1.model.databases

import notion.api.v1.model.common.OptionColor

interface DatabasePropertySchema

open class TitlePropertySchema @JvmOverloads constructor(val title: Map<String, Any> = emptyMap()) :
    DatabasePropertySchema

open class RichTextPropertySchema
@JvmOverloads
constructor(val richText: Map<String, Any> = emptyMap()) : DatabasePropertySchema

open class NumberPropertySchema @JvmOverloads constructor(val number: Number = Number()) :
    DatabasePropertySchema {
  open class Number(val format: String? = null)
}

open class SelectOptionSchema
@JvmOverloads
constructor(val name: String, val color: OptionColor? = null, val id: String? = null)

open class SelectPropertySchema
@JvmOverloads
constructor(select: List<SelectOptionSchema>? = null) : DatabasePropertySchema {
  val select = mapOf("options" to select)
}

open class MultiSelectPropertySchema
@JvmOverloads
constructor(multiSelect: List<SelectOptionSchema>? = null) : DatabasePropertySchema {
  val multiSelect = mapOf("options" to multiSelect)
}

class DatePropertySchema @JvmOverloads constructor(val date: Map<String, Any> = emptyMap()) :
    DatabasePropertySchema

open class PeoplePropertySchema
@JvmOverloads
constructor(val people: Map<String, Any> = emptyMap()) : DatabasePropertySchema

open class FilePropertySchema @JvmOverloads constructor(val files: Map<String, Any> = emptyMap()) :
    DatabasePropertySchema

open class CheckboxPropertySchema
@JvmOverloads
constructor(val checkbox: Map<String, Any> = emptyMap()) : DatabasePropertySchema

open class URLPropertySchema @JvmOverloads constructor(val url: Map<String, Any> = emptyMap()) :
    DatabasePropertySchema

open class EmailPropertySchema @JvmOverloads constructor(val email: Map<String, Any> = emptyMap()) :
    DatabasePropertySchema

open class PhoneNumberPropertySchema
@JvmOverloads
constructor(val phoneNumber: Map<String, Any> = emptyMap()) : DatabasePropertySchema

open class CreatedTimePropertySchema
@JvmOverloads
constructor(val createdTime: Map<String, Any> = emptyMap()) : DatabasePropertySchema

open class CreatedByPropertySchema
@JvmOverloads
constructor(val createdBy: Map<String, Any> = emptyMap()) : DatabasePropertySchema

open class LastEditedTimePropertySchema
@JvmOverloads
constructor(val lastEditedTime: Map<String, Any> = emptyMap()) : DatabasePropertySchema

open class LastEditedByPropertySchema
@JvmOverloads
constructor(val lastEditedBy: Map<String, Any> = emptyMap()) : DatabasePropertySchema
