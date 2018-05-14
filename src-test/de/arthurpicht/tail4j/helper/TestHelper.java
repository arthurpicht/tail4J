package de.arthurpicht.tail4j.helper;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * Arthur Picht, Düsseldorf, 02.05.18.
 */
public class TestHelper {

    public static List<String> getLastLines(File file, long pointer) throws IOException {

        List<String> lines = new ArrayList<>();

        RandomAccessFile readFileAccess = new RandomAccessFile(file, "r");
        readFileAccess.seek(pointer);
        String line;
        while ((line = readFileAccess.readLine()) != null) {
            lines.add(line);
        }
        readFileAccess.close();

        return lines;
    }

}
