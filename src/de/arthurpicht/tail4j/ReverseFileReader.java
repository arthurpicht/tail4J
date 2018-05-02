package de.arthurpicht.tail4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Arthur Picht, 30.04.18.
 */
public class ReverseFileReader {

    private Logger logger = LoggerFactory.getLogger(ReverseFileReader.class);

    private File file;

    public ReverseFileReader(File file) {
        this.file = file;
    }

    /**
     * Gibt einen Byte-Pointer zurück, der auf den Beginn der n-letzten Zeile zeigt. Wenn die Textdatei
     * weniger als n Zeilen beinhaltet, dann zeigt der Pointer auf den Beginn der ersten Textzeile.
     * Funktioniert sowohl für Linux/Unix-, Windows- und Alt-Mac-Zeilenumbrüche.
     *
     * @param n
     * @return
     * @throws IOException
     */
    public long getLastLinesPointer(int n) throws IOException {

        // CR = 0x0D, LF = 0x0A
        // Windows: CR LF
        // *nix: LF
        // Old-Mac: CR

        int linesFound = 0;
        long lineStartPointer = 0;

        RandomAccessFile randomAccessFile = new RandomAccessFile(this.file, "r");
        long pointer = this.file.length() - 1;

        while (pointer >= 0 && linesFound <= n) {
            randomAccessFile.seek(pointer);
            int readByte = randomAccessFile.readByte();

            boolean foundLineFeed = false;
            int lineFeedBytes = 0;

            if (readByte == 0x0A) {
                // LF
                foundLineFeed = true;
                lineFeedBytes = 1;

                if (pointer > 0) {
                    randomAccessFile.seek(pointer - 1);
                    int readByteBefore = randomAccessFile.readByte();
                    if (readByteBefore == 0x0D) {
                        // LF followed by CR --> New Line in Windows
                        lineFeedBytes = 2;
                    } else {
                        // LF only --> New Line in *nix
                    }
                }

            } else if (readByte == 0x0D) {
                // CR only --> New Line in Old-Mac
                foundLineFeed = true;
                lineFeedBytes = 1;
            }

            if (foundLineFeed) {
                linesFound++;
                lineStartPointer = pointer + 1;
                if (lineFeedBytes == 2) pointer--;
            }

            pointer--;

            if (pointer == 0 && !foundLineFeed) {
                linesFound++;
                lineStartPointer = 0;
            }

        }

        return lineStartPointer;
    }

}
