package notion.api.v1.json.gson

import com.google.gson.*
import java.lang.reflect.Type
import notion.api.v1.model.blocks.*

class BlockParser(private val unknownPropertyDetection: Boolean = false) :
    JsonDeserializer<Block>, JsonSerializer<Block> {

  override fun deserialize(
      json: JsonElement,
      typeOfT: Type,
      context: JsonDeserializationContext
  ): Block? {
    if (json == null || json.asJsonObject.get("type") == null) {
      return null
    }
    when (json.asJsonObject.get("type").asString) {
      "paragraph" -> return context.deserialize(json, ParagraphBlock::class.java)
      "heading_1" -> return context.deserialize(json, HeadingOneBlock::class.java)
      "heading_2" -> return context.deserialize(json, HeadingTwoBlock::class.java)
      "heading_3" -> return context.deserialize(json, HeadingThreeBlock::class.java)
      "bulleted_list_item" -> return context.deserialize(json, BulletedListItemBlock::class.java)
      "numbered_list_item" -> return context.deserialize(json, NumberedListItemBlock::class.java)
      "link_to_page" -> return context.deserialize(json, LinkToPageBlock::class.java)
      "link_preview" -> return context.deserialize(json, LinkPreviewBlock::class.java)
      "quote" -> return context.deserialize(json, QuoteBlock::class.java)
      "bookmark" -> return context.deserialize(json, BookmarkBlock::class.java)
      "equation" -> return context.deserialize(json, EquationBlock::class.java)
      "table_of_contents" -> return context.deserialize(json, TableOfContentsBlock::class.java)
      "breadcrumb" -> return context.deserialize(json, BreadcrumbBlock::class.java)
      "column" -> return context.deserialize(json, ColumnBlock::class.java)
      "column_list" -> return context.deserialize(json, ColumnListBlock::class.java)
      "code" -> return context.deserialize(json, CodeBlock::class.java)
      "image" -> return context.deserialize(json, ImageBlock::class.java)
      "file" -> return context.deserialize(json, FileBlock::class.java)
      "pdf" -> return context.deserialize(json, PDFBlock::class.java)
      "embed" -> return context.deserialize(json, EmbedBlock::class.java)
      "callout" -> return context.deserialize(json, CalloutBlock::class.java)
      "video" -> return context.deserialize(json, VideoBlock::class.java)
      "divider" -> return context.deserialize(json, DividerBlock::class.java)
      "to_do" -> return context.deserialize(json, ToDoBlock::class.java)
      "toggle" -> return context.deserialize(json, ToggleBlock::class.java)
      "child_database" -> return context.deserialize(json, ChildDatabaseBlock::class.java)
      "child_page" -> return context.deserialize(json, ChildPageBlock::class.java)
      "synced_block" -> return context.deserialize(json, SyncedBlock::class.java)
      "template" -> return context.deserialize(json, TemplateBlock::class.java)
      "table" -> return context.deserialize(json, TableBlock::class.java)
      "table_row" -> return context.deserialize(json, TableRowBlock::class.java)
      "audio" -> return context.deserialize(json, AudioBlock::class.java)
      "unsupported" -> return context.deserialize(json, UnsupportedBlock::class.java)
    }
    if (unknownPropertyDetection) {
      throw IllegalArgumentException("Unsupported block detected: $json")
    } else {
      return null
    }
  }

  override fun serialize(
      src: Block,
      typeOfSrc: Type,
      context: JsonSerializationContext
  ): JsonElement? = context.serialize(src)
}
