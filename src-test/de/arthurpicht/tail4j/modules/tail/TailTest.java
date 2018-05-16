package de.arthurpicht.tail4j.modules.tail;

import de.arthurpicht.tail4j.Logger;
import de.arthurpicht.tail4j.Tail;
import de.arthurpicht.tail4j.helper.LogFileCreator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

/**
 * Arthur Picht, Düsseldorf, 16.05.18.
 */
public class TailTest {

    private static final String LOGFILE_NAME = "tail4Jtemp.log";

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void test1() {

        try {

            File tempFile = this.folder.newFile(LOGFILE_NAME);

            Logger.debug("tempFile: " + tempFile.getAbsolutePath());

            Thread logFileCreatorThread = this.startLogFileCreator(tempFile);

            CounterTestLogstatementProcessor counterTestLogstatementProcessor = new CounterTestLogstatementProcessor();
            Tail tail = new Tail(tempFile, 10, counterTestLogstatementProcessor);

            while(logFileCreatorThread.isAlive() && counterTestLogstatementProcessor.hasSuccess()) {
                tail.visit();
                Thread.sleep(500);
            }

            tail.visit();

            if (!counterTestLogstatementProcessor.hasSuccess()) {
                fail("Could not process expected log statements.");
            }

            assertEquals("Could not process all expected log statements. Some are missing.", 101, counterTestLogstatementProcessor.getCounter());

        } catch (IOException | InterruptedException e) {
            Logger.info(e.getMessage());
            fail(e.getMessage());
        }
    }

    private Thread startLogFileCreator(File tempFile) throws IOException {
        Thread thread = new Thread(new LogFileCreator(tempFile));
        thread.start();
        return thread;
    }

}
