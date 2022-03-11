package notion.api.v1.json.gson

import com.google.gson.*
import java.lang.reflect.Type
import notion.api.v1.model.databases.query.filter.CompoundFilter
import notion.api.v1.model.databases.query.filter.CompoundFilterElement
import notion.api.v1.model.databases.query.filter.PropertyFilter

class CompoundFilterElementParser(private val unknownPropertyDetection: Boolean = false) :
    JsonDeserializer<CompoundFilterElement>, JsonSerializer<CompoundFilterElement> {

  override fun deserialize(
      json: JsonElement,
      typeOfT: Type,
      context: JsonDeserializationContext
  ): CompoundFilterElement? {
    if (json == null) {
      return null
    }
    if (json.asJsonObject.get("or") != null || json.asJsonObject.get("and") != null) {
      return context.deserialize(json, CompoundFilter::class.java)
    }
    if (json.asJsonObject.get("property") != null) {
      return context.deserialize(json, PropertyFilter::class.java)
    }
    if (unknownPropertyDetection) {
      throw IllegalArgumentException("Unsupported compound filter item detected: $json")
    } else {
      return null
    }
  }

  override fun serialize(
      src: CompoundFilterElement,
      typeOfSrc: Type,
      context: JsonSerializationContext
  ): JsonElement? {
    if (src is CompoundFilter) {
      return context.serialize(src, CompoundFilter::class.java)
    }
    if (src is PropertyFilter) {
      return context.serialize(src, PropertyFilter::class.java)
    }
    return null
  }
}
