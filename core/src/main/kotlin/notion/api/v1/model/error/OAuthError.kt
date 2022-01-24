package notion.api.v1.model.error

// This data class does not have setters as developers never manually modify this
data class OAuthError
@JvmOverloads
constructor(
    val error: String,
)
