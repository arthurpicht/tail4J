package de.arthurpicht.tail4j.demo;

import de.arthurpicht.tail4j.TailMinusF;
import de.arthurpicht.tail4j.TailMinusFConfBuilder;

import java.io.File;

public class Demo1 {

    public static void main(String[] args) throws InterruptedException {

        TailMinusFConfBuilder tailMinusFConfBuilder
                = new TailMinusFConfBuilder()
                .setFile(new File("/var/log/syslog"));

        TailMinusF tailMinusF = new TailMinusF(tailMinusFConfBuilder.build());

        tailMinusF.start();

        Thread.sleep(60 * 1000);

        tailMinusF.stop();

    }
}
