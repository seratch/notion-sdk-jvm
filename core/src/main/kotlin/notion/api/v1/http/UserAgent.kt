package notion.api.v1.http

object UserAgent {

    fun buildUserAgent(): String {
        val libraryVersion = "0.1" // TODO: embed from the build info
        val library = "notion-sdk-jvm/$libraryVersion"
        val repo = "https://github.com/seratch/notion-sdk-jvm"
        val jvm = "" + System.getProperty("java.vm.name") + "/" + System.getProperty("java.version") + ""
        val os = "" + System.getProperty("os.name") + "/" + System.getProperty("os.version") + ""
        return "$library; $repo; $jvm; $os"
    }
}