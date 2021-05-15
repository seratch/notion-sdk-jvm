import notion.api.v1.NotionClient
import notion.api.v1.model.database.TextQueryFilter

fun main() {
    val client = NotionClient(
        token = System.getenv("NOTION_TOKEN")
    )
    val users = client.listUsers()
    users.results.forEach { user ->
        println(client.findUser(user.id))
    }
    val databases = client.listDatabases()
    databases.results.forEach { database ->
        println(client.findDatabase(database.id))
    }
    println(client.findDatabase("invalid"))
    println(client.queryDatabase("invalid", filter = TextQueryFilter(
        property = "Name",
        equals = "Kazuhiro Sera",
    )))

    val searchResults = client.search("Getting Started")
    println(searchResults)
}