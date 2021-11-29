package integration_tests

import kotlin.test.assertNotNull
import notion.api.v1.NotionClient
import notion.api.v1.http.JavaNetHttpClient
import notion.api.v1.model.pages.Page
import notion.api.v1.model.pages.PageParent
import notion.api.v1.model.pages.PageProperty
import notion.api.v1.request.search.SearchRequest
import org.junit.Test

typealias prop = PageProperty

class SimpleTest {

  @Test
  fun pages() {
    NotionClient(token = System.getenv("NOTION_TOKEN")).use { client ->
      client.httpClient = JavaNetHttpClient()

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

      // All the options for "Severity" property (select type)
      val severityOptions = database.properties?.get("Severity")?.select?.options
      // All the options for "Tags" property (multi_select type)
      val tagOptions = database.properties?.get("Tags")?.multiSelect?.options
      // The user object for "Assignee" property (people type)
      val assignee = client.listUsers().results[0] // just picking the first user up

      val newPage: Page =
          client.createPage(
              // Use the "Test Database" as this page's parent
              parent = PageParent.database(databaseId = database.id),
              // Set values to the page's properties
              // (these must be pre-defined before this API call)
              properties =
                  mapOf(
                      "Title" to
                          prop(
                              title =
                                  listOf(
                                      PageProperty.RichText(
                                          text =
                                              PageProperty.RichText.Text(content = "Fix a bug")))),
                      "Severity" to prop(select = severityOptions?.find { it.name == "High" }),
                      "Tags" to prop(multiSelect = tagOptions),
                      "Due" to
                          prop(date = PageProperty.Date(start = "2021-05-13", end = "2021-12-31")),
                      "Velocity Points" to prop(number = 3),
                      "Assignee" to prop(people = listOf(assignee)),
                      "Done" to prop(checkbox = true),
                      "Link" to prop(url = "https://www.example.com"),
                      "Contact" to prop(email = "foo@example.com"),
                  ))
      assertNotNull(newPage)

      val patchResult =
          client.updatePage(
              pageId = newPage.id,
              // Update only "Severity" property
              properties =
                  mapOf(
                      "Severity" to prop(select = severityOptions?.find { it.name == "Medium" }),
                  ))
      assertNotNull(patchResult)

      val fetchedPage = client.retrievePage(newPage.id)
      assertNotNull(fetchedPage)
    }
  }
}
