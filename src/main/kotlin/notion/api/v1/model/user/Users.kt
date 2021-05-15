package notion.api.v1.model.user

import kotlinx.serialization.Serializable
import notion.api.v1.model.common.HasObjectType
import notion.api.v1.model.common.Pagination

@Serializable
data class Users(
    override val objectType: String = "list",
    val results: List<User>,
    override val nextCursor: String? = null,
    override val hasMore: Boolean = false,
) : HasObjectType, Pagination