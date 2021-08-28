package notion.api.v1.model.users

import com.google.gson.annotations.SerializedName

enum class UserType @JvmOverloads constructor(val value: String) {
  @SerializedName("person") Person("person"),
  @SerializedName("bot") Bot("bot");

  override fun toString(): String = value
}
