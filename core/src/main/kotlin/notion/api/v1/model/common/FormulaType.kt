package notion.api.v1.model.common

import com.google.gson.annotations.SerializedName

enum class FormulaType @JvmOverloads constructor(val value: String) {
  @SerializedName("string") StringType("string"),
  @SerializedName("number") Number("string"),
  @SerializedName("boolean") Boolean("boolean"),
  @SerializedName("date") Date("date");

  override fun toString(): String = value
}
