import notion.api.v1.NotionClient
import notion.api.v1.model.common.property.RichText
import notion.api.v1.model.common.property.RichTextText
import notion.api.v1.model.page.PagePropertyArg
import notion.api.v1.request.CreatePageRequest
import notion.api.v1.request.UpdatePagePropertiesRequest
import java.util.*

fun main() {
    NotionClient(token = System.getenv("NOTION_TOKEN")).use { client ->
        val databases = client.listDatabases()
        println(databases)
        val newPage = client.createPage(
            CreatePageRequest(
                parent = CreatePageRequest.Parent(
                    type = "database_id",
                    databaseId = databases.results[0].id
                ),
                properties = mapOf(
                    "Name" to PagePropertyArg(
                        id = UUID.randomUUID().toString(),
                        title = listOf(RichText(text = RichTextText(content = "Tuscan Kale")))
                    )
                )
            )
        )
        println(newPage)

        client.updatePageProperties(
            UpdatePagePropertiesRequest(
                pageId = newPage.id,
                properties = mapOf(
                    "Name" to PagePropertyArg(
                        id = UUID.randomUUID().toString(),
                        title = listOf(RichText(text = RichTextText(content = "Updated!")))
                    ),
                )
            )
        )
    }
}