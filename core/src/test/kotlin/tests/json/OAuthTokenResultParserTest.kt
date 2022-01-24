package tests.json

import kotlin.test.assertNotNull
import notion.api.v1.json.GsonSerializer
import org.junit.Test

class OAuthTokenResultParserTest {
  private val json =
      """
{
  "access_token": "secret_XXXXX",
  "token_type": "bearer",
  "bot_id": "4cda3e4b-XXX-XXX-XXX-XXX",
  "workspace_name": "Kaz's Notion",
  "workspace_icon": "https://lh3.googleusercontent.com/a-/AOh14Gi6IRO6Ea30aCqeQFbgbR5HgU7ghyRsGAUjsUo3Ito=s100",
  "workspace_id": "841147a2-YYY-YYY-YYY-YYY",
  "owner": {
    "type": "user",
    "user": {
      "object": "user",
      "id": "94ab783a-ZZZ-ZZZ-ZZZ-ZZZ",
      "name": "Kazuhiro Sera",
      "avatar_url": "https://lh3.googleusercontent.com/a-/AOh14Gi6IRO6Ea30aCqeQFbgbR5HgU7ghyRsGAUjsUo3Ito=s100",
      "type": "person",
      "person": {
        "email": "seratch@example.com"
      }
    }
  }
}
""".trimIndent()

  @Test
  fun testParsing() {
    val serializer = GsonSerializer()
    val result = serializer.toOAuthTokenResult(json)
    assertNotNull(result)
  }
}
