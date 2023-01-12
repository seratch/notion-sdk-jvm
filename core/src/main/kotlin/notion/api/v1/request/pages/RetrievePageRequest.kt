package notion.api.v1.request.pages

data class RetrievePageRequest
@JvmOverloads
constructor(
    val pageId: String,
    val filterProperties: List<String>? = null,
) {

  fun toQuery(): Map<String, List<String>> {
    val body = mutableMapOf<String, List<String>>()
    if (filterProperties != null) {
      body["filter_properties"] = filterProperties
    }
    return body
  }
}
