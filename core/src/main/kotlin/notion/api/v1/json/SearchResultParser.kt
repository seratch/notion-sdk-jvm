package notion.api.v1.json

import com.google.gson.*
import notion.api.v1.model.search.DatabaseSearchResult
import notion.api.v1.model.search.PageSearchResult
import notion.api.v1.model.search.SearchResult
import java.lang.reflect.Type

class SearchResultParser : JsonDeserializer<SearchResult>, JsonSerializer<SearchResult> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): SearchResult? {
        when (json.asJsonObject.get("object").asString) {
            "page" -> return context.deserialize(json, PageSearchResult::class.java)
            "database" -> return context.deserialize(json, DatabaseSearchResult::class.java)
        }
        return null
    }

    override fun serialize(src: SearchResult, typeOfSrc: Type, context: JsonSerializationContext): JsonElement? =
        context.serialize(src)
}