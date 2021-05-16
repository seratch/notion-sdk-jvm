import notion.api.v1.NotionClient
import notion.api.v1.http.Okhttp3Client
import notion.api.v1.logging.Slf4jNotionLogger

fun main() {
    NotionClient(token = System.getenv("NOTION_TOKEN")).use { client ->
        client.logger = Slf4jNotionLogger()
        client.httpClient = Okhttp3Client()

        val searchResults = client.search("Tuscan Kale")
        println(searchResults)
    }
}