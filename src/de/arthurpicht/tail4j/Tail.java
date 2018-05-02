package de.arthurpicht.tail4j;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Arthur Picht, Düsseldorf, 02.05.18.
 */
public class Tail {

    private final File logFile;
    private final int nrLastLines;
    private final LogstatementProcessor logstatementProcessor;

    private long pointer;
    private long lastFileLength;

    public Tail(File logFile) throws IOException {

        if (logFile == null) throw new IllegalArgumentException("Specified file (logFile) is null.");

        this.logFile = logFile;
        this.nrLastLines = 10;
        this.logstatementProcessor = new StdOutLogstatementProcessor();

        init();
    }

    public Tail(File logFile, int nrLastLines, LogstatementProcessor logstatementProcessor) throws IOException {

        if (logFile == null) throw new IllegalArgumentException("Specified file (logFile) is null.");
        if (nrLastLines < 0) throw new IllegalArgumentException("Specified number of lines (nrLastLines) is smaller than zero.");
        if (logstatementProcessor == null) throw new IllegalArgumentException("Specified instance of LogstatementProcessor is null.");

        this.logFile = logFile;
        this.nrLastLines = nrLastLines;
        this.logstatementProcessor = logstatementProcessor;

        init();
    }

    private void init() throws IOException {

        ReverseFileReader reverseFileReader = new ReverseFileReader(logFile);
        pointer = reverseFileReader.getLastLinesPointer(nrLastLines);
    }

    public void visit() throws IOException {

        long fileLength = this.logFile.length();

        if (fileLength < this.lastFileLength) {
            Logger.info("Logfile " + this.logFile.getAbsolutePath() + " skipped.");
            this.pointer = 0;
        }

        if (fileLength > this.pointer) {

            RandomAccessFile readFileAccess = new RandomAccessFile(logFile, "r");
            readFileAccess.seek(this.pointer);

            String line;
            while ((line = readFileAccess.readLine()) != null) {
                this.logstatementProcessor.process(line);
            }

            this.pointer = readFileAccess.getFilePointer();
            readFileAccess.close();
        }

        this.lastFileLength = fileLength;
    }

    public File getLogFile() {
        return logFile;
    }

    public int getNrLastLines() {
        return nrLastLines;
    }

    public LogstatementProcessor getLogstatementProcessor() {
        return logstatementProcessor;
    }

    public long getPointer() {
        return pointer;
    }
}
