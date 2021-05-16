package notion.api.v1.logging

class StdoutNotionLogger : NotionLogger {
    override fun debug(message: String, e: Throwable?) {
        println(message)
        e?.printStackTrace()
    }

    override fun info(message: String, e: Throwable?) {
        println(message)
        e?.printStackTrace()
    }

    override fun warn(message: String, e: Throwable?) {
        println(message)
        e?.printStackTrace()
    }

    override fun error(message: String, e: Throwable?) {
        println(message)
        e?.printStackTrace()
    }
}