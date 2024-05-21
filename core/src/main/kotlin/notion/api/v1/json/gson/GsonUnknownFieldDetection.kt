package notion.api.v1.json.gson

import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Field

class GsonUnknownFieldDetection : TypeAdapterFactory {

  override fun <T> create(gson: Gson, type: TypeToken<T>?): TypeAdapter<T>? {
    // If the type adapter is a reflective type adapter, we want to modify the implementation using
    // reflection. The trick is to replace the Map object used to look up the property name. Instead
    // of returning null if the property is not found,
    // we throw a Json exception to terminate the deserialization.
    val delegate = gson.getDelegateAdapter(this, type)

    // Check if the type adapter is a reflective, cause this solution only work for reflection.
    if (delegate is ReflectiveTypeAdapterFactory.Adapter<*, *>) {

      // This code is only compatible with GSON 2.11.0+
      try {
        var adaptorClass: Class<*> = delegate.javaClass
        var fieldsDataField: Field? = null
        while (fieldsDataField == null && adaptorClass != Any::class.java) {
          try {
            fieldsDataField = adaptorClass.getDeclaredField("fieldsData")
          } catch (_ignore: NoSuchFieldException) {
            // Since GSON v2.10, the internal class hierarchy has been changed
            // 1) com.google.gson.internal.bind.ReflectiveTypeAdapterFactory$FieldReflectionAdapter
            // 2) com.google.gson.internal.bind.ReflectiveTypeAdapterFactory$Adapter
            adaptorClass = adaptorClass.superclass
          }
        }
        if (fieldsDataField == null) {
          val message = "Failed to access fieldsData inside the GSON library"
          throw IllegalStateException(message)
        }
        fieldsDataField.isAccessible = true
        val fieldData = fieldsDataField[delegate]
        val deserializedFieldsField = fieldData.javaClass.getDeclaredField("deserializedFields")
        deserializedFieldsField.isAccessible = true
        var deserializedFields = deserializedFieldsField[fieldData] as Map<String, *>
        val sb = StringBuilder()
        for (key in deserializedFields.keys) {
          sb.append("$key, ")
        }
        val boundFieldsStr = sb.append("... (" + type!!.type.typeName + ")").toString()
        // Then replace it with our implementation throwing exception if the value is null.
        deserializedFields =
            object : LinkedHashMap<String, Any?>(deserializedFields) {
              override fun get(key: String): Any {
                return super.get(key)
                    ?: throw JsonParseException(
                        "Unknown property detected: $key in $boundFieldsStr")
              }
            }
        // Finally, push our custom map back using reflection.
        deserializedFieldsField[fieldData] = deserializedFields
      } catch (e: Exception) {
        // Should never happen if the implementation doesn't change.
        throw IllegalStateException(e)
      }
    }
    return delegate
  }
}
