package de.arthurpicht.tail4j;

import java.io.File;

/**
 * Arthur Picht, Düsseldorf, 14.05.18.
 */
public class DefaultExceptionListener implements ExceptionListener {

    private Exception exception = null;

    public DefaultExceptionListener() {}

    @Override
    public void notify(Exception e) {
        Logger.error(e.getMessage());
    }

    public boolean isExceptionOccurred() {
        return this.exception != null;
    }

    public Exception getLastException() {
        return this.exception;
    }
}
