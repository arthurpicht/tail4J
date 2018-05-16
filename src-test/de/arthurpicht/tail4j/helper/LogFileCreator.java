package de.arthurpicht.tail4j.helper;

import de.arthurpicht.tail4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Arthur Picht, Düsseldorf, 14.05.18.
 */
public class LogFileCreator implements Runnable {

    private File tempFile;

    public LogFileCreator(File tempFile) throws IOException {

        this.tempFile = tempFile;

    }


    @Override
    public void run() {

        try {

            this.writeTempFile(100);

            this.tempFile.delete();
            this.sleep(2000);

            this.writeTempFile(100);
//            this.tempFile.delete();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void writeTempFile(long sleepTime) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(this.tempFile);

        for (int i=0; i<= 100; i++) {

            printWriter.println(i + " ... some content ...");
            printWriter.flush();

            Logger.debug(i + " ... written.");

            this.sleep(sleepTime);
        }
        printWriter.close();

    }

    private void sleep(long timeInMSec) {

        try {
            Thread.sleep(timeInMSec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {


        try {
            Path tempDir = Files.createTempDirectory("tail4J");
            File tempDirFile = tempDir.toFile();
            File tempFile = new File(tempDirFile, "tail4Jtemp.log");

            System.out.println("tempFile: " + tempFile.getAbsolutePath());

            Thread thread = new Thread(new LogFileCreator(tempFile));
            thread.start();



        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
