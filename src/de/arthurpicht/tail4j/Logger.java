package de.arthurpicht.tail4j;

/**
 * Arthur Picht, Düsseldorf, 02.05.18.
 */
public class Logger {

    public static final String LOGGER_SYS_PROP = "de.arthurpicht.tail4j.logger";
    public static final String LOGGER_SLF4J = "slf4j";
    public static final String LOGGER_SDTOUT = "sdtout";

    private static final LoggerInterface loggerImpl;

    static {

        String loggerSysPropValue = System.getProperty(LOGGER_SYS_PROP, "false");

        if (loggerSysPropValue.toLowerCase() == LOGGER_SLF4J) {
            loggerImpl = new LoggerSLF4J();
            // debug("Logger of type " + LOGGER_SLF4J + " initialized.");
        } else if (loggerSysPropValue.toLowerCase() == LOGGER_SDTOUT) {
            loggerImpl = new LoggerSdtOut();
            // debug("Logger of type " + LOGGER_SDTOUT + " initialized.");
        } else {
            loggerImpl = null;
        }
    }

    public static void debug(String message) {
        if (!hasLoggerImpl()) return;
        loggerImpl.debug(message);
    }

    public static void info(String message) {
        if (!hasLoggerImpl()) return;
        loggerImpl.info(message);
    }

    public static void warn(String message) {
        if (!hasLoggerImpl()) return;
        loggerImpl.warn(message);
    }

    public static void error(String message) {
        if (!hasLoggerImpl()) return;
        loggerImpl.error(message);
    }

    private static boolean hasLoggerImpl() {
        return loggerImpl != null;
    }

}
