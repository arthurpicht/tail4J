package de.arthurpicht.tail4j.modules.tail;

import de.arthurpicht.tail4j.Logger;
import de.arthurpicht.tail4j.LogstatementProcessor;

/**
 * Arthur Picht, Düsseldorf, 16.05.18.
 */
public class CounterTestLogstatementProcessor implements LogstatementProcessor {

    private int counter = 0;
    private boolean success = true;

    @Override
    public void process(String logStatement) {

        try {
            String valueString = logStatement.substring(0, logStatement.indexOf(" ... some"));
            int value = Integer.parseInt(valueString);

            if (value == this.counter) {
                Logger.debug("Found as expected: " + value);
                this.counter++;

            } else if (value == 0) {
                Logger.debug("Found 0, set back.");
                this.counter = 1;

            } else {
                Logger.debug("Expected: " + this.counter + "; Found: " + value);
                this.success = false;

            }

        } catch (RuntimeException e) {

            e.printStackTrace();

            this.success = false;
        }


    }

    public boolean hasSuccess() {
        return this.success;
    }

    public int getCounter() {
        return this.counter;
    }
}
