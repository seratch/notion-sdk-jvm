import notion.api.v1.NotionClient
import notion.api.v1.model.database.query.filter.TextPropertyFilter

fun main() {
    NotionClient(token = System.getenv("NOTION_TOKEN")).use { client ->
        val users = client.listUsers()
        users.results.forEach { user ->
            println(client.retrieveUser(user.id))
        }
        val databases = client.listDatabases()
        databases.results.forEach { database ->
            println(client.retrieveDatabase(database.id))
            println(
                client.queryDatabase(
                    database.id, filter = TextPropertyFilter(
                        property = "Name",
                        equals = "Kazuhiro Sera",
                    )
                )
            )
        }
        val searchResults = client.search("Tuscan Kale")
        println(searchResults)
    }
}