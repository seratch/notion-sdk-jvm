import notion.api.v1.NotionClient
import notion.api.v1.model.common.property.NewProperty
import notion.api.v1.model.common.property.RichText
import notion.api.v1.model.common.property.RichTextText
import notion.api.v1.request.NewPageRequest
import java.util.*

fun main() {
    val client = NotionClient(token = System.getenv("NOTION_TOKEN"))
    val databases = client.listDatabases()
    println(databases)
    val newPage = client.createPage(
        NewPageRequest(
            parent = NewPageRequest.Parent(
                type = "database_id",
                databaseId = databases.results[0].id
            ),
            properties = mapOf(
                "Name" to NewProperty(
                    id = UUID.randomUUID().toString(),
                    title = listOf(RichText(text = RichTextText(content = "Tuscan Kale")))
                )
            )
        )
    )
    println(newPage)
}