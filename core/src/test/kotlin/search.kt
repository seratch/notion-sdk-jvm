import notion.api.v1.NotionClient

fun main() {
    NotionClient(token = System.getenv("NOTION_TOKEN")).use { client ->
        val searchResults = client.search("Tuscan Kale")
        println(searchResults)
    }
}