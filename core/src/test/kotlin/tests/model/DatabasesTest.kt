package tests.model

import kotlin.test.assertNotNull
import notion.api.v1.json.GsonSerializer
import notion.api.v1.model.databases.Databases
import org.junit.Test

class DatabasesTest {

  @Test
  fun listDatabases() {
    val parser = GsonSerializer(true)
    val databases: Databases = parser.toDatabases(listDatabasesResponse)
    assertNotNull(databases)
  }

  private val listDatabasesResponse =
      """
{
  "object": "list",
  "results": [
    {
      "object": "database",
      "id": "dfd4ecb5-2d43-443e-97eb-4700db2c4f75",
      "created_time": "2021-05-13T08:05:56.626Z",
      "last_edited_time": "2021-05-16T14:21:00.000Z",
      "title": [
        {
          "type": "text",
          "text": {
            "content": "Test Database",
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
          "plain_text": "Test Database",
          "href": null
        }
      ],
      "properties": {
        "Done": {
          "id": ":APL",
          "type": "checkbox",
          "checkbox": {}
        },
        "Column 1": {
          "id": "=Vlj",
          "type": "rich_text",
          "rich_text": {}
        },
        "Link": {
          "id": "Jf>F",
          "type": "url",
          "url": {}
        },
        "Test Formula": {
          "id": "K|aG",
          "type": "formula",
          "formula": {
            "expression": "prop(\"Creator\")"
          }
        },
        "Phone Number": {
          "id": "MyWZ",
          "type": "phone_number",
          "phone_number": {}
        },
        "Velocity Points": {
          "id": "SIsY",
          "type": "number",
          "number": {
            "format": "number"
          }
        },
        "Assignee": {
          "id": "ZKb?",
          "type": "people",
          "people": {}
        },
        "Last Edited Time": {
          "id": "ZMd=",
          "type": "last_edited_time",
          "last_edited_time": {}
        },
        "Created Time": {
          "id": "ZSgO",
          "type": "created_time",
          "created_time": {}
        },
        "Tags": {
          "id": "[r|\\",
          "type": "multi_select",
          "multi_select": {
            "options": [
              {
                "id": "c0a647eb-8e16-48a1-948c-84c6c5253fba",
                "name": "Tag1",
                "color": "gray"
              },
              {
                "id": "ed307f3d-6b74-45ff-8283-a7a290952b26",
                "name": "Tag2",
                "color": "pink"
              },
              {
                "id": "ff391f31-95b8-4edd-9205-f024c11e4f5c",
                "name": "Tag3",
                "color": "yellow"
              }
            ]
          }
        },
        "Description": {
          "id": "_;qC",
          "type": "rich_text",
          "rich_text": {}
        },
        "Last Editor": {
          "id": "`Jbr",
          "type": "last_edited_by",
          "last_edited_by": {}
        },
        "Contact": {
          "id": "fIt_",
          "type": "email",
          "email": {}
        },
        "Attachments": {
          "id": "hhy?",
          "type": "files",
          "files": {}
        },
        "Due": {
          "id": "j=nT",
          "type": "date",
          "date": {}
        },
        "Severity": {
          "id": "lZ`?",
          "type": "select",
          "select": {
            "options": [
              {
                "id": "2b52a4e0-7d25-4cb0-b32b-0d7ad7c6428e",
                "name": "High",
                "color": "pink"
              },
              {
                "id": "fbe38876-81f3-4f64-a28e-34513787b1f6",
                "name": "Medium",
                "color": "gray"
              },
              {
                "id": "324bef01-1dcf-422e-ab8b-dc8f8b5d32d4",
                "name": "Low",
                "color": "blue"
              }
            ]
          }
        },
        "Something": {
          "id": "lj:?",
          "type": "rich_text",
          "rich_text": {}
        },
        "Creator": {
          "id": "m`c{",
          "type": "created_by",
          "created_by": {}
        },
        "Column": {
          "id": "q=>K",
          "type": "formula",
          "formula": {
            "expression": "prop(\"Assignee\") == \"Kaz\""
          }
        },
        "Title": {
          "id": "title",
          "type": "title",
          "title": {}
        },
        "ID": {
          "id": "UQGz",
          "type": "unique_id",
          "unique_id": {"prefix": "TD"}
        }
      }
    }
  ],
  "next_cursor": null,
  "has_more": false
}
""".trimIndent()
}
