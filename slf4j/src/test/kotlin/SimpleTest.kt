import notion.api.v1.NotionClient
import notion.api.v1.http.OkHttp3Client
import notion.api.v1.logging.Slf4jLogger
import notion.api.v1.model.pages.Page
import notion.api.v1.model.pages.PageProperty
import notion.api.v1.request.pages.CreatePageRequest
import notion.api.v1.request.pages.UpdatePagePropertiesRequest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class SimpleTest {

    @Test
    fun pages() {
        NotionClient(token = System.getenv("NOTION_TOKEN")).use { client ->
            client.httpClient = OkHttp3Client()
            client.logger = Slf4jLogger()

            val databases = client.listDatabases()
            assertTrue { databases.results.isNotEmpty() }

            val database = databases.results[0]

            val newPage: Page = client.createPage(CreatePageRequest(
                parent = CreatePageRequest.Parent(
                    type = "database",
                    databaseId = database.id,
                ),
                properties = mapOf(
                    "Title" to PageProperty(
                        title = listOf(
                            PageProperty.RichText(
                                text = PageProperty.RichText.Text(content = "Fix a bug")
                            )
                        )
                    ),
                    "Description" to PageProperty(
                        richText = listOf(
                            PageProperty.RichText(
                                text = PageProperty.RichText.Text(
                                    content = "The bug is a minor one but one of our major customers have been affected by it for a while."
                                )
                            )
                        )
                    ),
                    "Due" to PageProperty(
                        date = PageProperty.Date(start = "2021-05-13", end = "2021-12-31")
                    ),
                    "Severity" to PageProperty(
                        select = database.properties["Severity"]?.select?.options?.find { it.name == "High" }
                    ),
                )
            ))
            assertNotNull(newPage)

            val patchResult = client.updatePageProperties(UpdatePagePropertiesRequest(
                pageId = newPage.id,
                properties = mapOf(
                    "Due" to PageProperty(
                        date = PageProperty.Date(start = "2021-12-31")
                    ),
                    "Severity" to PageProperty(
                        select = database.properties["Severity"]?.select?.options?.find { it.name == "Medium" }
                    ),
                )
            ))
            assertNotEquals(newPage.lastEditedTime, patchResult.lastEditedTime)

            val fetchedPage = client.retrievePage(newPage.id)
            assertEquals(patchResult.lastEditedTime, fetchedPage.lastEditedTime)
        }
    }
}