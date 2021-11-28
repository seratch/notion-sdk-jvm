package integration_tests

import kotlin.test.assertNotNull
import notion.api.v1.NotionClient
import org.junit.Test

class SearchTest {

  @Test
  fun search() {
    NotionClient(token = System.getenv("NOTION_TOKEN")).use { client ->
      val searchResult = client.search("Great example data")
      assertNotNull(searchResult.results)
    }
  }

  @Test
  fun database() {
    NotionClient(token = System.getenv("NOTION_TOKEN")).use { client ->
      val searchResult = client.search("Test Database")
      assertNotNull(searchResult.results)
    }
  }
}
