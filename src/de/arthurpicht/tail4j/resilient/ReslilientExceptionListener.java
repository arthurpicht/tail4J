package de.arthurpicht.tail4j.resilient;

import de.arthurpicht.tail4j.ExceptionListener;

/**
 * Arthur Picht, Düsseldorf, 29.05.18.
 */
public class ReslilientExceptionListener implements ExceptionListener {

    private ExceptionEventRegistry exceptionEventRegistry;

    public ReslilientExceptionListener(ExceptionEventRegistry exceptionEventRegistry) {
        this.exceptionEventRegistry = exceptionEventRegistry;
    }

    @Override
    public void notify(Exception e) {
        this.exceptionEventRegistry.add(e);
    }

}
