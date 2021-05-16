package endpoint

import notion.api.v1.NotionClient
import org.junit.Test
import kotlin.test.assertNotNull

class SearchTest {

    @Test
    fun search() {
        NotionClient(token = System.getenv("NOTION_TOKEN")).use { client ->
            val searchResult = client.search("Test")
            assertNotNull(searchResult.results)
        }
    }
}