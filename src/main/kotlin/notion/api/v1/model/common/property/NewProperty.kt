package notion.api.v1.model.common.property

data class NewProperty(
    val id: String,
    val type: String? = null,
    val title: List<RichText>? = null,
    val multiSelect: MultiSelect? = null,
    // TODO: add others
)