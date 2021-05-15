package notion.api.v1.model.database.query.filter

import kotlinx.serialization.Serializable

@Serializable
data class CheckboxFormulaFilter(
    override val equals: String? = null,
    override val doesNotEqual: String? = null,
) : CheckboxFilter
