package notion.api.v1.http

open class NotionHttpResponse
@JvmOverloads
constructor(
    val status: Int,
    val body: String,
    val headers: Map<String, List<String>>,
)
