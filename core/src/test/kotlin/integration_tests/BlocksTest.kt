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
      val pageId =
          if (System.getenv("NOTION_TEST_PAGE_ID") != null) System.getenv("NOTION_TEST_PAGE_ID")
          else "98461275-5a90-4640-a273-3923b5a3dca5"
      val page = client.retrievePage(pageId)
      assertEquals(pageId, page.id)
      val blocks = mutableListOf<Block>()
      var children = client.retrieveBlockChildren(pageId, pageSize = 1000)
      assertNotNull(children)
      blocks.addAll(children.results)
      var cursor = children.nextCursor
      while (cursor != null) {
        var children = client.retrieveBlockChildren(pageId, startCursor = cursor, pageSize = 1000)
        assertNotNull(children)
        blocks.addAll(children.results)
        cursor = children.nextCursor
      }

      val tableBlock = blocks.find { it.type == BlockType.Table }!!
      assertNotNull(tableBlock)
      // check table_row blocks
      val rows = client.retrieveBlockChildren(tableBlock?.id!!)
      assertNotNull(rows)

      val todoBlock = blocks.find { it.type == BlockType.ToDo }!!
      val updatedText = "Updated (${ZonedDateTime.now()})"
      val updatedBlock =
          client.updateBlock(
              blockId = todoBlock.id!!,
              elements =
                  mapOf(
                      BlockType.ToDo to
                          BlockElementUpdate(
                              checked = true,
                              richText =
                                  listOf(
                                      PageProperty.RichText(
                                          text =
                                              PageProperty.RichText.Text(content = updatedText))),
                          )))
      assertTrue { updatedBlock.asToDo().toDo.checked }
      assertEquals(updatedText, updatedBlock.asToDo().toDo.richText?.get(0)?.text?.content)

      val tableRowBlock = rows.results[0].asTableRow()

      // The table row must have three columns in a row
      val text = PageProperty.RichText(text = PageProperty.RichText.Text(content = "foo"))
      val updatedBlockWithCells =
          client.updateBlock(
              blockId = tableRowBlock.id!!,
              elements =
                  mapOf(
                      BlockType.TableRow to
                          BlockElementUpdate(
                              cells = listOf(listOf(text), listOf(text), listOf(text)),
                          )))
      assertTrue { updatedBlockWithCells.asTableRow().tableRow.cells.isNotEmpty() }

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
                              richText =
                                  listOf(
                                      PageProperty.RichText(
                                          text = PageProperty.RichText.Text("foo"))))),
                      HeadingOneBlock(
                          HeadingOneBlock.Element(
                              richText =
                                  listOf(
                                      PageProperty.RichText(
                                          text = PageProperty.RichText.Text("heading 1"))))),
                      HeadingTwoBlock(
                          HeadingTwoBlock.Element(
                              richText =
                                  listOf(
                                      PageProperty.RichText(
                                          text = PageProperty.RichText.Text("heading 2"))))),
                      HeadingThreeBlock(
                          HeadingThreeBlock.Element(
                              richText =
                                  listOf(
                                      PageProperty.RichText(
                                          text = PageProperty.RichText.Text("heading 3"))))),
                      BulletedListItemBlock(
                          BulletedListItemBlock.Element(
                              richText =
                                  listOf(
                                      PageProperty.RichText(
                                          text = PageProperty.RichText.Text("item1")),
                                  ))),
                      BulletedListItemBlock(
                          BulletedListItemBlock.Element(
                              richText =
                                  listOf(
                                      PageProperty.RichText(
                                          text = PageProperty.RichText.Text("item2")),
                                  ))),
                      NumberedListItemBlock(
                          NumberedListItemBlock.Element(
                              richText =
                                  listOf(
                                      PageProperty.RichText(
                                          text = PageProperty.RichText.Text("item1")),
                                  ))),
                      NumberedListItemBlock(
                          NumberedListItemBlock.Element(
                              richText =
                                  listOf(
                                      PageProperty.RichText(
                                          text = PageProperty.RichText.Text("item2")),
                                  ))),
                      ToDoBlock(
                          ToDoBlock.Element(
                              richText =
                                  listOf(
                                      PageProperty.RichText(
                                          text = PageProperty.RichText.Text("todo1")),
                                      PageProperty.RichText(
                                          text = PageProperty.RichText.Text("todo2")),
                                  ))),
                      ToggleBlock(
                          ToggleBlock.Element(
                              richText =
                                  listOf(
                                      PageProperty.RichText(
                                          text = PageProperty.RichText.Text("toggle"))))),
                      // ChildPageBlock, UnsupportedBlock are not available here
                      ))
      assertNotNull(updateResult)
    }
  }
}
