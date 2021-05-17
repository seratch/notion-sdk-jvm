import notion.api.v1.ScalaNotionClient
import notion.api.v1.http.JavaNetHttpClient
import notion.api.v1.model.databases.query.filter.condition.TextFilter
import notion.api.v1.model.databases.query.filter.PropertyFilter
import notion.api.v1.model.pages.PageProperty
import notion.api.v1.model.pages.PageProperty.RichText
import notion.api.v1.model.pages.PageProperty.RichText.Text
import notion.api.v1.request.pages.CreatePageRequest.Parent
import org.junit.Assert.assertNotNull
import org.junit.Test

import scala.jdk.CollectionConverters._

class SimpleTest {

  @Test
  def databases(): Unit = {
    val client = ScalaNotionClient(System.getenv("NOTION_TOKEN"))
    client.httpClient = new JavaNetHttpClient()
    try {
      val databases = client.listDatabases()
      assertNotNull(databases)

      val databaseId = databases.getResults.asScala
        .filter(_.getTitle.get(0).getPlainText == "Test Database")
        .head
        .getId

      val database = client.retrieveDatabase(databaseId)
      assertNotNull(database)

      val queryResult =
        client.queryDatabase(
          databaseId,
          filter = {
            val f = new PropertyFilter()
            f.setProperty("Title")
            val title = new TextFilter()
            title.setContains("bug")
            f.setTitle(title)
            f
          },
          pageSize = 1
        )
      assertNotNull(queryResult)
    } finally client.close()
  }

  @Test
  def pages(): Unit = {
    val client = ScalaNotionClient(
      token = System.getenv("NOTION_TOKEN"),
      httpClient = new JavaNetHttpClient()
    )
    try {
      val databases = client.listDatabases()
      assertNotNull(databases)

      val databaseId = databases.getResults.asScala
        .filter(_.getTitle.get(0).getPlainText == "Test Database")
        .head
        .getId

      val page = client.createPage(
        parent = {
          val p = new Parent("database")
          p.setDatabaseId(databaseId)
          p
        },
        properties = Map(
          "Title" -> {
            val p = new PageProperty()
            p.setTitle(List({
              val rt = new RichText()
              rt.setText(new Text("foo", null))
              rt
            }).asJava)
            p
          }
        )
      )
      assertNotNull(page)

      val updatedPage = client.updatePageProperties(
        pageId = page.getId,
        properties = Map(
          "Title" -> {
            val p = new PageProperty()
            p.setTitle(List({
              val rt = new RichText()
              rt.setText(new Text("This is an important task!", null))
              rt
            }).asJava)
            p
          }
        )
      )
      assertNotNull(updatedPage)

      val fetchedPage = client.retrievePage(page.getId)
      assertNotNull(fetchedPage)

    } finally client.close()
  }

}
