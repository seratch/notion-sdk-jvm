package integration_tests

import kotlin.test.*
import notion.api.v1.NotionClient
import notion.api.v1.model.pages.Page
import notion.api.v1.model.pages.PageParent
import notion.api.v1.model.pages.PageProperty
import notion.api.v1.request.search.SearchRequest
import org.junit.Ignore
import org.junit.Test

class PagesTest {

  @Ignore // same with integration_tests.SimpleTest
  @Test
  fun allOps() {
    NotionClient(token = System.getenv("NOTION_TOKEN")).use { client ->
      // Find the "Test Database" from the list
      val database =
          client
              .search(
                  query = "Test Database",
                  filter = SearchRequest.SearchFilter("database", property = "object"))
              .results
              .find { it.asDatabase().properties.containsKey("Severity") }
              ?.asDatabase()
              ?: throw IllegalStateException(
                  "Create a database named 'Test Database' and invite this app's user!")

      val newPage: Page =
          client.createPage(
              parent = PageParent.database(databaseId = database.id),
              properties =
                  mapOf(
                      "Title" to
                          PageProperty(
                              title =
                                  listOf(
                                      PageProperty.RichText(
                                          text =
                                              PageProperty.RichText.Text(content = "Fix a bug")))),
                      "Description" to
                          PageProperty(
                              richText =
                                  listOf(
                                      PageProperty.RichText(
                                          text =
                                              PageProperty.RichText.Text(
                                                  content =
                                                      "The bug is a minor one but one of our major customers have been affected by it for a while.")))),
                      "Due" to
                          PageProperty(
                              date = PageProperty.Date(start = "2021-05-13", end = "2021-12-31")),
                      "Severity" to
                          PageProperty(
                              select =
                                  database.properties["Severity"]?.select?.options?.find {
                                    it.name == "High"
                                  }),
                  ))
      assertNotNull(newPage)

      val patchResult =
          client.updatePage(
              pageId = newPage.id,
              properties =
                  mapOf(
                      "Due" to PageProperty(date = PageProperty.Date(start = "2021-12-31")),
                      "Severity" to
                          PageProperty(
                              select =
                                  database.properties["Severity"]?.select?.options?.find {
                                    it.name == "Medium"
                                  }),
                  ))
      assertNotEquals(newPage.lastEditedTime, patchResult.lastEditedTime)

      val fetchedPage = client.retrievePage(newPage.id)
      assertEquals(patchResult.lastEditedTime, fetchedPage.lastEditedTime)
      assertFalse(fetchedPage.archived!!)

      val archivedPage =
          client.updatePage(pageId = newPage.id, properties = emptyMap(), archived = true)
      assertTrue(archivedPage.archived!!)
    }
  }
}
