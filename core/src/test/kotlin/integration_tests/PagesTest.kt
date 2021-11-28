package integration_tests

import java.time.ZonedDateTime
import kotlin.test.*
import notion.api.v1.NotionClient
import notion.api.v1.model.blocks.BlockElementUpdate
import notion.api.v1.model.blocks.BlockType
import notion.api.v1.model.common.ObjectType
import notion.api.v1.model.pages.Page
import notion.api.v1.model.pages.PageParent
import notion.api.v1.model.pages.PageProperty as prop
import notion.api.v1.model.pages.PageProperty
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
              .search("Test Database")
              .results
              .find {
                it.objectType == ObjectType.Database &&
                    it.asDatabase().properties.containsKey("Severity")
              }
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

  @Test
  fun testBlocks() {
    // Create a test page with the following title with to_do blocks
    // And then, invite your app to the page
    val title = "Test Page for SDK"
    NotionClient(token = System.getenv("NOTION_TOKEN")).use { client ->
      val pageId = client.search(title).results[0].id
      val page = client.retrievePage(pageId)
      assertEquals(pageId, page.id)
      val children = client.retrieveBlockChildren(pageId)
      assertNotNull(children)
      val todoBlock = children.results.find { it.type == BlockType.ToDo }!!
      val updatedText = "Updated (${ZonedDateTime.now()})"
      val updatedBlock =
          client.updateBlock(
              blockId = todoBlock.id!!,
              elements =
                  mapOf(
                      BlockType.ToDo to
                          BlockElementUpdate(
                              checked = true,
                              text =
                                  listOf(
                                      prop.RichText(
                                          text = prop.RichText.Text(content = updatedText))),
                          )))
      assertTrue { updatedBlock.asToDo().toDo.checked }
      assertEquals(updatedText, updatedBlock.asToDo().toDo.text?.get(0)?.text?.content)

      // Until the Notion platform provides a way to create a block via API,
      // we don't run the deletion in tests
      // client.deleteBlock(todoBlock.id!!)
    }
  }
}
