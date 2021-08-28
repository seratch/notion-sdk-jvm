package notion.api.v1.logging

import java.util.logging.Level
import java.util.logging.Logger

class JavaUtilLogger @JvmOverloads constructor(val logger: Logger) : NotionLogger {

  constructor() : this(Logger.getLogger(JavaUtilLogger::class.java.canonicalName)) {
    if (this.logger.level == null) {
      this.logger.level = Level.ALL
    }
  }

  override fun isDebugEnabled(): Boolean = logger.isLoggable(Level.FINE)

  override fun debug(message: String, e: Throwable?) {
    logger.log(Level.FINE, message, e)
  }

  override fun info(message: String, e: Throwable?) {
    logger.log(Level.INFO, message, e)
  }

  override fun warn(message: String, e: Throwable?) {
    logger.log(Level.WARNING, message, e)
  }

  override fun error(message: String, e: Throwable?) {
    logger.log(Level.SEVERE, message, e)
  }
}
