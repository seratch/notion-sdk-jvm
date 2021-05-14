package notion.api.v1.logging

interface NotionLogger {
    fun debug(message: String) = debug(message, null)
    fun info(message: String) = info(message, null)
    fun warn(message: String) = warn(message, null)
    fun error(message: String) = error(message, null)

    fun debug(message: String, e: Throwable?)
    fun info(message: String, e: Throwable?)
    fun warn(message: String, e: Throwable?)
    fun error(message: String, e: Throwable?)
}