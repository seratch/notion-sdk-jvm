package notion.api.v1.request.blocks

import notion.api.v1.request.common.Pagination

data class RetrieveBlockChildrenRequest(
    val blockId: String,
    override val startCursor: String? = null,
    override val pageSize: Int? = null,
) : Pagination {

    fun toQuery(): Map<String, String> {
        val body = mutableMapOf<String, String>()
        if (startCursor != null) {
            body["start_cursor"] = startCursor
        }
        if (pageSize != null) {
            body["page_size"] = pageSize.toString()
        }
        return body
    }
}
