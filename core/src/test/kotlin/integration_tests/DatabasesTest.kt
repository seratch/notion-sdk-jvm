package integration_tests

import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import notion.api.v1.NotionClient
import notion.api.v1.model.common.*
import notion.api.v1.model.databases.*
import notion.api.v1.model.databases.DatabaseProperty as prop
import notion.api.v1.model.databases.query.filter.CompoundFilter
import notion.api.v1.model.databases.query.filter.PropertyFilter
import notion.api.v1.model.databases.query.filter.condition.DateFilter
import notion.api.v1.model.databases.query.filter.condition.TextFilter
import notion.api.v1.model.databases.query.filter.condition.TimestampFilter
import notion.api.v1.model.databases.query.sort.QuerySort
import notion.api.v1.model.databases.query.sort.QuerySortDirection
import notion.api.v1.model.databases.query.sort.QuerySortTimestamp
import notion.api.v1.model.pages.PageParent
import notion.api.v1.model.pages.PageProperty
import notion.api.v1.request.search.SearchRequest
import org.junit.Ignore
import org.junit.Test

class DatabasesTest {

  @Test
  fun creation() {
    NotionClient(token = System.getenv("NOTION_TOKEN")).use { client ->
      val testDatabase =
          client
              .search(
                  query = "Test Database",
                  filter = SearchRequest.SearchFilter("database", property = "object"))
              .results
              .find { it.asDatabase().properties.containsKey("Severity") }
              ?.asDatabase()
              ?: throw IllegalStateException(
                  "Create a database named 'Test Database' and invite this app's user!")

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

      val statusTODOID = UUID.randomUUID().toString()
      val statusInProgressID = UUID.randomUUID().toString()
      val statusDoneID = UUID.randomUUID().toString()
      val statusWontFixID = UUID.randomUUID().toString()
      val database =
          client.createDatabase(
              parent = DatabaseParent.page(page.id),
              title =
                  listOf(
                      prop.RichText(text = prop.RichText.Text(content = title), plainText = title)),
              description =
                  listOf(
                      prop.RichText(text = prop.RichText.Text(content = title), plainText = title)),
              isInline = true,
              properties =
                  mapOf(
                      "Title" to TitlePropertySchema(),
                      "Description" to RichTextPropertySchema(),
                      "Type" to
                          SelectPropertySchema(
                              listOf(
                                  SelectOptionSchema(name = "TODO", color = OptionColor.Brown),
                                  SelectOptionSchema(
                                      name = "In progress", color = OptionColor.Blue),
                                  SelectOptionSchema(name = "Done", color = OptionColor.Orange),
                              )),
                      "My Status" to
                          StatusPropertySchema(
                              options =
                                  listOf(
                                      StatusOptionSchema(
                                          id = statusTODOID,
                                          name = "TODO",
                                          color = OptionColor.Brown),
                                      StatusOptionSchema(
                                          id = statusInProgressID,
                                          name = "In progress",
                                          color = OptionColor.Blue),
                                      StatusOptionSchema(
                                          id = statusDoneID,
                                          name = "Done",
                                          color = OptionColor.Gray),
                                      StatusOptionSchema(
                                          id = statusWontFixID,
                                          name = "Won't fix",
                                          color = OptionColor.Gray),
                                  ),
                              groups =
                                  listOf(
                                      StatusOptionGroupSchema(
                                          id = UUID.randomUUID().toString(),
                                          name = "TODO",
                                          color = OptionColor.Brown,
                                          optionIds = listOf(statusTODOID),
                                      ),
                                      StatusOptionGroupSchema(
                                          id = UUID.randomUUID().toString(),
                                          name = "In Progress",
                                          color = OptionColor.Brown,
                                          optionIds = listOf(statusInProgressID),
                                      ),
                                      StatusOptionGroupSchema(
                                          id = UUID.randomUUID().toString(),
                                          name = "Done",
                                          color = OptionColor.Brown,
                                          optionIds = listOf(statusDoneID, statusWontFixID),
                                      ),
                                  )),
                      "Tags" to
                          MultiSelectPropertySchema(
                              listOf(
                                  SelectOptionSchema(name = "One", color = OptionColor.Brown),
                                  SelectOptionSchema(name = "Two", color = OptionColor.Blue),
                                  SelectOptionSchema(name = "Three", color = OptionColor.Orange),
                              )),
                  ),
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

  // notion.api.v1.exception.NotionAPIError:
  // Got an error from Notion (status: 400, code: validation_error, message: This API is
  // deprecated.)
  @Ignore
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
  fun query_date_timestamp() {
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

      val today = LocalDate.now(ZoneId.of("UTC")).toString()

      // timestamp filter
      assertNotNull(
          client.queryDatabase(
              databaseId = database.id,
              filter =
                  PropertyFilter(
                      timestamp = "created_time", createdTime = TimestampFilter(equals = today)),
              pageSize = 1,
          ))
      // date filter
      assertNotNull(
          client.queryDatabase(
              databaseId = database.id,
              filter = PropertyFilter(property = "Created Time", date = DateFilter(equals = today)),
              pageSize = 1,
          ))
    }
  }

  @Test
  fun query_compound() {
    NotionClient(token = System.getenv("NOTION_TOKEN")).use { client ->
      val database =
          client
              .search(
                  query = "Test Database",
                  filter = SearchRequest.SearchFilter("database", property = "object"))
              .results
              .find { it.asDatabase().properties.containsKey("Severity") }
              ?.asDatabase()!!

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

      val nestedCompoundQuery1 =
          CompoundFilter(
              and =
                  listOf(PropertyFilter(property = "title", title = TextFilter(contains = "bug"))))
      val nestedCompoundQuery2 =
          CompoundFilter(
              and =
                  listOf(PropertyFilter(property = "title", title = TextFilter(contains = "test"))))

      val nestedQueryResult =
          client.queryDatabase(
              databaseId = database.id,
              filter = CompoundFilter(or = listOf(nestedCompoundQuery1, nestedCompoundQuery2)),
              pageSize = 1,
          )
      assertNotNull(nestedQueryResult)
      assertTrue { nestedQueryResult.results.isNotEmpty() }
      assertEquals(ObjectType.Page, nestedQueryResult.results[0].objectType)
    }
  }

  @Test
  fun query_nested_compound() {
    NotionClient(token = System.getenv("NOTION_TOKEN")).use { client ->
      val database =
          client
              .search(
                  query = "Test Database",
                  filter = SearchRequest.SearchFilter("database", property = "object"))
              .results
              .find { it.asDatabase().properties.containsKey("Severity") }
              ?.asDatabase()!!

      val nestedCompoundQuery1 =
          CompoundFilter(
              and =
                  listOf(PropertyFilter(property = "title", title = TextFilter(contains = "bug"))))
      val nestedCompoundQuery2 =
          CompoundFilter(
              and =
                  listOf(PropertyFilter(property = "title", title = TextFilter(contains = "test"))))

      val nestedQueryResult =
          client.queryDatabase(
              databaseId = database.id,
              filter = CompoundFilter(or = listOf(nestedCompoundQuery1, nestedCompoundQuery2)),
              pageSize = 1,
          )
      assertNotNull(nestedQueryResult)
      assertTrue { nestedQueryResult.results.isNotEmpty() }
      assertEquals(ObjectType.Page, nestedQueryResult.results[0].objectType)
    }
  }
}
