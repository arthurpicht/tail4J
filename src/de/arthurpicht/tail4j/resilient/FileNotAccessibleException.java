package de.arthurpicht.tail4j.resilient;

/**
 * Arthur Picht, Düsseldorf, 30.05.18.
 */
public class FileNotAccessibleException extends Exception {

    public FileNotAccessibleException(String message) {
        super(message);
    }
}
