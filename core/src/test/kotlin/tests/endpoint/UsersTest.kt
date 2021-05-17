package tests.endpoint

import kotlin.test.assertNotNull
import notion.api.v1.NotionClient
import org.junit.Test

class UsersTest {

    @Test
    fun fetchUsers() {
        NotionClient(token = System.getenv("NOTION_TOKEN")).use { client ->
            val users = client.listUsers()
            assertNotNull(users.results)

            val users2 = client.listUsers(pageSize = 1, startCursor = null)
            assertNotNull(users2.results)

            val user = client.retrieveUser(users2.results[0].id)
            assertNotNull(user)
        }
    }
}
