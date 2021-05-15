package notion.api.v1

import java.net.URLEncoder

object Utilities {

    fun urlEncode(value: String): String = URLEncoder.encode(value, "UTF-8")

}