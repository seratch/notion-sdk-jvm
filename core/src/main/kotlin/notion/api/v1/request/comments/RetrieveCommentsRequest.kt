package notion.api.v1.request.comments

import notion.api.v1.request.common.Pagination

// var is intentional here (for the easiness in other JVM languages)
data class RetrieveCommentsRequest
@JvmOverloads
constructor(
    val blockId: String,
    override var startCursor: String? = null,
    override var pageSize: Int? = null,
) : Pagination {

  fun toQuery(): Map<String, List<String>> {
    val body = mutableMapOf<String, List<String>>()
    body["block_id"] = listOf(blockId)
    if (startCursor != null) {
      body["start_cursor"] = listOf(startCursor!!)
    }
    if (pageSize != null) {
      body["page_size"] = listOf(pageSize.toString())
    }
    return body
  }
}
