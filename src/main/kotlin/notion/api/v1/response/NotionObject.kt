package notion.api.v1.response

import kotlinx.serialization.SerialName

interface NotionObject {
    @SerialName("object")
    val objectType: String
}