import notion.api.v1.NotionClient

fun main() {
    val client = NotionClient(
        token = System.getenv("NOTION_TOKEN")
    )
    val searchResults = client.search("Tuscan Kale")
    println(searchResults)
}