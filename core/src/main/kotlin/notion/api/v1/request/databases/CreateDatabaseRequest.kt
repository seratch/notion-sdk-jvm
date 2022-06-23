package notion.api.v1.request.databases

import notion.api.v1.model.common.Cover
import notion.api.v1.model.common.Icon
import notion.api.v1.model.databases.DatabaseParent
import notion.api.v1.model.databases.DatabaseProperty
import notion.api.v1.model.databases.DatabasePropertySchema

data class CreateDatabaseRequest
@JvmOverloads
constructor(
    val parent: DatabaseParent,
    val title: List<DatabaseProperty.RichText>,
    val properties: Map<String, DatabasePropertySchema>,
    val description: List<DatabaseProperty.RichText>? = null,
    val isInline: Boolean? = null,
    val icon: Icon? = null,
    val cover: Cover? = null,
)
