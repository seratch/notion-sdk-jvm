package notion.api.v1.endpoint

import notion.api.v1.http.NotionHttpClient
import notion.api.v1.json.NotionJsonSerializer
import notion.api.v1.logging.NotionLogger

interface BlocksSupport : EndpointsSupport {
    val httpClient: NotionHttpClient
    val jsonSerializer: NotionJsonSerializer
    val logger: NotionLogger
    val baseUrl: String

    // -----------------------------------------------
    // retrieveBlockChildren
    // -----------------------------------------------
    // TODO: retrieve block children

    // -----------------------------------------------
    // appendBlockChildren
    // -----------------------------------------------

    // TODO: append block children

}