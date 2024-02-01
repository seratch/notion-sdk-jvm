package tests.model

import kotlin.test.assertNotNull
import notion.api.v1.json.GsonSerializer
import notion.api.v1.model.pages.Page
import org.junit.Test

class PagesTest {

  @Test
  fun createPage() {
    val parser = GsonSerializer(true)
    val page: Page = parser.toPage(createdPageResponse)
    assertNotNull(page)
  }

  private val createdPageResponse =
      """
{
  "parent": {
    "type": "database",
    "database_id": "dfd4ecb5-2d43-443e-97eb-4700db2c4f75"
  },
  "properties": {
    "Title": {
      "id": "cce3a0d4-36b2-436e-840b-08f15b0cfe1a",
      "title": [
        {
          "type": "text",
          "text": {
            "content": "Fix a bug"
          }
        }
      ]
    },
    "Description": {
      "id": "17f35fe5-1daa-4037-94a1-c6fd84690cf3",
      "rich_text": [
        {
          "type": "text",
          "text": {
            "content": "The bug is a minor one but one of our major customers have been affected by it for a while."
          }
        }
      ]
    },
    "Rollup Property": {
      "id": "da010d01-6406-43d1-ae03-4b4b6989bbd9",
      "type": "rollup",
      "rollup": {
        "type": "date",
        "function": "opaque_value",
        "date": {
          "start": "2023-03-09T00:00:00.000+00:00",
          "end": null,
          "time_zone": null
        }
      }
    },
    "Due": {
      "id": "da010d01-6406-43d1-ae03-4b4b6989bbd8",
      "date": {
        "start": "2021-05-13",
        "end": "2021-12-31"
      }
    },
    "Severity": {
      "id": "f5194155-7187-4283-b55b-3de32e9045cd",
      "select": {
        "id": "2b52a4e0-7d25-4cb0-b32b-0d7ad7c6428e",
        "name": "High",
        "color": "pink"
      }
    },
    "Velocity Points": {
      "id": "acb02754-f0be-4809-aa8f-1b2e4f6c16fc",
      "number": 3
    },
    "Assignee": {
      "id": "55c293b1-4680-4b8f-94d3-90895cc92de1",
      "people": [
        {
          "object": "user",
          "id": "94ab783a-9ee8-416d-b9b2-bdb1b0b2b1b3",
          "type": "person",
          "person": {
            "email": "foo@example.com"
          },
          "name": "Kazuhiro Sera",
          "avatar_url": "https://lh3.googleusercontent.com/a-/xxx=s100"
        }
      ]
    },
    "Done": {
      "id": "467a58f9-dccf-4cfb-a203-a995731da577",
      "checkbox": true
    },
    "Rollup Array": {
      "id": "467a58f9-dccf-4cfb-a203-a995731da577",
      "rollup": {
        "type": "array",
        "array": [
          {
            "type": "title",
            "title": [
              {
                "type": "text",
                "text": {
                  "content": "Gamba intermezzo",
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
                "plain_text": "Gamba intermezzo",
                "href": null
              }
            ]
          }
        ],
        "function": "show_original"
      }
    },
    "ID": {
      "id": "UQGz",
      "type": "unique_id",
      "unique_id": {"prefix": "TD", "number": 18}
    }
  }
}
""".trimIndent()
}
