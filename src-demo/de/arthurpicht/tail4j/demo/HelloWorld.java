package de.arthurpicht.tail4j.demo;

import de.arthurpicht.tail4j.Tail;

import java.io.File;
import java.io.IOException;

public class HelloWorld {

    public static void main(String[] args) throws IOException, InterruptedException {

        Tail tail = new Tail(new File("/var/log/syslog"));

        while (true) {

            tail.visit();

            Thread.sleep(1000);
        }
    }
}
