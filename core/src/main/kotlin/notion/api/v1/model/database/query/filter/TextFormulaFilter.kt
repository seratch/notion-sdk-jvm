package notion.api.v1.model.database.query.filter

data class TextFormulaFilter(
    override val equals: String? = null,
    override val doesNotEqual: String? = null,
    override val contains: String? = null,
    override val doesNotContain: String? = null,
    override val startsWith: String? = null,
    override val endsWith: String? = null,
    override val isEmpty: Boolean? = null,
    override val isNotEmpty: Boolean? = null,
) : QueryFilter, TextFilter