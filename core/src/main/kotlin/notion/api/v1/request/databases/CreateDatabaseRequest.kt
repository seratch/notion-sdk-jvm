package notion.api.v1.request.databases

import notion.api.v1.model.common.Cover
import notion.api.v1.model.common.Icon
import notion.api.v1.model.databases.DatabaseParent
import notion.api.v1.model.databases.DatabaseProperty
import notion.api.v1.model.databases.DatabasePropertySchema

data class CreateDatabaseRequest(
    val parent: DatabaseParent,
    val title: List<DatabaseProperty.RichText>,
    val properties: Map<String, DatabasePropertySchema>,
    val icon: Icon? = null,
    val cover: Cover? = null,
) {
  // For other JVM languages
  constructor(
      parent: DatabaseParent,
      title: List<DatabaseProperty.RichText>,
      properties: Map<String, DatabasePropertySchema>,
  ) : this(parent, title, properties, null, null)
}
