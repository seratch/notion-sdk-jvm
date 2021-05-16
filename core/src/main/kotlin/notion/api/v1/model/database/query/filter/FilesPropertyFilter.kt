package notion.api.v1.model.database.query.filter

data class FilesPropertyFilter(
    override val property: String? = null,
    val isEmpty: Boolean? = null,
    val isNotEmpty: Boolean? = null,
) : QueryFilter, PropertyFilter