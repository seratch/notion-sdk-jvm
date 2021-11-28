package integration_tests

import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import notion.api.v1.NotionClient
import notion.api.v1.model.blocks.HeadingOneBlock
import notion.api.v1.model.blocks.ToDoBlock
import notion.api.v1.model.common.ObjectType
import notion.api.v1.model.databases.DatabaseProperty
import notion.api.v1.model.pages.Page
import notion.api.v1.model.pages.PageParent
import notion.api.v1.model.pages.PageProperty as prop
import org.junit.Test

class SimpleTest {

  private val pageChildren =
      listOf(
          HeadingOneBlock(
              heading1 =
                  HeadingOneBlock.Element(
                      text =
                          listOf(
                              prop.RichText(text = prop.RichText.Text(content = "head1 text"))))),
          ToDoBlock(
              toDo =
                  ToDoBlock.Element(
                      checked = true,
                      text =
                          listOf(
                              prop.RichText(
                                  text =
                                      prop.RichText.Text(content = "ToDo block element text"))))),
      )

  @Test
  fun pages() {
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

      // All the options for "Severity" property (select type)
      val severityOptions = database.properties?.get("Severity")?.select?.options

      // All the options for "Tags" property (multi_select type)
      // https://developers.notion.com/changelog/select-values-can-now-be-dynamically-created-via-create-and-update-page-endpoints-other-updates-since-public-beta-launch
      // val tagOptions = database.properties?.get("Tags")?.multiSelect?.options
      val tagOptions =
          listOf(
              DatabaseProperty.MultiSelect.Option(name = "Tag1"),
              DatabaseProperty.MultiSelect.Option(name = "Tag2"),
              DatabaseProperty.MultiSelect.Option(name = "Tag3"),
          )
      // The user object for "Assignee" property (people type)
      val assignee = client.listUsers().results[0] // just picking the first user up

      val titleProp =
          prop(title = listOf(prop.RichText(text = prop.RichText.Text(content = "Fix a bug"))))
      val severityProp = prop(select = severityOptions?.find { it.name == "High" })
      val newPage: Page =
          client.createPage(
              // Use the "Test Database" as this page's parent
              parent = PageParent.database(databaseId = database.id),
              // Set values to the page's properties
              // (these must be pre-defined before this API call)
              properties =
                  mapOf(
                      "Title" to titleProp,
                      "Severity" to severityProp,
                      "Tags" to prop(multiSelect = tagOptions),
                      "Due" to prop(date = prop.Date(start = "2021-05-13", end = "2021-12-31")),
                      "Velocity Points" to prop(number = 3),
                      "Assignee" to prop(people = listOf(assignee)),
                      "Done" to prop(checkbox = true),
                      "Link" to prop(url = "https://www.example.com"),
                      "Contact" to prop(email = "foo@example.com"),
                  ),
              children = pageChildren,
          )
      assertNotNull(newPage)

      // number type page property
      val numberPagePropertyId = database.properties["Velocity Points"]!!.id
      val numberProperty =
          client.retrievePagePropertyItem(pageId = newPage.id, propertyId = numberPagePropertyId)
      assertNotNull(numberProperty)
      // list type page property
      val listProperty = client.retrievePagePropertyItem(pageId = newPage.id, propertyId = "title")
      assertNotNull(listProperty)

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
      assertFalse(fetchedPage.archived!!)

      // check child_page block
      val block = client.retrieveBlock(newPage.id)
      assertNotNull(block)

      val archivedPage =
          client.updatePage(pageId = newPage.id, properties = emptyMap(), archived = true)
      assertTrue(archivedPage.archived!!)
    }
  }
}
