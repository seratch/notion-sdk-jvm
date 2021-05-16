import notion.api.v1.NotionClient
import notion.api.v1.model.pages.PageProperty
import notion.api.v1.request.pages.CreatePageRequest
import notion.api.v1.request.pages.UpdatePagePropertiesRequest
import java.lang.IllegalStateException

fun main() {
    val client = NotionClient(token = System.getenv("NOTION_TOKEN"))
    client.use {
        // Look up all databases that this app can access
        val databases = client.listDatabases()
        // Find the "Test Database" from the list
        val database = databases.results.find { it.title?.first()?.plainText == "Test Database" }
            ?: throw IllegalStateException("Create a database named 'Test Database' and invite this app's user!")
        // All the options for "Severity" property (select type)
        val severityOptions = database.properties?.get("Severity")?.select?.options
        val tagOptions = database.properties?.get("Tags")?.multiSelect?.options

        // Create a new page in the database
        val newPage = client.createPage(CreatePageRequest(
            parent = CreatePageRequest.Parent(type = "database", databaseId = database.id),
            properties = mapOf(
                "Title" to PageProperty(
                    title = listOf(PageProperty.RichText(
                        text = PageProperty.RichText.Text(content = "Fix a bug")
                    ))
                ),
                "Severity" to PageProperty(select = severityOptions?.find { it.name == "High" }),
                "Tags" to PageProperty(multiSelect = tagOptions),
                "Due" to PageProperty(
                    date = PageProperty.Date(start = "2021-05-13", end = "2021-12-31")
                ),
                "Velocity Points" to PageProperty(number = 3),
                "Assignee" to PageProperty(people = listOf(client.listUsers().results[0])),
                "Done" to PageProperty(checkbox = true),
                "Link" to PageProperty(url = "https://www.example.com"),
                "Contact" to PageProperty(email = "foo@example.com"),
            )
        ))

        // Update properties in the page
        val updatedPage = client.updatePageProperties(UpdatePagePropertiesRequest(
            pageId = newPage.id,
            properties = mapOf(
                "Severity" to PageProperty(select = severityOptions?.find { it.name == "Medium" }),
            )
        ))

        // Fetch the latest data of the page
        val retrievedPage = client.retrievePage(newPage.id)
    }
}