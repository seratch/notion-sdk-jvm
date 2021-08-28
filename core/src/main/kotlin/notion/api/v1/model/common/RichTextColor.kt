package notion.api.v1.model.common

import com.google.gson.annotations.SerializedName

enum class RichTextColor @JvmOverloads constructor(val value: String) {
  @SerializedName("default") Default("default"),
  @SerializedName("gray") Gray("gray"),
  @SerializedName("brown") Brown("brown"),
  @SerializedName("orange") Orange("orange"),
  @SerializedName("yellow") Yellow("yellow"),
  @SerializedName("green") Green("green"),
  @SerializedName("blue") Blue("blue"),
  @SerializedName("purple") Purple("purple"),
  @SerializedName("pink") Pink("pink"),
  @SerializedName("red") Red("red"),
  @SerializedName("gray_background") GrayBackground("gray_background"),
  @SerializedName("brown_background") BrownBackground("brown_background"),
  @SerializedName("orange_background") OrangeBackground("orange_background"),
  @SerializedName("yellow_background") YellowBackground("yellow_background"),
  @SerializedName("green_background") GreenBackground("green_background"),
  @SerializedName("blue_background") BlueBackground("blue_background"),
  @SerializedName("purple_background") PurpleBackground("purple_background"),
  @SerializedName("pink_background") PinkBackground("pink_background"),
  @SerializedName("red_background") RedBackground("red_background");

  override fun toString(): String = value
}
