package de.arthurpicht.tail4j;

/**
 * Arthur Picht, Düsseldorf, 02.05.18.
 */
public class LoggerSdtOut implements LoggerInterface {

    private static final String LOGGER_NAME = "Tail4J";

    public void debug(String message) {
        System.out.println("[" + LOGGER_NAME + "] [debug] " + message);
    }

    public void info(String message) {
        System.out.println("[" + LOGGER_NAME + "] [info ] " + message);
    }

    public void warn(String message) {
        System.out.println("[" + LOGGER_NAME + "] [warn ] " + message);
    }

    public void error(String message) {
        System.out.println("[" + LOGGER_NAME + "] [error] " + message);
    }

}
