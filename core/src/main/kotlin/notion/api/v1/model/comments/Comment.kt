package notion.api.v1.model.comments

import com.google.gson.annotations.SerializedName
import notion.api.v1.model.blocks.BlockParent
import notion.api.v1.model.common.ObjectType
import notion.api.v1.model.common.WithObjectType
import notion.api.v1.model.pages.PageProperty
import notion.api.v1.model.users.User

// This data class does not have setters as developers never manually modify this
data class Comment
@JvmOverloads
constructor(
    @SerializedName("object") override val objectType: ObjectType = ObjectType.Comment,
    val id: String,
    var parent: BlockParent? = null,
    val discussionId: String,
    val createdTime: String,
    val createdBy: User,
    val lastEditedTime: String,
    var richText: List<PageProperty.RichText>,
    val requestId: String? = null,
) : WithObjectType
