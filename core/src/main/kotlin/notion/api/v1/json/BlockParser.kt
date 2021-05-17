package notion.api.v1.json

import com.google.gson.*
import java.lang.reflect.Type
import notion.api.v1.model.blocks.*

class BlockParser : JsonDeserializer<Block>, JsonSerializer<Block> {

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Block? {
        when (json.asJsonObject.get("type").asString) {
            "paragraph" -> return context.deserialize(json, ParagraphBlock::class.java)
            "heading_1" -> return context.deserialize(json, HeadingOneBlock::class.java)
            "heading_2" -> return context.deserialize(json, HeadingTwoBlock::class.java)
            "heading_3" -> return context.deserialize(json, HeadingThreeBlock::class.java)
            "bulleted_list_item" ->
                return context.deserialize(json, BulletedListItemBlock::class.java)
            "numbered_list_item" ->
                return context.deserialize(json, NumberedListItemBlock::class.java)
            "to_do" -> return context.deserialize(json, ToDoBlock::class.java)
            "toggle" -> return context.deserialize(json, ToggleBlock::class.java)
            "child_page" -> return context.deserialize(json, ChildPageBlock::class.java)
            "unsupported" -> return context.deserialize(json, UnsupportedBlock::class.java)
        }
        return null
    }

    override fun serialize(
        src: Block,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement? = context.serialize(src)
}
