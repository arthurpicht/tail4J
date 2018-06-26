package de.arthurpicht.tail4j.modules.tailMinusF;

import de.arthurpicht.tail4j.DefaultExceptionListener;
import de.arthurpicht.tail4j.Logger;
import de.arthurpicht.tail4j.TailMinusF;
import de.arthurpicht.tail4j.TailMinusFConfBuilder;
import de.arthurpicht.tail4j.helper.LogFileCreator;
import de.arthurpicht.tail4j.modules.tail.CounterTestLogstatementProcessor;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static junit.framework.TestCase.*;

/**
 * Arthur Picht, Düsseldorf, 16.05.18.
 */
public class TailMinusFTest {

    private static final String LOGFILE_NAME = "tail4Jtemp.log";

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void test1() {

        try {

            File tempFile = this.folder.newFile(LOGFILE_NAME);

            Logger.debug("tempFile: " + tempFile.getAbsolutePath());

            Thread logFileCreatorThread = this.startLogFileCreator(tempFile);

            DefaultExceptionListener exceptionListener = new DefaultExceptionListener();
            CounterTestLogstatementProcessor counterTestLogstatementProcessor = new CounterTestLogstatementProcessor();

            TailMinusFConfBuilder tailMinusFConfBuilder = new TailMinusFConfBuilder()
                    .setFile(tempFile)
                    .setLastLines(10)
                    .setLogstatementProcessor(counterTestLogstatementProcessor)
                    .setExceptionListener(exceptionListener);

            TailMinusF tailMinusF = new TailMinusF(tailMinusFConfBuilder.build());

            assertFalse("TailMinusF not in excepected state (not running)", tailMinusF.isRunning());

            tailMinusF.start();
//            Thread.sleep(500);

            assertTrue("TailMinusF not in excepected state (running)", tailMinusF.isRunning());

            while(logFileCreatorThread.isAlive() && counterTestLogstatementProcessor.hasSuccess() && !exceptionListener.isExceptionOccured()) {
                Thread.sleep(500);
            }

            Thread.sleep(1000);

            if (exceptionListener.isExceptionOccured()) {
                fail("Exception occured when processing logfile: " + exceptionListener.getLastException().getMessage());
            }

            if (!counterTestLogstatementProcessor.hasSuccess()) {
                fail("Could not process expected log statements.");
            }

            assertEquals("Could not process all expected log statements. Some are missing.", 101, counterTestLogstatementProcessor.getCounter());

            assertTrue("TailMinusF not in excepected state (running)", tailMinusF.isRunning());
            tailMinusF.stop();
            Thread.sleep(1500);
            assertFalse("TailMinusF not in excepected state (not running)", tailMinusF.isRunning());

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
