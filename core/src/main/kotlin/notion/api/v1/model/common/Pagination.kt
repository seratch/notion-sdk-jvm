package notion.api.v1.model.common

interface Pagination {
  val nextCursor: String?
  val hasMore: Boolean?
}
