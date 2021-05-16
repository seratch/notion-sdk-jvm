import notion.api.v1.NotionClient
import notion.api.v1.http.Okhttp3Client

fun main() {
    NotionClient(token = System.getenv("NOTION_TOKEN"), httpClient = Okhttp3Client()).use { client ->
        val searchResults = client.search("Tuscan Kale")
        println(searchResults)
    }
}