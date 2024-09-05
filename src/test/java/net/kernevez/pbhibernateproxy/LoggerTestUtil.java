package net.kernevez.pbhibernateproxy;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.read.ListAppender;
import org.apache.commons.collections4.IteratorUtils;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class is directly manipulating LogBack that is the backend for SLF4J.
 * <p>
 * Our code is based on SLF4J, but its behavior depends on LogBack
 */
public class LoggerTestUtil {

    public static LogAsserter createTestAppender(Class<?> loggerFor, org.slf4j.event.Level level) {
        Logger logger = (Logger) LoggerFactory.getLogger(loggerFor);
        return new LogAsserter(logger, level, true);
    }

    public static class LogAsserter implements Closeable {
        private final Level originalLevel;
        private final List<Appender<ILoggingEvent>> originalAppenders;
        private final boolean originalAdditive;
        private final Logger logger;
        ListAppender<ILoggingEvent> appender = new ListAppender<>();

        public LogAsserter(Logger logger, org.slf4j.event.Level level, boolean additive) {
            this.logger = logger;
            this.originalLevel = logger.getLevel();
            this.originalAdditive = logger.isAdditive();
            this.originalAppenders = IteratorUtils.toList(logger.iteratorForAppenders());
            this.originalAppenders.forEach(logger::detachAppender);
            appender.start();
            logger.addAppender(appender);
            logger.setAdditive(additive);
            logger.setLevel(getLogbackLevel(level));
        }

        @Override
        public void close() {
            logger.setAdditive(originalAdditive);
            logger.setLevel(originalLevel);
            logger.detachAppender(appender);
            IteratorUtils.toList(logger.iteratorForAppenders()).forEach(logger::detachAppender);
            originalAppenders.forEach(logger::addAppender);
        }

        public void assertLogEquals(org.slf4j.event.Level level, String expectedLog) {
            assertLogs(List.of(new LogEvent(level, expectedLog)));
        }

        private void assertLogs(List<LogEvent> expectedLogs) {
            String expected = String.join("\n", getEvents().stream().map(Object::toString).toList());
            String actual = String.join("\n", expectedLogs.stream().map(Object::toString).toList());
            assertEquals(actual, expected);
        }

        public void assertNoLogs() {
            assertLogs(List.of());
        }

        public List<LogEvent> getEvents() {
            return appender.list.stream()
                                .map(e -> new LogEvent(e.getLevel(), e.getFormattedMessage()))
                                .toList();
        }
    }

    public record LogEvent(org.slf4j.event.Level level, String message) {
        public LogEvent(Level level, String message) {
            this(getSlf4jLevel(level), message);
        }
    }

    private static Level getLogbackLevel(org.slf4j.event.Level level) {
        return switch (level) {
            case org.slf4j.event.Level.TRACE -> Level.TRACE;
            case org.slf4j.event.Level.DEBUG -> Level.DEBUG;
            case org.slf4j.event.Level.INFO -> Level.INFO;
            case org.slf4j.event.Level.WARN -> Level.WARN;
            case org.slf4j.event.Level.ERROR -> Level.ERROR;
        };
    }

    private static org.slf4j.event.Level getSlf4jLevel(Level level) {
        if (level == Level.TRACE) return org.slf4j.event.Level.TRACE;
        if (level == Level.DEBUG) return org.slf4j.event.Level.DEBUG;
        if (level == Level.INFO) return org.slf4j.event.Level.INFO;
        if (level == Level.WARN) return org.slf4j.event.Level.WARN;
        if (level == Level.ERROR) return org.slf4j.event.Level.ERROR;
        throw new RuntimeException("Invalid level");
    }

}
