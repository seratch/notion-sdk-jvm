package integration_tests

import java.time.ZonedDateTime
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import notion.api.v1.NotionClient
import notion.api.v1.model.blocks.*
import notion.api.v1.model.pages.PageProperty
import org.junit.Test

class BlocksTest {

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
                                      PageProperty.RichText(
                                          text =
                                              PageProperty.RichText.Text(content = updatedText))),
                          )))
      assertTrue { updatedBlock.asToDo().toDo.checked }
      assertEquals(updatedText, updatedBlock.asToDo().toDo.text?.get(0)?.text?.content)

      // Until the Notion platform provides a way to create a block via API,
      // we don't run the deletion in tests
      // client.deleteBlock(todoBlock.id!!)
    }
  }

  @Test
  fun allOps() {
    NotionClient(token = System.getenv("NOTION_TOKEN")).use { client ->
      val blockId = System.getenv("NOTION_SDK_TEST_BLOCK_ID") ?: "50a30e81202b498cb01857b0aa0c515c"

      val block = client.retrieveBlock(blockId)
      assertNotNull(block)

      val blocks = client.retrieveBlockChildren(blockId)
      assertNotNull(blocks)

      val updateResult =
          client.appendBlockChildren(
              blockId = blockId,
              children =
                  listOf(
                      ParagraphBlock(
                          ParagraphBlock.Element(
                              text =
                                  listOf(
                                      PageProperty.RichText(
                                          text = PageProperty.RichText.Text("foo"))))),
                      HeadingOneBlock(
                          HeadingOneBlock.Element(
                              text =
                                  listOf(
                                      PageProperty.RichText(
                                          text = PageProperty.RichText.Text("heading 1"))))),
                      HeadingTwoBlock(
                          HeadingTwoBlock.Element(
                              text =
                                  listOf(
                                      PageProperty.RichText(
                                          text = PageProperty.RichText.Text("heading 2"))))),
                      HeadingThreeBlock(
                          HeadingThreeBlock.Element(
                              text =
                                  listOf(
                                      PageProperty.RichText(
                                          text = PageProperty.RichText.Text("heading 3"))))),
                      BulletedListItemBlock(
                          BulletedListItemBlock.Element(
                              text =
                                  listOf(
                                      PageProperty.RichText(
                                          text = PageProperty.RichText.Text("item1")),
                                  ))),
                      BulletedListItemBlock(
                          BulletedListItemBlock.Element(
                              text =
                                  listOf(
                                      PageProperty.RichText(
                                          text = PageProperty.RichText.Text("item2")),
                                  ))),
                      NumberedListItemBlock(
                          NumberedListItemBlock.Element(
                              text =
                                  listOf(
                                      PageProperty.RichText(
                                          text = PageProperty.RichText.Text("item1")),
                                  ))),
                      NumberedListItemBlock(
                          NumberedListItemBlock.Element(
                              text =
                                  listOf(
                                      PageProperty.RichText(
                                          text = PageProperty.RichText.Text("item2")),
                                  ))),
                      ToDoBlock(
                          ToDoBlock.Element(
                              text =
                                  listOf(
                                      PageProperty.RichText(
                                          text = PageProperty.RichText.Text("todo1")),
                                      PageProperty.RichText(
                                          text = PageProperty.RichText.Text("todo2")),
                                  ))),
                      ToggleBlock(
                          ToggleBlock.Element(
                              text =
                                  listOf(
                                      PageProperty.RichText(
                                          text = PageProperty.RichText.Text("toggle"))))),
                      // ChildPageBlock, UnsupportedBlock are not available here
                      ))
      assertNotNull(updateResult)
    }
  }
}
