package notion.api.v1.model.database.query.filter

interface CheckboxFilter {
    val equals: String?
    val doesNotEqual: String?
}
