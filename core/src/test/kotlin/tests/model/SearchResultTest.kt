package tests.model

import kotlin.test.assertNotNull
import notion.api.v1.json.GsonSerializer
import org.junit.Test

class SearchResultTest {
  @Test
  fun parse() {
    val serializer = GsonSerializer(true)
    val searchResults = serializer.toSearchResults(json)
    assertNotNull(searchResults)
  }

  private val json =
      """
{
  "object": "list",
  "results": [
    {
      "object": "page",
      "id": "4ca9549a-9ec4-4e1f-846d-6c37f4313687",
      "created_time": "2021-05-16T13:09:31.119Z",
      "last_edited_time": "2021-05-16T13:31:00.000Z",
      "parent": {
        "type": "database_id",
        "database_id": "dfd4ecb5-2d43-443e-97eb-4700db2c4f75"
      },
      "archived": false,
      "properties": {
        "Done": {
          "id": ":APL"
        },
        "Link": {
          "id": "Jf>F"
        },
        "Phone Number": {
          "id": "MyWZ"
        },
        "Velocity Points": {
          "id": "SIsY"
        },
        "Assignee": {
          "id": "ZKb?"
        },
        "Last Edited Time": {
          "id": "ZMd="
        },
        "Created Time": {
          "id": "ZSgO"
        },
        "Tags": {
          "id": "[r|\\"
        },
        "Description": {
          "id": "_;qC"
        },
        "Last Editor": {
          "id": "`Jbr"
        },
        "Contact": {
          "id": "fIt_"
        },
        "Attachments": {
          "id": "hhy?"
        },
        "Task List Relation": {
          "id": "i[;j"
        },
        "Due": {
          "id": "j=nT"
        },
        "Severity": {
          "id": "lZ`?"
        },
        "Creator": {
          "id": "m`c{"
        },
        "Column": {
          "id": "q=>K"
        },
        "Title": {
          "id": "title"
        }
      }
    }
  ],
  "next_cursor": null,
  "has_more": false
}
""".trimIndent()
}
