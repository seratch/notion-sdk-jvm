package notion.api.v1.http

import notion.api.Metadata

object UserAgent {

  @JvmStatic
  fun buildUserAgent(): String {
    val libraryVersion = Metadata.VERSION
    val library = "notion-sdk-jvm/$libraryVersion"
    val repo = "https://github.com/seratch/notion-sdk-jvm"
    val jvm =
        "" + System.getProperty("java.vm.name") + "/" + System.getProperty("java.version") + ""
    val os = "" + System.getProperty("os.name") + "/" + System.getProperty("os.version") + ""
    return "$library; $repo; $jvm; $os"
  }
}
