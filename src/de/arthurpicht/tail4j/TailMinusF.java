package de.arthurpicht.tail4j;

import java.io.File;
import java.io.IOException;

/**
 * Arthur Picht, Düsseldorf, 02.05.18.
 */
public class TailMinusF implements Runnable, TailMinusFInterface {

    private final Tail tail;
    private final ExceptionListener exceptionListener;

    private long sleepInterval = 1000;
    private boolean run = true;

    private Thread tailMinusFThread = null;

    public TailMinusF(File logFile) throws IOException {
        this.tail = new Tail(logFile);
        this.exceptionListener = new DefaultExceptionListener();
    }

    public TailMinusF(File logFile, int lastLines, LogstatementProcessor logstatementProcessor, long sleepInterval, ExceptionListener exceptionListener) throws IOException {
        this.tail = new Tail(logFile, lastLines, logstatementProcessor);
        this.sleepInterval = sleepInterval;
        this.exceptionListener = exceptionListener;
    }


    @Override
    public void run() {

        Logger.info(TailMinusF.class.getSimpleName() + " started processing file " + this.tail.getLogFile().getAbsolutePath());

        while(this.run) {

            try {
                this.tail.visit();
                sleepInterruptable(this.sleepInterval);

            } catch (IOException e) {

//                Logger.error("Error when processing [" + this.tail.getLogFile().getAbsolutePath() + "]: " + e.getMessage());
                this.exceptionListener.notify(e);

                sleepInterruptable(this.sleepInterval);
                // optional: introduce stopOnException Parameter
//                this.run = false;
            }
        }

        Logger.info(TailMinusF.class.getSimpleName() + " stopped processing file " + this.tail.getLogFile().getAbsolutePath());
    }

    /**
     * Starts <code>TailMinusF</code> as a thread.
     * @return
     * @throws IllegalStateException If thread is already running.
     */
    @Override
    public Thread start() {

        if (this.tailMinusFThread != null && this.tailMinusFThread.isAlive()) {
            throw new IllegalStateException("Specified TailMinusF thread is already running.");
        }

        this.run = true;
        this.tailMinusFThread = new Thread(this);
        this.tailMinusFThread.start();

        return this.tailMinusFThread;
    }

    @Override
    public boolean isRunning() {
        return this.tailMinusFThread != null && this.tailMinusFThread.isAlive();
    }

    @Override
    public void stop() {
        this.run = false;
    }

    public ExceptionListener getExceptionListener() {
        return exceptionListener;
    }

    public long getSleepInterval() {
        return sleepInterval;
    }

    private void sleepInterruptable(long sleepInterval) {

        if (!this.run) return;

        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            this.run = false;
        }
    }

}
