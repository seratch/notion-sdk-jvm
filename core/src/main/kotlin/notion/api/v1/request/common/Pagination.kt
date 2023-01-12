package notion.api.v1.request.common

interface Pagination {
  var startCursor: String?
  var pageSize: Int?

  fun buildPaginationParams(): Map<String, List<String>> {
    val q = mutableMapOf<String, List<String>>()
    if (startCursor != null) {
      q["start_cursor"] = listOf(startCursor!!)
    }
    if (pageSize != null) {
      q["page_size"] = listOf(pageSize.toString())
    }
    return q
  }
}
