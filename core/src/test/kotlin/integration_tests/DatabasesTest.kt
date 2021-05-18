package integration_tests

import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import notion.api.v1.NotionClient
import notion.api.v1.model.common.ObjectType
import notion.api.v1.model.common.PropertyType
import notion.api.v1.model.databases.query.filter.CompoundFilter
import notion.api.v1.model.databases.query.filter.PropertyFilter
import notion.api.v1.model.databases.query.filter.condition.TextFilter
import notion.api.v1.model.databases.query.sort.QuerySort
import notion.api.v1.model.databases.query.sort.QuerySortDirection
import notion.api.v1.model.databases.query.sort.QuerySortTimestamp
import org.junit.Test

class DatabasesTest {

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
      val databases = client.listDatabases()
      assertTrue { databases.results.isNotEmpty() }

      val database =
          databases.results.find { it.title.any { t -> t.plainText.contains("Test Database") } }!!

      val queryResult =
          client.queryDatabase(
              databaseId = database.id,
              filter =
                  PropertyFilter(
                      property = PropertyType.Title, title = TextFilter(contains = "bug")),
              sorts =
                  listOf(
                      QuerySort(property = PropertyType.Title),
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
                                  property = PropertyType.Title,
                                  title = TextFilter(contains = "bug")))),
              pageSize = 1,
          )
      assertNotNull(queryResult)
      assertTrue { queryResult.results.isNotEmpty() }
      assertEquals(ObjectType.Page, queryResult.results[0].objectType)
    }
  }
}
