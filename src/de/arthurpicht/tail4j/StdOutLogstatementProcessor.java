package de.arthurpicht.tail4j;

/**
 * Arthur Picht, Düsseldorf, 02.05.18.
 */
public class StdOutLogstatementProcessor implements LogstatementProcessor {

    @Override
    public void process(String logStatement) {
        System.out.println(logStatement);
    }
}
