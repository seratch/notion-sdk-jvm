package notion.api.v1.request.cmmon

interface Pagination {
    val startCursor: String?
    val pageSize: Int?

    fun buildPaginationParams(): Map<String, String> {
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