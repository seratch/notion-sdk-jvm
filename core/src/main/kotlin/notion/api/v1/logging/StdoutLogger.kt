package notion.api.v1.logging

import java.time.ZoneId
import java.util.*

class StdoutLogger
@JvmOverloads
constructor(val name: String = StdoutLogger::class.java.canonicalName) : NotionLogger {

  override fun isDebugEnabled(): Boolean = true

  private fun now(): String =
      Date().toInstant().atZone(ZoneId.systemDefault()).toOffsetDateTime().toString()

  private fun buildMessage(level: String, message: String): String {
    return "${now()} $level $name - $message"
  }

  override fun debug(message: String, e: Throwable?) {
    println(buildMessage("DEBUG", message))
    e?.printStackTrace()
  }

  override fun info(message: String, e: Throwable?) {
    println(buildMessage("INFO", message))
    e?.printStackTrace()
  }

  override fun warn(message: String, e: Throwable?) {
    println(buildMessage("WARN", message))
    e?.printStackTrace()
  }

  override fun error(message: String, e: Throwable?) {
    println(buildMessage("ERROR", message))
    e?.printStackTrace()
  }
}
