package notion.api.v1.model.database

import kotlinx.serialization.Serializable
import notion.api.v1.model.common.HasObjectType
import notion.api.v1.model.common.Pagination

@Serializable
data class Databases(
    override val objectType: String = "list",
    val results: List<Database>,
    override val nextCursor: String? = null,
    override val hasMore: Boolean = false,
) : HasObjectType, Pagination
