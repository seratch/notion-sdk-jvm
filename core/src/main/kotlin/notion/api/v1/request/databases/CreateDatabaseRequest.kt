package notion.api.v1.request.databases

import notion.api.v1.model.databases.DatabaseParent
import notion.api.v1.model.databases.DatabaseProperty
import notion.api.v1.model.databases.DatabasePropertySchema

data class CreateDatabaseRequest(
    val parent: DatabaseParent,
    val title: List<DatabaseProperty.RichText>,
    val properties: Map<String, DatabasePropertySchema>,
)
