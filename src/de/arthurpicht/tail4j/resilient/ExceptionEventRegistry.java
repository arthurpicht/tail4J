package de.arthurpicht.tail4j.resilient;

import de.arthurpicht.tail4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Arthur Picht, Düsseldorf, 30.05.18.
 */
public class ExceptionEventRegistry {

    private List<ExceptionEvent> registry;

    public ExceptionEventRegistry() {
        this.registry = new ArrayList<>();
    }

    public synchronized void add(Exception exception) {
        if (this.registry.size() > 0) {
            ExceptionEvent lastExceptionEvent = this.registry.get(this.registry.size() - 1);
            if (exception.getMessage().equals(lastExceptionEvent.getException().getMessage())) {
                lastExceptionEvent.setLastTimestamp(System.currentTimeMillis());
                lastExceptionEvent.incNrOfOccurrences();
            } else {
                addNew(exception);
            }
        } else {
            addNew(exception);
        }
    }

    public List<ExceptionEvent> getAll() {
        return this.registry;
    }

    private void addNew(Exception exception) {
        Logger.error(exception.getMessage());
        ExceptionEvent exceptionEvent = new ExceptionEvent(exception);
        this.registry.add(exceptionEvent);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (ExceptionEvent exceptionEvent : this.registry) {
            stringBuilder.append(exceptionEvent.toString() + "\n");
        }
        return stringBuilder.toString();
    }

}
