package notion.api.v1.json.gson

import com.google.gson.*
import java.lang.reflect.Type
import notion.api.v1.model.common.Emoji
import notion.api.v1.model.common.File
import notion.api.v1.model.common.Icon

class IconParser(private val unknownPropertyDetection: Boolean = false) :
    JsonDeserializer<Icon>, JsonSerializer<Icon> {

  override fun deserialize(
      json: JsonElement,
      typeOfT: Type,
      context: JsonDeserializationContext
  ): Icon? {
    when (json.asJsonObject.get("type").asString) {
      "file" -> return context.deserialize(json, File::class.java)
      "external" -> return context.deserialize(json, File::class.java)
      "emoji" -> return context.deserialize(json, Emoji::class.java)
    }
    if (unknownPropertyDetection) {
      throw IllegalArgumentException("Unsupported icon object detected: $json")
    } else {
      return null
    }
  }

  override fun serialize(
      src: Icon,
      typeOfSrc: Type,
      context: JsonSerializationContext
  ): JsonElement? = context.serialize(src)
}
