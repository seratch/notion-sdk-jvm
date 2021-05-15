package notion.api.v1.model.database.query.filter

interface NumberFilter {
    val equals: Int?
    val doesNotEqual: Int?
    val greaterThan: Int?
    val lessThan: Int?
    val greaterThanOrEqualTo: Int?
    val lessThanOrEqualTo: Int?
    val isEmpty: Boolean?
    val isNotEmpty: Boolean?
}
