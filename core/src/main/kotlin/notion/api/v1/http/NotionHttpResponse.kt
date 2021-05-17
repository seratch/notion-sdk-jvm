package notion.api.v1.http

open class NotionHttpResponse(
    val status: Int,
    val body: String,
    val headers: Map<String, List<String>>,
)
