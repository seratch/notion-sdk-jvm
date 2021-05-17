package notion.api.v1.logging

import org.slf4j.LoggerFactory

class Slf4jLogger(name: String = Slf4jLogger::class.java.canonicalName) : NotionLogger {
    private val logger = LoggerFactory.getLogger(name)

    override fun isDebugEnabled(): Boolean = logger.isDebugEnabled

    override fun debug(message: String, e: Throwable?) {
        if (e != null) {
            logger.debug(message, e)
        } else {
            logger.debug(message)
        }
    }

    override fun info(message: String, e: Throwable?) {
        if (e != null) {
            logger.info(message, e)
        } else {
            logger.info(message)
        }
    }

    override fun warn(message: String, e: Throwable?) {
        if (e != null) {
            logger.warn(message, e)
        } else {
            logger.warn(message)
        }
    }

    override fun error(message: String, e: Throwable?) {
        if (e != null) {
            logger.error(message, e)
        } else {
            logger.error(message)
        }
    }
}