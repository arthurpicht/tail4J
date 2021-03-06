package de.arthurpicht.tail4j.modules.reverseFileReader;

import de.arthurpicht.tail4j.ReverseFileReader;
import de.arthurpicht.tail4j.helper.TestHelper;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Arthur Picht, Düsseldorf, 30.04.18.
 */
public class ReverseFileReaderTest {

    private String FILE_UNIX = "test-resrc/file-unix.txt";
    private String FILE_WIN = "test-resrc/file-win.txt";
    private String FILE_OLDMAC = "test-resrc/file-oldmac.txt";

    @Test
    public void getLastLinesPointerUnix() {
        getLastLinesPointer(FILE_UNIX);
    }

    @Test
    public void getLastLinesPointerWin() {
        getLastLinesPointer(FILE_WIN);
    }

    @Test
    public void getLastLinesPointerOldMac() {
        getLastLinesPointer(FILE_OLDMAC);
    }

    private void getLastLinesPointer(String fileName) {

        ReverseFileReader reverseFileReader = new ReverseFileReader(new File(fileName));
        long pointer;

        try {
            pointer = reverseFileReader.getLastLinesPointer(5);
            List<String> lines = TestHelper.getLastLines(new File(fileName), pointer);
            assertLines(lines);

        } catch (IOException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    private void assertLines(List<String> lines) {
        assertEquals("Zeile 8", lines.get(0));
        assertEquals("Zeile 9", lines.get(1));
        assertEquals("Zeile 10", lines.get(2));
        assertEquals("Zeile 11", lines.get(3));
        assertEquals("Zeile 12", lines.get(4));
    }

}