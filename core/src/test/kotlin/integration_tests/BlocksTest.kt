package integration_tests

import kotlin.test.assertNotNull
import notion.api.v1.NotionClient
import notion.api.v1.model.blocks.*
import notion.api.v1.model.pages.PageProperty
import org.junit.Test

class BlocksTest {

  @Test
  fun allOps() {
    NotionClient(token = System.getenv("NOTION_TOKEN")).use { client ->
      val blockId = System.getenv("NOTION_SDK_TEST_BLOCK_ID") ?: "50a30e81202b498cb01857b0aa0c515c"
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
