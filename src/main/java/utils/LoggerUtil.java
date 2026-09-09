package utils;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * LoggerUtil — thin JUL facade for TestFlow step logging.
 */
public final class LoggerUtil {

    private static final Logger LOGGER = Logger.getLogger("framework");

    private LoggerUtil() {
        // Utility class — do not instantiate.
    }

    public static void info(String message) {
        LOGGER.log(Level.INFO, message);
    }

    public static void step(String description) {
        LOGGER.log(Level.INFO, () -> "STEP: " + description);
    }

    public static void warn(String message) {
        LOGGER.log(Level.WARNING, message);
    }

    public static void error(String message, Throwable throwable) {
        LOGGER.log(Level.SEVERE, message, throwable);
    }
}
