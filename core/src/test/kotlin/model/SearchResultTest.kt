package model

import notion.api.v1.json.GsonSerializer
import org.junit.Test
import kotlin.test.assertNotNull

class SearchResultTest {
    @Test
    fun parse() {
        val serializer = GsonSerializer()
        val searchResults = serializer.toSearchResults(json)
        assertNotNull(searchResults)
    }

    private val json = """
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
          "id": ":APL",
          "type": "checkbox",
          "checkbox": true
        },
        "Link": {
          "id": "Jf>F",
          "type": "url",
          "url": "https://www.example.com/foo"
        },
        "Phone Number": {
          "id": "MyWZ",
          "type": "phone_number",
          "phone_number": "000-1111-2222"
        },
        "Velocity Points": {
          "id": "SIsY",
          "type": "number",
          "number": 123.5
        },
        "Assignee": {
          "id": "ZKb?",
          "type": "people",
          "people": [
            {
              "object": "user",
              "id": "94ab783a-9ee8-416d-b9b2-bdb1b0b2b1b3",
              "name": "Alice",
              "avatar_url": "https://lh3.googleusercontent.com/a-/xxx",
              "type": "person",
              "person": {
                "email": "foo@gmail.com"
              }
            }
          ]
        },
        "Last Edited Time": {
          "id": "ZMd=",
          "type": "last_edited_time",
          "last_edited_time": "2021-05-16T13:31:00.000Z"
        },
        "Created Time": {
          "id": "ZSgO",
          "type": "created_time",
          "created_time": "2021-05-16T13:09:31.119Z"
        },
        "Tags": {
          "id": "[r|\\",
          "type": "multi_select",
          "multi_select": [
            {
              "id": "c0a647eb-8e16-48a1-948c-84c6c5253fba",
              "name": "Tag1",
              "color": "gray"
            },
            {
              "id": "ed307f3d-6b74-45ff-8283-a7a290952b26",
              "name": "Tag2",
              "color": "pink"
            }
          ]
        },
        "Description": {
          "id": "_;qC",
          "type": "rich_text",
          "rich_text": [
            {
              "type": "text",
              "text": {
                "content": "The bug is a minor one but one of our major customers have been affected by it for a while.",
                "link": null
              },
              "annotations": {
                "bold": false,
                "italic": false,
                "strikethrough": false,
                "underline": false,
                "code": false,
                "color": "default"
              },
              "plain_text": "The bug is a minor one but one of our major customers have been affected by it for a while.",
              "href": null
            }
          ]
        },
        "Last Editor": {
          "id": "`Jbr",
          "type": "last_edited_by",
          "last_edited_by": {
            "object": "user",
            "id": "94ab783a-9ee8-416d-b9b2-bdb1b0b2b1b3",
            "name": "Alice",
            "avatar_url": "https://lh3.googleusercontent.com/a-/xxx",
            "type": "person",
            "person": {
              "email": "foo@gmail.com"
            }
          }
        },
        "Contact": {
          "id": "fIt_",
          "type": "email",
          "email": "foo@gmail.com"
        },
        "Attachments": {
          "id": "hhy?",
          "type": "files",
          "files": [
            {
              "name": "https://www.example.com"
            },
            {
              "name": "Screen Shot 2021-05-07 at 18.22.30.png"
            }
          ]
        },
        "Task List Relation": {
          "id": "i[;j",
          "type": "relation",
          "relation": []
        },
        "Due": {
          "id": "j=nT",
          "type": "date",
          "date": {
            "start": "2021-12-31",
            "end": null
          }
        },
        "Severity": {
          "id": "lZ`?",
          "type": "select",
          "select": {
            "id": "fbe38876-81f3-4f64-a28e-34513787b1f6",
            "name": "Medium",
            "color": "gray"
          }
        },
        "Creator": {
          "id": "m`c{",
          "type": "created_by",
          "created_by": {
            "object": "user",
            "id": "2f22fbed-c837-4bb6-9e49-c0fff7dd5b8b",
            "name": "hello-notion",
            "avatar_url": null,
            "type": "bot",
            "bot": {}
          }
        },
        "Column": {
          "id": "q=>K",
          "type": "formula",
          "formula": {
            "type": "boolean",
            "boolean": true
          }
        },
        "Title": {
          "id": "title",
          "type": "title",
          "title": [
            {
              "type": "text",
              "text": {
                "content": "Great example data",
                "link": null
              },
              "annotations": {
                "bold": false,
                "italic": false,
                "strikethrough": false,
                "underline": false,
                "code": false,
                "color": "default"
              },
              "plain_text": "Great example data",
              "href": null
            }
          ]
        }
      }
    }
  ],
  "next_cursor": null,
  "has_more": false
}
""".trimIndent()
}