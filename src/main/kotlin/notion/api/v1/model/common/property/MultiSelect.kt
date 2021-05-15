package notion.api.v1.model.common.property

data class MultiSelect(
    val options: List<MultiSelectOption>? = null
)

data class MultiSelectOption(
    val id: String? = null,
    val name: String? = null,
    val color: String? = null,
)
