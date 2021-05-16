import notion.api.v1.NotionClient
import notion.api.v1.http.JavaHttpClient

fun main() {
    NotionClient(token = System.getenv("NOTION_TOKEN"), httpClient = JavaHttpClient()).use { client ->
        val searchResults = client.search("Tuscan Kale")
        println(searchResults)
    }
}