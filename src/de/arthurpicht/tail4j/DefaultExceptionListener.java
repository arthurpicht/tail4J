package de.arthurpicht.tail4j;

import java.io.File;

/**
 * Arthur Picht, Düsseldorf, 14.05.18.
 */
public class DefaultExceptionListener implements ExceptionListener {

    @Override
    public void notify(File file, Exception e) {
        // do intentionally nothing
    }
}
