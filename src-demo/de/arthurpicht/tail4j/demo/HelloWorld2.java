package de.arthurpicht.tail4j.demo;

import de.arthurpicht.tail4j.LogstatementProcessor;
import de.arthurpicht.tail4j.Tail;

import java.io.File;
import java.io.IOException;

public class HelloWorld2 {

    public static void main(String[] args) throws IOException, InterruptedException {

        int nrOfLastLines = 5;

        LogstatementProcessor logstatementProcessor = logStatement -> System.out.println("Here I could do some processing of \"" + logStatement + "\" ...");

        Tail tail = new Tail(new File("/var/log/syslog"), nrOfLastLines, logstatementProcessor);

        while (true) {

            tail.visit();

            Thread.sleep(1000);
        }
    }
}
