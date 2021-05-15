package notion.api.v1.model.database.query.filter

interface TextFilter {
    val equals: String?
    val doesNotEqual: String?
    val contains: String?
    val doesNotContain: String?
    val startsWith: String?
    val endsWith: String?
    val isEmpty: Boolean?
    val isNotEmpty: Boolean?
}