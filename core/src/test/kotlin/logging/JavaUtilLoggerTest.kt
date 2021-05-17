package logging

import notion.api.v1.logging.JavaUtilLogger
import org.junit.Test
import java.lang.RuntimeException
import kotlin.test.assertTrue

class JavaUtilLoggerTest {

    @Test
    fun test() {
        val logger = JavaUtilLogger()
        assertTrue(logger.isDebugEnabled())

        logger.debug("foo")
        logger.info("foo")
        logger.warn("foo")
        logger.error("foo")

        logger.debug("foo", RuntimeException("hi"))
        logger.info("foo", RuntimeException("hi"))
        logger.warn("foo", RuntimeException("hi"))
        logger.error("foo", RuntimeException("hi"))
    }
}