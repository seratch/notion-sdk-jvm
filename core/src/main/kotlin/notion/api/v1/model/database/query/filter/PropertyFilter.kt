package notion.api.v1.model.database.query.filter

interface PropertyFilter : QueryFilter {
    val property: String?
}
