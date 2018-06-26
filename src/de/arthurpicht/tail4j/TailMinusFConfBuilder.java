package de.arthurpicht.tail4j;

import java.io.File;

/**
 * Arthur Picht, Düsseldorf, 30.05.18.
 */
public class TailMinusFConfBuilder {

    private File file;
    private int lastLines = 10;
    private LogstatementProcessor logstatementProcessor = new StdOutLogstatementProcessor();
    private long sleepInterval = 1000;

    private boolean isWaitForFileToBecomeAccessible = true;
    private ExceptionListener exceptionListener = new DefaultExceptionListener();

    public TailMinusFConfBuilder setFile(File file) {
        this.file = file;
        return this;
    }

    public TailMinusFConfBuilder setLastLines(int lastLines) {
        this.lastLines = lastLines;
        return this;
    }

    public TailMinusFConfBuilder setLogstatementProcessor(LogstatementProcessor logstatementProcessor) {
        this.logstatementProcessor = logstatementProcessor;
        return this;
    }

    public TailMinusFConfBuilder setSleepInterval(long sleepInterval) {
        this.sleepInterval = sleepInterval;
        return this;
    }

    public TailMinusFConfBuilder setWaitForFileToBecomeAccessible(boolean isWaitForFileToBecomeAccessible) {
        this.isWaitForFileToBecomeAccessible = isWaitForFileToBecomeAccessible;
        return this;
    }

    public TailMinusFConfBuilder setExceptionListener(ExceptionListener exceptionListener) {
        this.exceptionListener = exceptionListener;
        return this;
    }

    public TailMinusFConf build() {
        if (this.file == null) throw new IllegalStateException("Parameter 'file' not specified.");
        return new TailMinusFConf(
                this.file,
                this.lastLines,
                this.logstatementProcessor,
                this.sleepInterval,
                this.isWaitForFileToBecomeAccessible,
                this.exceptionListener);
    }

}
