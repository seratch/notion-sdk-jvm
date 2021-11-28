package notion.api.v1.request.databases

import notion.api.v1.model.databases.DatabaseProperty
import notion.api.v1.model.databases.DatabasePropertySchema

data class UpdateDatabaseRequest
@JvmOverloads
constructor(
    @Transient val id: String,
    val title: List<DatabaseProperty.RichText>? = null,
    val properties: Map<String, DatabasePropertySchema>? = null,
)
