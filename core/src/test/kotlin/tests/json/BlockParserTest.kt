package tests.json

import kotlin.test.assertNotNull
import notion.api.v1.json.GsonSerializer
import org.junit.Test

class BlockParserTest {
  private val json =
      """
{
  "object": "list",
  "results": [
    {
      "object": "block",
      "id": "40e84e45-fd6d-4532-93f7-4d77fa2a9679",
      "created_time": "2021-06-22T11:03:00.000Z",
      "last_edited_time": "2021-11-28T05:48:00.000Z",
      "has_children": false,
      "archived": false,
      "type": "paragraph",
      "paragraph": {
        "text": [
          {
            "type": "text",
            "text": {
              "content": "test",
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
            "plain_text": "test",
            "href": null
          }
        ]
      }
    },
    {
      "object": "block",
      "id": "324e2796-21c9-4cea-a725-8040c16401cc",
      "created_time": "2021-11-28T05:48:00.000Z",
      "last_edited_time": "2021-11-29T02:55:00.000Z",
      "has_children": false,
      "archived": false,
      "type": "to_do",
      "to_do": {
        "text": [
          {
            "type": "text",
            "text": {
              "content": "Updated (2021-11-29T11:55:46.110539+09:00[Asia/Tokyo])",
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
            "plain_text": "Updated (2021-11-29T11:55:46.110539+09:00[Asia/Tokyo])",
            "href": null
          }
        ],
        "checked": true
      }
    },
    {
      "object": "block",
      "id": "b9b950af-ad65-4452-a867-1ad3a9e9d75f",
      "created_time": "2021-11-28T05:48:00.000Z",
      "last_edited_time": "2021-11-28T05:48:00.000Z",
      "has_children": false,
      "archived": false,
      "type": "bulleted_list_item",
      "bulleted_list_item": {
        "text": [
          {
            "type": "text",
            "text": {
              "content": "a",
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
            "plain_text": "a",
            "href": null
          }
        ]
      }
    },
    {
      "object": "block",
      "id": "6f8d6856-cfc5-4037-ad19-92e4fa52bb9b",
      "created_time": "2021-11-28T05:48:00.000Z",
      "last_edited_time": "2021-11-28T05:48:00.000Z",
      "has_children": false,
      "archived": false,
      "type": "divider",
      "divider": {}
    },
    {
      "object": "block",
      "id": "1528fded-1f8c-4523-a889-9a35849fd6da",
      "created_time": "2021-11-28T05:49:00.000Z",
      "last_edited_time": "2021-11-28T05:49:00.000Z",
      "has_children": false,
      "archived": false,
      "type": "video",
      "video": {
        "caption": [],
        "type": "external",
        "external": {
          "url": "https://www.youtube.com/watch?v=aA7si7AmPkY"
        }
      }
    },
    {
      "object": "block",
      "id": "dc9ae479-a884-4587-af2b-32a0143ff363",
      "created_time": "2021-11-28T05:49:00.000Z",
      "last_edited_time": "2021-11-28T05:49:00.000Z",
      "has_children": true,
      "archived": false,
      "type": "unsupported",
      "unsupported": {}
    },
    {
      "object": "block",
      "id": "be3fcf57-27c2-4b24-aeeb-a81dd0e0698f",
      "created_time": "2021-11-29T11:25:00.000Z",
      "last_edited_time": "2021-11-29T11:25:00.000Z",
      "has_children": false,
      "archived": false,
      "type": "callout",
      "callout": {
        "text": [
          {
            "type": "text",
            "text": {
              "content": "foo",
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
            "plain_text": "foo",
            "href": null
          }
        ],
        "icon": {
          "type": "emoji",
          "emoji": "💡"
        }
      }
    },
    {
      "object": "block",
      "id": "6735d1a3-a340-4df7-a6f3-94ba09c6b1fd",
      "created_time": "2021-11-29T11:26:00.000Z",
      "last_edited_time": "2021-11-29T11:26:00.000Z",
      "has_children": false,
      "archived": false,
      "type": "toggle",
      "toggle": {
        "text": [
          {
            "type": "text",
            "text": {
              "content": "toggle",
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
            "plain_text": "toggle",
            "href": null
          }
        ]
      }
    },
    {
      "object": "block",
      "id": "1605f847-f69a-4a2e-92b1-ddc73fffbbf3",
      "created_time": "2021-11-29T11:26:00.000Z",
      "last_edited_time": "2021-11-29T11:26:00.000Z",
      "has_children": false,
      "archived": false,
      "type": "quote",
      "quote": {
        "text": [
          {
            "type": "text",
            "text": {
              "content": "Here you are!",
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
            "plain_text": "Here you are!",
            "href": null
          }
        ]
      }
    },
    {
      "object": "block",
      "id": "4fe49862-aa34-4391-93bb-63eaebd7dc76",
      "created_time": "2021-11-29T11:26:00.000Z",
      "last_edited_time": "2021-11-29T11:26:00.000Z",
      "has_children": false,
      "archived": false,
      "type": "link_to_page",
      "link_to_page": {
        "type": "database_id",
        "database_id": "0acf9e16-055b-4dc1-adf3-f3d8bbdbeaae"
      }
    },
    {
      "object": "block",
      "id": "9fe7d56e-e9fd-4247-b44d-b8049f9dfc24",
      "created_time": "2021-11-29T11:26:00.000Z",
      "last_edited_time": "2021-11-29T11:26:00.000Z",
      "has_children": false,
      "archived": false,
      "type": "child_page",
      "child_page": {
        "title": "foo"
      }
    },
    {
      "object": "block",
      "id": "72d6f9dc-4a98-4c49-bc37-86a2fcb0e807",
      "created_time": "2021-11-29T11:27:00.000Z",
      "last_edited_time": "2021-11-29T11:27:00.000Z",
      "has_children": false,
      "archived": false,
      "type": "heading_1",
      "heading_1": {
        "text": [
          {
            "type": "text",
            "text": {
              "content": "h1",
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
            "plain_text": "h1",
            "href": null
          }
        ]
      }
    },
    {
      "object": "block",
      "id": "d3a64934-4d78-46eb-b9d8-6f0b8be86e26",
      "created_time": "2021-11-29T11:27:00.000Z",
      "last_edited_time": "2021-11-29T11:27:00.000Z",
      "has_children": false,
      "archived": false,
      "type": "heading_2",
      "heading_2": {
        "text": [
          {
            "type": "text",
            "text": {
              "content": "h2",
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
            "plain_text": "h2",
            "href": null
          }
        ]
      }
    },
    {
      "object": "block",
      "id": "3db12010-6905-4f17-8389-56ceb7eafce0",
      "created_time": "2021-11-29T11:27:00.000Z",
      "last_edited_time": "2021-11-29T11:27:00.000Z",
      "has_children": false,
      "archived": false,
      "type": "heading_3",
      "heading_3": {
        "text": [
          {
            "type": "text",
            "text": {
              "content": "h3",
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
            "plain_text": "h3",
            "href": null
          }
        ]
      }
    },
    {
      "object": "block",
      "id": "7c0abe8f-cc68-41e1-852f-99e41cee7c87",
      "created_time": "2021-11-29T11:27:00.000Z",
      "last_edited_time": "2021-11-29T11:27:00.000Z",
      "has_children": true,
      "archived": false,
      "type": "unsupported",
      "unsupported": {}
    },
    {
      "object": "block",
      "id": "e147cd23-e83b-40e7-bfed-2a9a17efc3f7",
      "created_time": "2021-11-29T11:27:00.000Z",
      "last_edited_time": "2021-11-29T11:27:00.000Z",
      "has_children": false,
      "archived": false,
      "type": "numbered_list_item",
      "numbered_list_item": {
        "text": [
          {
            "type": "text",
            "text": {
              "content": "1",
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
            "plain_text": "1",
            "href": null
          }
        ]
      }
    },
    {
      "type": "synced_block",
      "synced_block": {
        "synced_from": null,
        "children": [
          {
            "type": "callout",
            "callout": {
              "text": [
                {
                  "type": "text",
                  "text": {
                    "content": "Callout in synced block"
                  }
                }
              ]
            }
          }
        ]
      }
    },
    {
      "type": "synced_block",
      "synced_block": {
        "synced_from": {
          "block_id": "original_synced_block_id"
        }
      }
    },
    {
      "type": "template",
      "template": {
        "text": [
          {
            "type": "text",
            "text": {
              "content": "Create callout template"
            }
          }
        ],
        "children": [
          {
            "callout": {
              "text": [
                {
                  "type": "text",
                  "text": {
                    "content": "Placeholder callout text"
                  }
                }
              ]
            }
          }
        ]
      }
    },
    {
      "type": "code",
      "code": {
        "text": [{
          "type": "text",
          "text": {
            "content": "const a = 3"
          }
        }],
        "language": "javascript"
      }
    },
    {
      "type": "child_database",
      "child_database": {
        "title": "My database"
      }
    },
    {
      "type": "embed",
      "embed": {
        "url": "https://website.domain"
      }
    },
    {
      "type": "image",
      "image": {
        "type": "external",
        "external": {
          "url": "https://website.domain/images/image.png"
        }
      }
    },
    {
      "type": "file",
      "file": {
        "type": "external",
        "external": {
          "url": "https://website.domain/files/doc.txt"
        }
      }
    },
    {
      "type": "pdf",
      "pdf": {
        "type": "external",
        "external": {
          "url": "https://website.domain/files/doc.pdf"
        }
      }
    },
    {
      "type": "bookmark",
      "bookmark": {
        "url": "https://website.domain"
      }
    },
    {
      "type": "equation",
      "equation": {
        "expression": "e=mc^2"
      }
    },
    {
      "type": "table_of_contents",
      "table_of_contents": {}
    },
    {
      "type": "breadcrumb",
      "breadcrumb": {}
    },
    {
      "type": "column",
      "column": {}
    },
    {
      "type": "column_list",
      "column_list": {}
    },
    {
      "type": "link_preview",
      "link_preview": {
        "url": "https://github.com/example/example-repo/pull/1234"
      }
    }
  ],
  "next_cursor": null,
  "has_more": false
}
""".trimIndent()

  @Test
  fun testParsing() {
    val serializer = GsonSerializer()
    val blocks = serializer.toBlocks(json)
    assertNotNull(blocks)
  }
}
