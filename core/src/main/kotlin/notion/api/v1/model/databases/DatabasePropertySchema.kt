package notion.api.v1.model.databases

import notion.api.v1.model.common.OptionColor

interface DatabasePropertySchema

open class TitlePropertySchema(val title: Map<String, Any> = emptyMap()) : DatabasePropertySchema

open class RichTextPropertySchema(val richText: Map<String, Any> = emptyMap()) :
    DatabasePropertySchema

open class NumberPropertySchema(val number: Number = Number()) : DatabasePropertySchema {
  open class Number(val format: String? = null)
}

open class SelectOptionSchema(val name: String, val color: OptionColor? = null)

open class SelectPropertySchema(val select: List<SelectOptionSchema>? = null) :
    DatabasePropertySchema

open class MultiSelectPropertySchema(val multiSelect: List<SelectOptionSchema>? = null) :
    DatabasePropertySchema

class DatePropertySchema(val date: Map<String, Any> = emptyMap()) : DatabasePropertySchema

open class PeoplePropertySchema(val people: Map<String, Any> = emptyMap()) : DatabasePropertySchema

open class FilePropertySchema(val files: Map<String, Any> = emptyMap()) : DatabasePropertySchema

open class CheckboxPropertySchema(val checkbox: Map<String, Any> = emptyMap()) :
    DatabasePropertySchema

open class URLPropertySchema(val url: Map<String, Any> = emptyMap()) : DatabasePropertySchema

open class EmailPropertySchema(val email: Map<String, Any> = emptyMap()) : DatabasePropertySchema

open class PhoneNumberPropertySchema(val phoneNumber: Map<String, Any> = emptyMap()) :
    DatabasePropertySchema

open class CreatedTimePropertySchema(val createdTime: Map<String, Any> = emptyMap()) :
    DatabasePropertySchema

open class CreatedByPropertySchema(val createdBy: Map<String, Any> = emptyMap()) :
    DatabasePropertySchema

open class LastEditedTimePropertySchema(val lastEditedTime: Map<String, Any> = emptyMap()) :
    DatabasePropertySchema

open class LastEditedByPropertySchema(val lastEditedBy: Map<String, Any> = emptyMap()) :
    DatabasePropertySchema
