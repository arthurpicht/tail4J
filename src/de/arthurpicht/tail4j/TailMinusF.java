package de.arthurpicht.tail4j;

import java.io.File;
import java.io.IOException;

/**
 * Arthur Picht, Düsseldorf, 02.05.18.
 */
public class TailMinusF implements Runnable {

    private Tail tail;
    private ExceptionListener exceptionListener = null;

    private long sleepInterval = 1000;
    private boolean run = true;

    public TailMinusF(File logFile) throws IOException {
        this.tail = new Tail(logFile);
    }

    public TailMinusF(File logFile, int lastLines, LogstatementProcessor logstatementProcessor, long sleepInterval) throws IOException {
        this.tail = new Tail(logFile, lastLines, logstatementProcessor);
        this.sleepInterval = sleepInterval;
    }

    public TailMinusF(File logFile, int lastLines, LogstatementProcessor logstatementProcessor, long sleepInterval, ExceptionListener exceptionListener) throws IOException {
        this.tail = new Tail(logFile, lastLines, logstatementProcessor);
        this.sleepInterval = sleepInterval;
        this.exceptionListener = exceptionListener;
    }


    @Override
    public void run() {

        Logger.info(TailMinusF.class.getName() + " started processing file " + this.tail.getLogFile().getAbsolutePath());

        while(this.run) {

            try {
                this.tail.visit();
                Thread.sleep(this.sleepInterval);

            } catch (IOException | InterruptedException e) {

                Logger.error(e.getMessage());

                if (this.exceptionListener != null) {
                    this.exceptionListener.notify(this.tail.getLogFile(), e);
                }

                this.run = false;
            }
        }

        Logger.info(TailMinusF.class.getName() + " stopped processing file " + this.tail.getLogFile().getAbsolutePath());
    }

    public void stop() {
        this.run = false;
    }
}
