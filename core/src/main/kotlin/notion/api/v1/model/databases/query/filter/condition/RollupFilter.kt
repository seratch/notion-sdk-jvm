package notion.api.v1.model.databases.query.filter.condition

open class RollupFilter
@JvmOverloads
constructor(
    var any: Content? = null,
    var every: Content? = null,
    var none: Content? = null,
) {
  open class Content
  @JvmOverloads
  constructor(
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
      var timestamp: String? = null, // "created_time", "last_edited_time"
      var createdTime: TimestampFilter? = null,
      var lastEditedTime: TimestampFilter? = null,
      var createdBy: PeopleFilter? = null,
      var lastEditedBy: PeopleFilter? = null,
      var file: FilesFilter? = null,
      var relation: RelationFilter? = null,
      var formula: FormulaFilter? = null,
      var status: StatusFilter? = null,
      var rollup: RollupFilter? = null,
  )
}
