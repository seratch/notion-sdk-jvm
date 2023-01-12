package notion.api.v1.request.pages

import notion.api.v1.request.common.Pagination

data class RetrievePagePropertyItemRequest
@JvmOverloads
constructor(
    val pageId: String,
    val propertyId: String,
    override var startCursor: String? = null,
    override var pageSize: Int? = null,
) : Pagination {

  fun toQuery(): Map<String, List<String>> {
    val body = mutableMapOf<String, List<String>>()
    if (startCursor != null) {
      body["start_cursor"] = listOf(startCursor!!)
    }
    if (pageSize != null) {
      body["page_size"] = listOf(pageSize.toString())
    }
    return body
  }
}
