package notion.api.v1.json.gson

import com.google.gson.*
import java.lang.reflect.Type
import notion.api.v1.model.common.Cover
import notion.api.v1.model.common.File

class CoverParser : JsonDeserializer<Cover>, JsonSerializer<Cover> {

  override fun deserialize(
      json: JsonElement,
      typeOfT: Type,
      context: JsonDeserializationContext
  ): Cover? {
    when (json.asJsonObject.get("type").asString) {
      "file" -> return context.deserialize(json, File::class.java)
      "external" -> return context.deserialize(json, File::class.java)
    }
    return null
  }

  override fun serialize(
      src: Cover,
      typeOfSrc: Type,
      context: JsonSerializationContext
  ): JsonElement? = context.serialize(src)
}
