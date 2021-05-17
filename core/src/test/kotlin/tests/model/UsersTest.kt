package tests.model

import kotlin.test.assertNotNull
import notion.api.v1.json.GsonSerializer
import org.junit.Test

class UsersTest {

    @Test
    fun listUsers() {
        val parser = GsonSerializer(true)
        val users = parser.toUsers(listUsersResponse)
        assertNotNull(users)
    }

    private val listUsersResponse =
        """
{
  "object": "list",
  "results": [
    {
      "object": "user",
      "id": "94ab783a-9ee8-416d-b9b2-bdb1b0b2b1b3",
      "name": "Alice",
      "avatar_url": "https://lh3.googleusercontent.com/a-/xxx=s100",
      "type": "person",
      "person": {
        "email": "foo@example.com"
      }
    },
    {
      "object": "user",
      "id": "2f22fbed-c837-4bb6-9e49-c0fff7dd5b8b",
      "name": "sync-app",
      "avatar_url": null,
      "type": "bot",
      "bot": {}
    },
    {
      "object": "user",
      "id": "3f8cea1d-20eb-4d07-8b3b-9258b648940b",
      "name": "awesome-app",
      "avatar_url": null,
      "type": "bot",
      "bot": {}
    }
  ],
  "next_cursor": null,
  "has_more": false
}
""".trimIndent()
}
