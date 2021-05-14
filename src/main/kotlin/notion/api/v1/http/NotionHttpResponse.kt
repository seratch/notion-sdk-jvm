package notion.api.v1.http

data class NotionHttpResponse(
    val status: Int,
    val body: String,
    val headers: Map<String, List<String>>,
)
