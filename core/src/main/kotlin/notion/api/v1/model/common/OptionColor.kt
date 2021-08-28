package notion.api.v1.model.common

import com.google.gson.annotations.SerializedName

enum class OptionColor @JvmOverloads constructor(val value: String) {
  @SerializedName("default") Default("default"),
  @SerializedName("gray") Gray("gray"),
  @SerializedName("brown") Brown("brown"),
  @SerializedName("orange") Orange("orange"),
  @SerializedName("yellow") Yellow("yellow"),
  @SerializedName("green") Green("green"),
  @SerializedName("blue") Blue("blue"),
  @SerializedName("purple") Purple("purple"),
  @SerializedName("pink") Pink("pink"),
  @SerializedName("red") Red("red");

  override fun toString(): String = value
}
