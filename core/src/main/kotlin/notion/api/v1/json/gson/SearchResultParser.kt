package notion.api.v1.json.gson

import com.google.gson.*
import java.lang.reflect.Type
import notion.api.v1.model.search.DatabaseSearchResult
import notion.api.v1.model.search.PageSearchResult
import notion.api.v1.model.search.SearchResult

class SearchResultParser(private val unknownPropertyDetection: Boolean = false) :
    JsonDeserializer<SearchResult>, JsonSerializer<SearchResult> {

  override fun deserialize(
      json: JsonElement,
      typeOfT: Type,
      context: JsonDeserializationContext
  ): SearchResult? {
    when (json.asJsonObject.get("object").asString) {
      "page" -> return context.deserialize(json, PageSearchResult::class.java)
      "database" -> return context.deserialize(json, DatabaseSearchResult::class.java)
    }
    if (unknownPropertyDetection) {
      throw IllegalArgumentException("Unsupported search result object detected: $json")
    } else {
      return null
    }
  }

  override fun serialize(
      src: SearchResult,
      typeOfSrc: Type,
      context: JsonSerializationContext
  ): JsonElement? = context.serialize(src)
}
