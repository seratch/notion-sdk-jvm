import notion.api.v1.NotionClient
import notion.api.v1.model.common.ObjectType
import notion.api.v1.model.pages.PageParent
import notion.api.v1.request.search.SearchRequest
import notion.api.v1.model.pages.PageProperty

fun main() {
  NotionClient(token = System.getenv("NOTION_TOKEN")).use { client ->
    // Find the "Test Database" from the list
    val database = client
      .search(
        query = "Test Database",
        filter = SearchRequest.SearchFilter("database", property = "object")
      )
      .results
      .find { it.asDatabase().properties.containsKey("Severity") }
      ?.asDatabase()
      ?: error("Create a database named 'Test Database' and invite this app's user!")
    // Alternatively if you know the UUID of the Database, use `val database = client.retrieveDatabase("...")`.

    // All the options for "Severity" property (select type)
    val severityOptions = database.properties["Severity"]!!.select!!.options!!
    // All the options for "Tags" property (multi_select type)
    val tagOptions = database.properties["Tags"]!!.multiSelect!!.options!!
    // A user object for "Assignee" property (people type)
    val assignee = client.listUsers().results.first() // Just picking a random user.

    // Create a new page in the database
    val newPage = client.createPage(
      // Use the "Test Database" as this page's parent
      parent = PageParent.database(database.id),
      // Set values to the page's properties
      // (Values of referenced options, people, and relations must be pre-defined before this API call!)
      properties = mapOf(
        "Title" to PageProperty(title = "Fix a bug".asRichText()),
        "Severity" to PageProperty(select = severityOptions.single { it.name == "High" }),
        "Tags" to PageProperty(multiSelect = listOf("Tag1", "Tag2").map { tag -> tagOptions.single { it.name == tag } }),
        "Due" to PageProperty(date = PageProperty.Date(start = "2021-05-13", end = "2021-12-31")),
        "Velocity Points" to PageProperty(number = 3),
        "Assignee" to PageProperty(people = listOf(assignee)),
        "Done" to PageProperty(checkbox = true),
        "Link" to PageProperty(url = "https://www.example.com"),
        "Contact" to PageProperty(email = "foo@example.com"),
      )
    )
    
    // Properties can be addressed by their ID too.
    val severityId = newPage.properties["Severity"]!!.id

    // Update properties in the page
    val updatedPage = client.updatePage(
        pageId = newPage.id,
        // Update only "Severity" property
        properties = mapOf(
          severityId to PageProperty(select = severityOptions.single { it.name == "Medium" }),
        )
      )

    // Fetch the latest data of the page
    val retrievedPage = client.retrievePage(newPage.id)
  }
}

private fun String.asRichText(): List<PageProperty.RichText> =
  listOf(PageProperty.RichText(text = PageProperty.RichText.Text(content = this)))
