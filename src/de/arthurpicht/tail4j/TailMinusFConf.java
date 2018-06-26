package de.arthurpicht.tail4j;

import java.io.File;

/**
 * Arthur Picht, Düsseldorf, 30.05.18.
 */
public final class TailMinusFConf {

    final private File file;
    final private int lastLines;
    final private LogstatementProcessor logstatementProcessor;
    final private long sleepInterval;

    final private boolean isWaitForFileToBecomeAccessible;
    final private ExceptionListener exceptionListener;

    public TailMinusFConf(
            File file,
            int lastLines,
            LogstatementProcessor logstatementProcessor,
            long sleepInterval,
            boolean isWaitForFileToBecomeAccessible,
            ExceptionListener exceptionListener) {

        this.file = file;
        this.lastLines = lastLines;
        this.logstatementProcessor = logstatementProcessor;
        this.sleepInterval = sleepInterval;

        this.isWaitForFileToBecomeAccessible = isWaitForFileToBecomeAccessible;
        this.exceptionListener = exceptionListener;
    }

    public File getFile() {
        return file;
    }

    public int getLastLines() {
        return lastLines;
    }

    public LogstatementProcessor getLogstatementProcessor() {
        return logstatementProcessor;
    }

    public long getSleepInterval() {
        return sleepInterval;
    }

    public boolean isWaitForFileToBecomeAccessible() {
        return isWaitForFileToBecomeAccessible;
    }

   public ExceptionListener getExceptionListener() {
        return this.exceptionListener;
    }
}
