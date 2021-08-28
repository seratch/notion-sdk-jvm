package notion.api.v1.request.search

import notion.api.v1.request.common.Pagination

open class SearchRequest
@JvmOverloads
constructor(
    val query: String,
    val filter: SearchFilter? = null,
    val sort: SearchSort? = null,
    override var startCursor: String? = null,
    override var pageSize: Int? = null,
) : Pagination {

  open class SearchFilter
  @JvmOverloads
  constructor(
      var value: String? = null,
      var property: String? = null,
  )

  open class SearchSort
  @JvmOverloads
  constructor(
      var direction: String? = null,
      var timestamp: String? = null,
  )
}
