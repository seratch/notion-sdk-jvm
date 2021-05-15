package notion.api.v1.json.impl

import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Field

class GsonUnknownFieldDetection : TypeAdapterFactory {

    override fun <T> create(gson: Gson, type: TypeToken<T>?): TypeAdapter<T>? {
        // If the type adapter is a reflective type adapter, we want to modify the implementation using reflection. The
        // trick is to replace the Map object used to lookup the property name. Instead of returning null if the
        // property is not found, we throw a Json exception to terminate the deserialization.
        val delegate = gson.getDelegateAdapter(this, type)

        // Check if the type adapter is a reflective, cause this solution only work for reflection.
        if (delegate is ReflectiveTypeAdapterFactory.Adapter<*>) {
            try {
                // Get reference to the existing boundFields.
                val f: Field = delegate.javaClass.getDeclaredField("boundFields")
                f.isAccessible = true
                var boundFields = f.get(delegate) as Map<*, *>
                val sb = StringBuilder()
                for (key in boundFields.keys) {
                    sb.append("$key, ")
                }
                val boundFieldsStr = sb.append("...").toString()

                // Then replace it with our implementation throwing exception if the value is null.
                boundFields = object : LinkedHashMap<Any?, Any?>(boundFields) {
                    override fun get(key: Any?): Any? {
                        return super.get(key)
                            ?: throw JsonParseException("Unknown property detected: $key in $boundFieldsStr")
                    }
                }
                // Finally, push our custom map back using reflection.
                f.set(delegate, boundFields)
            } catch (e: Exception) {
                // Should never happen if the implementation doesn't change.
                throw IllegalStateException(e)
            }
        }
        return delegate
    }
}