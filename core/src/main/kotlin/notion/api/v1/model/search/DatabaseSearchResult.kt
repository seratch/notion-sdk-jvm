package notion.api.v1.model.search

import com.google.gson.annotations.SerializedName
import notion.api.v1.model.databases.DatabaseProperty

data class DatabaseSearchResult(
    @SerializedName("object")
    override val objectType: String = "database",
    override val id: String,
    override val createdTime: String,
    override val lastEditedTime: String,
    val title: List<DatabaseProperty.RichText>?,
    val properties: Map<String, DatabaseProperty>,
) : SearchResult