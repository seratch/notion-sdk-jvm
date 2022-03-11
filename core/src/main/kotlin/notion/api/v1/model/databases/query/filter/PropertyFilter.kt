package notion.api.v1.model.databases.query.filter

import notion.api.v1.model.databases.query.filter.condition.*

open class PropertyFilter
@JvmOverloads
constructor(
    var property: String? = null, // The name or ID of the property to filter on.
    var title: TextFilter? = null,
    var richText: TextFilter? = null,
    var url: TextFilter? = null,
    var email: TextFilter? = null,
    var phoneNumber: TextFilter? = null,
    var number: NumberFilter? = null,
    var checkbox: CheckboxFilter? = null,
    var select: SelectFilter? = null,
    var multiSelect: MultiSelectFilter? = null,
    var date: DateFilter? = null,
    var createdDate: DateFilter? = null,
    var lastEditedDate: DateFilter? = null,
    var createdBy: PeopleFilter? = null,
    var lastEditedBy: PeopleFilter? = null,
    var file: FilesFilter? = null,
    var relation: RelationFilter? = null,
    var formula: FormulaFilter? = null,
) : QueryTopLevelFilter, CompoundFilterElement
