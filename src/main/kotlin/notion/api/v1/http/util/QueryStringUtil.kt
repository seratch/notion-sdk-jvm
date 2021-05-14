package notion.api.v1.http.util

import java.net.URLEncoder

object QueryStringUtil {

    fun urlEncode(value: String): String = URLEncoder.encode(value, "UTF-8")

}