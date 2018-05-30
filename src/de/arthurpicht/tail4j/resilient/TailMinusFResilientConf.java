package de.arthurpicht.tail4j.resilient;

import de.arthurpicht.tail4j.LogstatementProcessor;

import java.io.File;

/**
 * Arthur Picht, Düsseldorf, 30.05.18.
 */
public final class TailMinusFResilientConf {

    final private File file;
    final private int lastLines;
    final private LogstatementProcessor logstatementProcessor;
    final private long sleepInterval;

    public TailMinusFResilientConf(File file, int lastLines, LogstatementProcessor logstatementProcessor, long sleepInterval) {
        this.file = file;
        this.lastLines = lastLines;
        this.logstatementProcessor = logstatementProcessor;
        this.sleepInterval = sleepInterval;
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
}
