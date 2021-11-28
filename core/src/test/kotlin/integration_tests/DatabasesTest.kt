package integration_tests

import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import notion.api.v1.NotionClient
import notion.api.v1.model.common.*
import notion.api.v1.model.databases.DatabaseParent
import notion.api.v1.model.databases.DatabaseProperty as prop
import notion.api.v1.model.databases.RichTextPropertySchema
import notion.api.v1.model.databases.TitlePropertySchema
import notion.api.v1.model.databases.query.filter.CompoundFilter
import notion.api.v1.model.databases.query.filter.PropertyFilter
import notion.api.v1.model.databases.query.filter.condition.TextFilter
import notion.api.v1.model.databases.query.sort.QuerySort
import notion.api.v1.model.databases.query.sort.QuerySortDirection
import notion.api.v1.model.databases.query.sort.QuerySortTimestamp
import notion.api.v1.model.pages.PageParent
import notion.api.v1.model.pages.PageProperty
import org.junit.Test

class DatabasesTest {

  @Test
  fun creation() {
    NotionClient(token = System.getenv("NOTION_TOKEN")).use { client ->
      val databases = client.listDatabases()
      assertTrue { databases.results.isNotEmpty() }

      val testDatabase =
          databases.results.find { it.title.any { t -> t.plainText.contains("Test Database") } }!!

      val page =
          client.createPage(
              // Use the "Test Database" as this page's parent
              parent = PageParent.database(databaseId = testDatabase.id),
              properties = mapOf("Link" to PageProperty(url = "https://www.example.com")),
              icon = Emoji(emoji = "\uD83C\uDF89"),
              cover =
                  File.external(
                      url =
                          "https://www.notion.so/front-static/external/readme/images/api-hanging@2x.png"),
          )

      val title = "Test database ${System.currentTimeMillis()}"
      val database =
          client.createDatabase(
              parent = DatabaseParent.page(page.id),
              title =
                  listOf(
                      prop.RichText(text = prop.RichText.Text(content = title), plainText = title)),
              properties =
                  mapOf(
                      "Title" to TitlePropertySchema(), "Description" to RichTextPropertySchema()),
              icon = Emoji(emoji = "\uD83C\uDF89"),
              cover =
                  File.external(
                      url =
                          "https://www.notion.so/front-static/external/readme/images/api-hanging@2x.png"),
          )
      assertNotNull(database)
      val update1 =
          client.updateDatabase(
              databaseId = database.id,
              title =
                  listOf(
                      prop.RichText(
                          text = prop.RichText.Text(content = title + "_"),
                          plainText = title + "_")))
      assertNotNull(update1)
      assertEquals(title + "_", update1.title[0].text?.content)

      val update2 =
          client.updateDatabase(
              databaseId = database.id,
              properties =
                  mapOf(
                      "Title" to TitlePropertySchema(),
                      "Description___" to RichTextPropertySchema()))
      assertNotNull(update2)
      assertEquals(title + "_", update2.title[0].text?.content)
      assertTrue(update2.properties.keys.contains("Description___"))
    }
  }

  @Test
  fun list() {
    NotionClient(token = System.getenv("NOTION_TOKEN")).use { client ->
      val databases = client.listDatabases()
      assertNotNull(databases)
      assertEquals(ObjectType.List, databases.objectType)
      assertTrue { databases.results.isNotEmpty() }
      assertEquals(ObjectType.Database, databases.results[0].objectType)
    }
  }

  @Test
  fun query_property() {
    NotionClient(token = System.getenv("NOTION_TOKEN")).use { client ->
      // Find the "Test Database" from the list
      val database =
          client
              .search("Test Database")
              .results
              .find {
                it.objectType == ObjectType.Database &&
                    it.asDatabase().properties.containsKey("Severity")
              }
              ?.asDatabase()
              ?: throw IllegalStateException(
                  "Create a database named 'Test Database' and invite this app's user!")

      val queryResult =
          client.queryDatabase(
              databaseId = database.id,
              filter = PropertyFilter(property = "title", title = TextFilter(contains = "bug")),
              sorts =
                  listOf(
                      QuerySort(property = "title"),
                      QuerySort(
                          timestamp = QuerySortTimestamp.LastEditedTime,
                          direction = QuerySortDirection.Descending)),
              pageSize = 1,
          )
      assertNotNull(queryResult)
      assertTrue { queryResult.results.isNotEmpty() }
      assertEquals(ObjectType.Page, queryResult.results[0].objectType)
    }
  }

  @Test
  fun query_compound() {
    NotionClient(token = System.getenv("NOTION_TOKEN")).use { client ->
      val databases = client.listDatabases()
      assertTrue { databases.results.isNotEmpty() }

      val database =
          databases.results.find { it.title.any { t -> t.plainText.contains("Test Database") } }!!

      val queryResult =
          client.queryDatabase(
              databaseId = database.id,
              filter =
                  CompoundFilter(
                      and =
                          listOf(
                              PropertyFilter(
                                  property = "title", title = TextFilter(contains = "bug")))),
              pageSize = 1,
          )
      assertNotNull(queryResult)
      assertTrue { queryResult.results.isNotEmpty() }
      assertEquals(ObjectType.Page, queryResult.results[0].objectType)
    }
  }
}
