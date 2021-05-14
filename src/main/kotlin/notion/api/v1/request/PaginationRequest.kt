package notion.api.v1.request

interface PaginationRequest {
    val startCursor: String?
    val pageSize: Int?

    fun toQueryString(): Map<String, String> {
        val q = mutableMapOf<String, String>()
        if (startCursor != null) {
            q["start_cursor"] = startCursor!!
        }
        if (pageSize != null) {
            q["page_size"] = pageSize.toString()
        }
        return q
    }
}