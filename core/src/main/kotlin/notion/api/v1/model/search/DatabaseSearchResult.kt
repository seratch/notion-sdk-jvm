package notion.api.v1.model.search

import com.google.gson.annotations.SerializedName
import notion.api.v1.model.common.property.RichText
import notion.api.v1.model.database.DatabaseProperty

data class DatabaseSearchResult(
    @SerializedName("object")
    override val objectType: String = "database",
    override val id: String,
    override val createdTime: String,
    override val lastEditedTime: String,
    val title: List<RichText>?,
    val properties: Map<String, DatabaseProperty>,
) : SearchResult