package de.arthurpicht.tail4j.resilient;

import de.arthurpicht.tail4j.LogstatementProcessor;
import de.arthurpicht.tail4j.StdOutLogstatementProcessor;

import java.io.File;

/**
 * Arthur Picht, Düsseldorf, 30.05.18.
 */
public class TailMinusFResilientConfBuilder {

    private File file;
    private int lastLines = 10;
    private LogstatementProcessor logstatementProcessor = new StdOutLogstatementProcessor();
    private long sleepInterval = 1000;

    public TailMinusFResilientConfBuilder setFile(File file) {
        this.file = file;
        return this;
    }

    public TailMinusFResilientConfBuilder setLastLines(int lastLines) {
        this.lastLines = lastLines;
        return this;
    }

    public TailMinusFResilientConfBuilder setLogstatementProcessor(LogstatementProcessor logstatementProcessor) {
        this.logstatementProcessor = logstatementProcessor;
        return this;
    }

    public TailMinusFResilientConfBuilder setSleepInterval(long sleepInterval) {
        this.sleepInterval = sleepInterval;
        return this;
    }

    public TailMinusFResilientConf build() {
        if (this.file == null) throw new IllegalStateException("Parameter 'file' not specified.");
        return new TailMinusFResilientConf(this.file, this.lastLines, this.logstatementProcessor, this.sleepInterval);
    }

}
