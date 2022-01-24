package notion.api.v1.request.oauth

data class ExchangeAuthCodeRequest
@JvmOverloads
constructor(
    val grantType: String = "authorization_code",
    val code: String,
    val state: String,
    val redirectUri: String,
)
