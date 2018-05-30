package de.arthurpicht.tail4j;

/**
 * Arthur Picht, Düsseldorf, 30.05.18.
 */
public interface TailMinusFInterface {

    Thread start();

    void stop();

    boolean isRunning();
}
