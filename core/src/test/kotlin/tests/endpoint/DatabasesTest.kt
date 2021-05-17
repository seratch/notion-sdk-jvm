package tests.endpoint

import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import notion.api.v1.NotionClient
import notion.api.v1.model.databases.query.filter.*
import notion.api.v1.model.databases.query.filter.condition.TextFilter
import org.junit.Test

class DatabasesTest {

  @Test
  fun list() {
    NotionClient(token = System.getenv("NOTION_TOKEN")).use { client ->
      val databases = client.listDatabases()
      assertNotNull(databases)
      assertEquals("list", databases.objectType)
      assertTrue { databases.results.isNotEmpty() }
    }
  }

  @Test
  fun query_property() {
    NotionClient(token = System.getenv("NOTION_TOKEN")).use { client ->
      val databases = client.listDatabases()
      assertTrue { databases.results.isNotEmpty() }

      val database = databases.results.find { it.title?.get(0)?.plainText == "Test Database" }!!

      val queryResult =
          client.queryDatabase(
              databaseId = database.id,
              filter = PropertyFilter(property = "Title", title = TextFilter(contains = "bug")),
              pageSize = 1,
          )
      assertNotNull(queryResult)
      assertTrue { queryResult.results.isNotEmpty() }
    }
  }

  @Test
  fun query_compound() {
    NotionClient(token = System.getenv("NOTION_TOKEN")).use { client ->
      val databases = client.listDatabases()
      assertTrue { databases.results.isNotEmpty() }

      val database = databases.results.find { it.title?.get(0)?.plainText == "Test Database" }!!

      val queryResult =
          client.queryDatabase(
              databaseId = database.id,
              filter =
                  CompoundFilter(
                      and =
                          listOf(
                              PropertyFilter(
                                  property = "Title", title = TextFilter(contains = "bug")))),
              pageSize = 1,
          )
      assertNotNull(queryResult)
      assertTrue { queryResult.results.isNotEmpty() }
    }
  }
}
