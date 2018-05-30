package de.arthurpicht.tail4j.resilient;

import de.arthurpicht.tail4j.Logger;
import de.arthurpicht.tail4j.TailMinusF;
import de.arthurpicht.tail4j.TailMinusFInterface;

import java.io.File;
import java.io.IOException;

/**
 * Arthur Picht, Düsseldorf, 29.05.18.
 */
public class TailMinusFResilient implements Runnable, TailMinusFInterface {

    private TailMinusFResilientConf tailMinusFResilientConf;

    private ExceptionEventRegistry exceptionEventRegistry;

    private TailMinusF tailMinusF;
    private boolean run = true;

    private Thread tailMinusFResilientThread;

    public TailMinusFResilient(TailMinusFResilientConf tailMinusFResilientConf) {

        this.tailMinusFResilientConf = tailMinusFResilientConf;
        this.exceptionEventRegistry = new ExceptionEventRegistry();
        this.tailMinusF = null;
    }

    @Override
    public void run() {

        while (this.run && this.tailMinusF == null) {

            waitForFileToBecomeAccessible();

            createTailMinusFInstance();
        }

        while (this.run) {

            if (!this.tailMinusF.isRunning()) {
                this.tailMinusF.start();
            } else {
                this.tailMinusF.stop();
            }

            watchRunningTailMinusF();

            waitForFileToBecomeAccessible();
        }
    }

    @Override
    public Thread start() {

        if (isRunning()) {
            throw new IllegalStateException("TailMinusFResilient thread is already running.");
        }

        this.run = true;
        this.tailMinusFResilientThread = new Thread(this);
        this.tailMinusFResilientThread.start();

        return this.tailMinusFResilientThread;

    }

    @Override
    public void stop() {
        this.run = false;
        this.tailMinusF.stop();
    }

    @Override
    public boolean isRunning() {
        return this.tailMinusFResilientThread != null && this.tailMinusFResilientThread.isAlive();
    }

    public ExceptionEventRegistry getExceptionEventRegistry() {
        return this.exceptionEventRegistry;
    }

    private void createTailMinusFInstance() {

        if (!this.run) return;

        try {
            ReslilientExceptionListener reslilientExceptionListener = new ReslilientExceptionListener(this.exceptionEventRegistry);
            this.tailMinusF = new TailMinusF(
                    this.tailMinusFResilientConf.getFile(),
                    this.tailMinusFResilientConf.getLastLines(),
                    this.tailMinusFResilientConf.getLogstatementProcessor(),
                    this.tailMinusFResilientConf.getSleepInterval(),
                    reslilientExceptionListener);
            Logger.debug("Instance of TailMinusF created.");
        } catch (IOException e) {
//            Logger.debug("Exception when creating TailMinusF-Instance for file [" + getAbsolutePath() + "]: " + e.getMessage());
            this.exceptionEventRegistry.add(e);

            Logger.debug("Sleep before next try.");
            sleepInterruptable(1000);
        }
    }

    private void watchRunningTailMinusF() {
        Logger.debug("Watching TailMinusF@" + getAbsolutePath() + " while running.");

        while (this.run) {
            if (!this.tailMinusF.isRunning()) return;
            sleepInterruptable(1000);
        }
    }

    private void sleepInterruptable(long sleepInterval) {
        if (!this.run) return;
        try {
            Thread.sleep(sleepInterval);
        } catch (InterruptedException e) {
            this.run = false;
        }
    }

    private boolean isFileAccessible() {
        File file = this.tailMinusFResilientConf.getFile();
        return (file.exists() && file.isFile() && file.canRead());
    }

    private void waitForFileToBecomeAccessible() {
        if (!this.run) return;

        Logger.debug("Waiting for file [" + getAbsolutePath() + "] to become accessible.");

        boolean isErrorLogged = false;
        while (!isFileAccessible() && this.run) {

            if (!isErrorLogged) {
//                Logger.error("File [" + getAbsolutePath() + "] is not accessible.");
                FileNotAccessibleException fileNotAccessibleException = new FileNotAccessibleException("File not accessible: " + getAbsolutePath());
                this.exceptionEventRegistry.add(fileNotAccessibleException);
                isErrorLogged = true;
            }

            sleepInterruptable(1000);
        }
        Logger.debug("File [" + getAbsolutePath() + "] is accessible.");

    }

    private String getAbsolutePath() {
        return this.tailMinusFResilientConf.getFile().getAbsolutePath();
    }

    public static void main(String[] args) {
        // TODO move to test class

        TailMinusFResilientConfBuilder tailMinusFResilientConfBuilder
                = new TailMinusFResilientConfBuilder()
                .setFile(new File("/home/apicht/work/logtest.log"));

        TailMinusFResilient tailMinusFResilient = new TailMinusFResilient(tailMinusFResilientConfBuilder.build());
        tailMinusFResilient.start();

        try {
            Thread.sleep(60 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        tailMinusFResilient.stop();

        System.out.println("Exception occurred: (" + tailMinusFResilient.getExceptionEventRegistry().getAll().size() + ")");
        System.out.println(tailMinusFResilient.getExceptionEventRegistry().toString());
    }
}
