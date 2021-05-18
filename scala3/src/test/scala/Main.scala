import notion.api.v1.ScalaNotionClient
import notion.api.v1.http.JavaNetHttpClient
import notion.api.v1.model.common.PropertyType
import notion.api.v1.model.databases.query.filter.PropertyFilter
import notion.api.v1.model.databases.query.filter.condition.TextFilter

import scala.jdk.CollectionConverters._

object Main extends App {

  val client = ScalaNotionClient(
    token = System.getenv("NOTION_TOKEN"),
    httpClient = new JavaNetHttpClient()
  )

  val users = client.listUsers(pageSize = 2)

  val databaseId = client
    .listDatabases()
    .getResults
    .asScala
    .find(_.getTitle.get(0).getPlainText == "Test Database")
    .get
    .getId

  val queryResult = client.queryDatabase(
    databaseId = databaseId,
    filter = {
      val filter = new PropertyFilter()
      filter.setProperty(PropertyType.Title)
      filter.setTitle {
        val title = new TextFilter()
        title.setContains("bug")
        title
      }
      filter
    },
    pageSize = 3
  )
}
