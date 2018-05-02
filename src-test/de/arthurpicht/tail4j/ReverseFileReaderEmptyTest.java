package de.arthurpicht.tail4j;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Arthur Picht, Düsseldorf, 30.04.18.
 */
public class ReverseFileReaderEmptyTest {

    private String FILE_EMPTY = "test-resrc/empty-file.txt";

    @Test
    public void getLastLinesPointerUnix() {
        getLastLinesPointer(FILE_EMPTY);
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
        assertEquals(0, lines.size());
    }

}