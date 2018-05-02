package de.arthurpicht.tail4j;

import org.slf4j.LoggerFactory;

/**
 * Arthur Picht, Düsseldorf, 02.05.18.
 */
public class Logger {

    private static final boolean LOGGER = true;
    private static final String LOGGER_NAME = "Tail4J";

    public static void debug(String message) {
        if (!LOGGER) return;

        org.slf4j.Logger logger = LoggerFactory.getLogger(LOGGER_NAME);
        logger.debug(message);
    }

    public static void info(String message) {
        if (!LOGGER) return;

        org.slf4j.Logger logger = LoggerFactory.getLogger(LOGGER_NAME);
        logger.info(message);
    }

    public static void warn(String message) {
        if (!LOGGER) return;

        org.slf4j.Logger logger = LoggerFactory.getLogger(LOGGER_NAME);
        logger.warn(message);
    }

    public static void error(String message) {
        if (!LOGGER) return;

        org.slf4j.Logger logger = LoggerFactory.getLogger(LOGGER_NAME);
        logger.error(message);
    }

}
