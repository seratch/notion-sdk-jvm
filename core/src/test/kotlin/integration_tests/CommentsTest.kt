package integration_tests

import java.util.*
import java.time.ZonedDateTime
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import notion.api.v1.NotionClient
import notion.api.v1.model.comments.*
import notion.api.v1.model.pages.PageProperty
import notion.api.v1.model.pages.PageParent
import org.junit.Test

class CommentsTest {

  @Test
  fun testHelloWorldComment() {
    NotionClient(token = System.getenv("NOTION_TOKEN")).use { client ->

      // Create a test page with the following comment "Hello World!"
      // And then, invite your app to the page

      val pageId =
          if (System.getenv("NOTION_TEST_PAGE_ID") != null) System.getenv("NOTION_TEST_PAGE_ID")
          else "98461275-5a90-4640-a273-3923b5a3dca5"
      val page = client.retrievePage(pageId)
      assertEquals(pageId, page.id)

      val comments = mutableListOf<Comment>()
      var children = client.retrieveComments(pageId, pageSize = 1000)
      assertNotNull(children)
      comments.addAll(children.results)
      var cursor = children.nextCursor
      while (cursor != null) {
        var children = client.retrieveComments(pageId, startCursor = cursor, pageSize = 1000)
        assertNotNull(children)
        comments.addAll(children.results)
        cursor = children.nextCursor
      }

      var found = false
      val itr = comments.listIterator()    // or, use `iterator()`
      while (itr.hasNext()) {
          var comment = itr.next()
          for(richText in comment.richText) {
              if(richText.plainText == "Hello World!") {
                  found = true
              }
          }
      }

      assertTrue(found, "Could not find \"Hello World!\" comment on test page")
    }
  }

  @Test
  fun testCreateComment() {
    NotionClient(token = System.getenv("NOTION_TOKEN")).use { client ->
      val pageId =
          if (System.getenv("NOTION_TEST_PAGE_ID") != null) System.getenv("NOTION_TEST_PAGE_ID")
          else "98461275-5a90-4640-a273-3923b5a3dca5"
      val page = client.retrievePage(pageId)
      assertEquals(pageId, page.id)

      val comments = mutableListOf<Comment>()

      val text = "Hello World! (created by Notion-SDK-JVM)" + UUID.randomUUID().toString()

      val newComment: Comment =
          client.createComment(
              parent = PageParent.page(page.id),
              richText = listOf(
                PageProperty.RichText(
                    text = PageProperty.RichText.Text(text))))
      assertNotNull(newComment)

      var children = client.retrieveComments(pageId, pageSize = 1000)
      assertNotNull(children)
      comments.addAll(children.results)
      var cursor = children.nextCursor
      while (cursor != null) {
        var children = client.retrieveComments(pageId, startCursor = cursor, pageSize = 1000)
        assertNotNull(children)
        comments.addAll(children.results)
        cursor = children.nextCursor
      }

      var found = false
      val itr = comments.listIterator()    // or, use `iterator()`
      while (itr.hasNext()) {
          var comment = itr.next()
          for(richText in comment.richText) {
              if(richText.plainText == text) {
                  found = true
              }
          }
      }

      assertTrue(found, "Could not find generated comment on test page")
    }
  }

}
