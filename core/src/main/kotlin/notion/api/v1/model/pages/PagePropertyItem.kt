package notion.api.v1.model.pages

import com.google.gson.annotations.SerializedName
import notion.api.v1.model.common.*
import notion.api.v1.model.databases.DatabaseProperty
import notion.api.v1.model.users.User

data class PagePropertyItem
@JvmOverloads
constructor(
    @SerializedName("object")
    val objectType: ObjectType = ObjectType.PropertyItem, // "property_item" or "list"
    val type: PropertyType = PropertyType.PropertyItem, // can be "property_item"
    val results: List<PagePropertyItem>? = null, // for pagination
    val nextCursor: String? = null, // for pagination
    val hasMore: Boolean? = null, // for pagination
    val propertyItem: Element? = null,
    var id: String? = null,
    var title: PageProperty.RichText? = null,
    var richText: List<PageProperty.RichText>? = null,
    var select: DatabaseProperty.Select.Option? = null,
    var multiSelect: List<DatabaseProperty.MultiSelect.Option>? = null,
    var number: Number? = null,
    var date: PageProperty.Date? = null,
    var people: List<User>? = null,
    var checkbox: Boolean? = null,
    var url: String? = null,
    var phoneNumber: String? = null,
    var email: String? = null,
    var files: List<PageProperty.File>? = null,
    var relation: List<PageProperty.PageReference>? = null,
    var formula: PageProperty.Formula? = null,
    var rollup: PageProperty.Rollup? = null,
    val createdBy: User? = null,
    val lastEditedBy: User? = null,
    val createdTime: String? = null,
    val lastEditedTime: String? = null,
) {
  data class Element(
      val type: PropertyType, // can be "property_item"
      val nextUrl: String,
      var id: String? = null,
      var title: PageProperty.RichText? = null,
      var richText: List<PageProperty.RichText>? = null,
      var select: DatabaseProperty.Select.Option? = null,
      var multiSelect: List<DatabaseProperty.MultiSelect.Option>? = null,
      var number: Number? = null,
      var date: PageProperty.Date? = null,
      var people: List<User>? = null,
      var checkbox: Boolean? = null,
      var url: String? = null,
      var phoneNumber: String? = null,
      var email: String? = null,
      var files: List<PageProperty.File>? = null,
      var relation: List<PageProperty.PageReference>? = null,
      var formula: PageProperty.Formula? = null,
      var rollup: PageProperty.Rollup? = null,
      val createdBy: User? = null,
      val lastEditedBy: User? = null,
      val createdTime: String? = null,
      val lastEditedTime: String? = null
  )
}
