package notion.api.v1.model.database.query.filter

interface QueryPropertyFilter : QueryFilter {
    val property: String?
}
