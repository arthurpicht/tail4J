package de.arthurpicht.tail4j.demo;

import de.arthurpicht.tail4j.ExceptionListener;
import de.arthurpicht.tail4j.LogstatementProcessor;
import de.arthurpicht.tail4j.TailMinusF;
import de.arthurpicht.tail4j.TailMinusFConfBuilder;

import java.io.File;

public class Demo4 {

    public static void main(String[] args) throws InterruptedException {

        LogstatementProcessor logstatementProcessor = logStatement -> System.out.println("Here I could do some processing of \"" + logStatement + "\" ...");

        ExceptionListener exceptionListener = e -> System.out.println("Exception occured: " + e.getMessage());

        TailMinusFConfBuilder tailMinusFConfBuilder
                = new TailMinusFConfBuilder()
                .setFile(new File("/var/log/syslog"))
                .setLogstatementProcessor(logstatementProcessor)
                .setExceptionListener(exceptionListener);

        TailMinusF tailMinusF = new TailMinusF(tailMinusFConfBuilder.build());

        tailMinusF.start();

        Thread.sleep(60 * 1000);

        tailMinusF.stop();

    }
}
