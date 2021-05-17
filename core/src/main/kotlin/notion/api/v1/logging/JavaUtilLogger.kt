package notion.api.v1.logging

import java.util.logging.*

class JavaUtilLogger(val logger: Logger) : NotionLogger {

    constructor() : this(Logger.getLogger(JavaUtilLogger::class.java.canonicalName)) {
        if (this.logger.level == null) {
            this.logger.level = Level.ALL
        }
        if (this.logger.handlers == null || this.logger.handlers.isEmpty()) {
            val handler = ConsoleHandler()
            handler.level = this.logger.level
            handler.formatter = SimpleFormatter()
            this.logger.addHandler(handler)
        }
    }

    override fun isDebugEnabled(): Boolean = logger.level != null && logger.level.intValue() <= Level.FINE.intValue()

    override fun debug(message: String, e: Throwable?) {
        logger.fine(message)
    }

    override fun info(message: String, e: Throwable?) {
        logger.info(message)
    }

    override fun warn(message: String, e: Throwable?) {
        logger.warning(message)
    }

    override fun error(message: String, e: Throwable?) {
        logger.severe(message)
    }
}