package notion.api.v1.model.blocks

import com.google.gson.annotations.SerializedName
import notion.api.v1.model.common.ObjectType
import notion.api.v1.model.pages.PageProperty

interface Block : ObjectType {
    val id: String
    val type: String
    val createdTime: String
    val lastEditedTime: String
    val hasChildren: Boolean

    fun asParagraph(): ParagraphBlock =
        if (type == "paragraph") this as ParagraphBlock else throw IllegalStateException("")
}

data class AllBlock(
    @SerializedName("object")
    override val objectType: String = "block",
    override val id: String,
    override val type: String,
    override val createdTime: String,
    override val lastEditedTime: String,
    override val hasChildren: Boolean,
) : Block

data class ParagraphBlock(
    @SerializedName("object")
    override val objectType: String = "block",
    override val type: String = "paragraph",
    override val id: String,
    override val createdTime: String,
    override val lastEditedTime: String,
    override val hasChildren: Boolean,
    val paragraph: Element,
) : Block {
    data class Element(
        val text: List<PageProperty.RichText>,
        val children: List<Block>? = null
    )
}

data class HeadingOneBlock(
    @SerializedName("object")
    override val objectType: String = "block",
    override val type: String = "heading_1",
    override val id: String,
    override val createdTime: String,
    override val lastEditedTime: String,
    override val hasChildren: Boolean = false,
    @SerializedName("heading_1")
    val heading1: Element,
) : Block {
    data class Element(
        val text: List<PageProperty.RichText>,
    )
}

data class HeadingTwoBlock(
    @SerializedName("object")
    override val objectType: String = "block",
    override val type: String = "heading_2",
    override val id: String,
    override val createdTime: String,
    override val lastEditedTime: String,
    override val hasChildren: Boolean = false,
    @SerializedName("heading_2")
    val heading2: Element,
) : Block {
    data class Element(
        val text: List<PageProperty.RichText>,
    )
}

data class HeadingThreeBlock(
    @SerializedName("object")
    override val objectType: String = "block",
    override val type: String = "heading_3",
    override val id: String,
    override val createdTime: String,
    override val lastEditedTime: String,
    override val hasChildren: Boolean = false,
    @SerializedName("heading_3")
    val heading3: Element,
) : Block {
    data class Element(
        val text: List<PageProperty.RichText>,
    )
}

data class BulletedListItemBlock(
    @SerializedName("object")
    override val objectType: String = "block",
    override val type: String = "bulleted_list_item",
    override val id: String,
    override val createdTime: String,
    override val lastEditedTime: String,
    override val hasChildren: Boolean,
    val bulletedListItem: Element,
) : Block {
    data class Element(
        val text: List<PageProperty.RichText>,
        val children: List<Block>? = null
    )
}

data class NumberedListItemBlock(
    @SerializedName("object")
    override val objectType: String = "block",
    override val type: String = "numbered_list_item",
    override val id: String,
    override val createdTime: String,
    override val lastEditedTime: String,
    override val hasChildren: Boolean,
    val numberedListItem: Element,
) : Block {
    data class Element(
        val text: List<PageProperty.RichText>,
        val children: List<Block>? = null
    )
}

data class TodoBlock(
    @SerializedName("object")
    override val objectType: String = "block",
    override val type: String = "to_do",
    override val id: String,
    override val createdTime: String,
    override val lastEditedTime: String,
    override val hasChildren: Boolean,
    val toDo: Element,
) : Block {
    data class Element(
        val checked: Boolean,
        val children: List<Block>? = null
    )
}

data class ToggleBlock(
    @SerializedName("object")
    override val objectType: String = "block",
    override val type: String = "toggle",
    override val id: String,
    override val createdTime: String,
    override val lastEditedTime: String,
    override val hasChildren: Boolean,
    val toggle: Element,
) : Block {
    data class Element(
        val text: List<PageProperty.RichText>,
        val children: List<Block>? = null
    )
}

data class ChildPageBlock(
    @SerializedName("object")
    override val objectType: String = "block",
    override val type: String = "child_page",
    override val id: String,
    override val createdTime: String,
    override val lastEditedTime: String,
    override val hasChildren: Boolean,
    val childPage: Element,
) : Block {
    data class Element(
        val title: String
    )
}

data class UnsupportedBlock(
    @SerializedName("object")
    override val objectType: String = "block",
    override val type: String = "unsupported",
    override val id: String,
    override val createdTime: String,
    override val lastEditedTime: String,
    override val hasChildren: Boolean,
) : Block
