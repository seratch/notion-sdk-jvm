package notion.api.v1.request.search

import notion.api.v1.request.common.Pagination

open class SearchRequest(
    val query: String,
    val filter: SearchFilter? = null,
    val sort: SearchSort? = null,
    override var startCursor: String? = null,
    override var pageSize: Int? = null,
) : Pagination {

  // For other JVM languages
  constructor(query: String) : this(query, null, null, null, null)
  constructor(query: String, filter: SearchFilter) : this(query, filter, null, null, null)
  constructor(
      query: String,
      filter: SearchFilter,
      sort: SearchSort
  ) : this(query, filter, sort, null, null)

  open class SearchFilter(
      var value: String? = null,
      var property: String? = null,
  )

  open class SearchSort(
      var direction: String? = null,
      var timestamp: String? = null,
  )
}
