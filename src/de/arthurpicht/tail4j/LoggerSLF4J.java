package de.arthurpicht.tail4j;

import org.slf4j.LoggerFactory;

/**
 * Arthur Picht, Düsseldorf, 02.05.18.
 */
public class LoggerSLF4J implements LoggerInterface {

    private static final String LOGGER_NAME = "Tail4J";

    public void debug(String message) {
        org.slf4j.Logger logger = LoggerFactory.getLogger(LOGGER_NAME);
        logger.debug(message);
    }

    public void info(String message) {
        org.slf4j.Logger logger = LoggerFactory.getLogger(LOGGER_NAME);
        logger.info(message);
    }

    public void warn(String message) {
        org.slf4j.Logger logger = LoggerFactory.getLogger(LOGGER_NAME);
        logger.warn(message);
    }

    public void error(String message) {
        org.slf4j.Logger logger = LoggerFactory.getLogger(LOGGER_NAME);
        logger.error(message);
    }

}
