package notion.api.v1.endpoint

import java.net.URLEncoder

interface EndpointsSupport {
    val token: String

    fun buildRequestHeaders(additionalOnes: Map<String, String>): Map<String, String> {
        return mapOf(
            "Authorization" to "Bearer $token",
            "Notion-Version" to "2021-05-13",
            "User-Agent" to "Notion-SDK-JVM by @seratch" // TODO: finalize
        ).plus(additionalOnes)
    }

    fun urlEncode(value: String): String = URLEncoder.encode(value, "UTF-8")

}