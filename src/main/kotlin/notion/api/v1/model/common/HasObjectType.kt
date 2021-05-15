package notion.api.v1.model.common

import kotlinx.serialization.SerialName

interface HasObjectType {
    @SerialName("object")
    val objectType: String
}