package de.arthurpicht.tail4j;

import java.io.File;
import java.io.IOException;

/**
 * Arthur Picht, Düsseldorf, 02.05.18.
 */
public class TailMinusF implements Runnable, TailMinusFInterface {

    private final TailMinusFConf tailMinusFConf;

    private Tail tail;
    private boolean run = true;

    private Thread tailMinusFThread = null;

    public TailMinusF(TailMinusFConf tailMinusFConf) {
        this.tailMinusFConf = tailMinusFConf;
    }


    @Override
    public void run() {

        Logger.info(TailMinusF.class.getSimpleName() + " started processing file " + getAbsolutePath());

        if (this.tailMinusFConf.isWaitForFileToBecomeAccessible()) {
            waitForFileToBecomeAccessible();
        }

        createTailInstance();

        while(this.run) {

            try {

                this.tail.visit();
                sleepInterruptable();

            } catch (IOException e) {

                this.tailMinusFConf.getExceptionListener().notify(e);
                this.run = false;

            }
        }

        Logger.info(TailMinusF.class.getSimpleName() + " stopped processing file " + getAbsolutePath());
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

    private void sleepInterruptable() {

        if (!this.run) return;

        try {
            Thread.sleep(this.tailMinusFConf.getSleepInterval());
        } catch (InterruptedException e) {
            this.run = false;
        }
    }

    private String getAbsolutePath() {
        return this.tailMinusFConf.getFile().getAbsolutePath();
    }

    private boolean isFileAccessible() {
        File file = this.tailMinusFConf.getFile();
        return (file.exists() && file.isFile() && file.canRead());
    }

    private void waitForFileToBecomeAccessible() {
        if (!this.run) return;

        boolean isErrorLogged = false;
        while (!isFileAccessible() && this.run) {

            if (!isErrorLogged) {
                Logger.info("Waiting for file to become accessible: [" + getAbsolutePath() + "].");
                isErrorLogged = true;
            }

            sleepInterruptable();
        }
        Logger.debug("File [" + getAbsolutePath() + "] is accessible.");

    }

    private void createTailInstance() {

        if (!this.run) return;

        try {

            this.tail = new Tail(
                    this.tailMinusFConf.getFile(),
                    this.tailMinusFConf.getLastLines(),
                    this.tailMinusFConf.getLogstatementProcessor()
            );

            Logger.debug("Instance of TailMinusF successfully created.");

        } catch (IOException e) {

            this.tailMinusFConf.getExceptionListener().notify(e);
            this.run = false;

        }
    }

}
