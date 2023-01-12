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
    // If the type adapter is a reflective type adapter, we want to modify the implementation
    // using
    // reflection. The
    // trick is to replace the Map object used to lookup the property name. Instead of returning
    // null if the
    // property is not found, we throw a Json exception to terminate the deserialization.
    val delegate = gson.getDelegateAdapter(this, type)

    // Check if the type adapter is a reflective, cause this solution only work for reflection.
    if (delegate is ReflectiveTypeAdapterFactory.Adapter<*, *>) {
      try {
        // Get reference to the existing boundFields.
        var adaptorClass: Class<*> = delegate.javaClass
        var boundFieldsField: Field? = null
        while (boundFieldsField == null && adaptorClass != Any::class.java) {
          try {
            boundFieldsField = adaptorClass.getDeclaredField("boundFields")
          } catch (_ignore: NoSuchFieldException) {
            // Since GSON v3.10, the internal class hierarchy has been changed
            // 1) com.google.gson.internal.bind.ReflectiveTypeAdapterFactory$FieldReflectionAdapter
            // 2) com.google.gson.internal.bind.ReflectiveTypeAdapterFactory$Adapter
            adaptorClass = adaptorClass.superclass
          }
        }
        if (boundFieldsField == null) {
          val message = "Failed to find bound fields inside GSON"
          throw IllegalStateException(message)
        }
        boundFieldsField.isAccessible = true
        var boundFields = boundFieldsField[delegate] as Map<*, *>
        val sb = StringBuilder()
        for (key in boundFields.keys) {
          sb.append("$key, ")
        }
        val boundFieldsStr = sb.append("...").toString()
        // Then replace it with our implementation throwing exception if the value is null.
        boundFields =
            object : LinkedHashMap<Any?, Any?>(boundFields) {
              override fun get(key: Any?): Any? {
                return super.get(key)
                    ?: throw JsonParseException(
                        "Unknown property detected: $key in $boundFieldsStr")
              }
            }
        // Finally, push our custom map back using reflection.
        boundFieldsField[delegate] = boundFields
      } catch (e: Exception) {
        // Should never happen if the implementation doesn't change.
        throw IllegalStateException(e)
      }
    }
    return delegate
  }
}
