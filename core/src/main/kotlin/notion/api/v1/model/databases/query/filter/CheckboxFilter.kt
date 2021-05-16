package notion.api.v1.model.databases.query.filter

interface CheckboxFilter {
    val equals: String?
    val doesNotEqual: String?
}
