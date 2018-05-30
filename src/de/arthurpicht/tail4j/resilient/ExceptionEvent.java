package de.arthurpicht.tail4j.resilient;

/**
 * Arthur Picht, Düsseldorf, 29.05.18.
 */
public class ExceptionEvent {

    private long firstTimestamp;
    private long lastTimestamp;
    private long nrOfOccurences;
    private Exception exception;

    public ExceptionEvent(Exception exception) {
        long ts = System.currentTimeMillis();
        this.firstTimestamp = ts;
        this.lastTimestamp = ts;
        this.exception = exception;
        this.nrOfOccurences = 1;
    }

    public long getFirstTimestamp() {
        return firstTimestamp;
    }

    public long getLastTimestamp() {
        return lastTimestamp;
    }

    public long getNrOfOccurences() {
        return nrOfOccurences;
    }

    public Exception getException() {
        return exception;
    }

    public void setLastTimestamp(long lastTimestamp) {
        this.lastTimestamp = lastTimestamp;
    }

    public void incNrOfOccurrences() {
        this.nrOfOccurences++;
    }

    @Override
    public String toString() {
        return "ExceptionEvent{" +
                "firstTimestamp=" + firstTimestamp +
                ", lastTimestamp=" + lastTimestamp +
                ", nrOfOccurences=" + nrOfOccurences +
                ", exception=" + exception +
                '}';
    }
}
