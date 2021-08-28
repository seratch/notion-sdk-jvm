package notion.api.v1.model.common

// https://developers.notion.com/reference/emoji-object
data class Emoji(
    val type: String = "emoji",
    var emoji: String? = null,
) : Icon
