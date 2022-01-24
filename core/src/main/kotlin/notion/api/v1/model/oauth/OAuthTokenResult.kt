package notion.api.v1.model.oauth

import notion.api.v1.model.users.User

data class OAuthTokenResult
@JvmOverloads
constructor(
    val accessToken: String,
    val tokenType: String,
    val workspaceId: String,
    val workspaceName: String?,
    val workspaceIcon: String?,
    val botId: String,
    val owner: Owner,
)

data class Owner @JvmOverloads constructor(val type: String, val user: User?)
