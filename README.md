# tail4J
[![Build Status](https://travis-ci.com/arthurpicht/tail4J.svg?branch=master)](https://travis-ci.com/arthurpicht/tail4J)

Java functionality for reading the tail of dynamically growing and rotating log files, inspired by the *nix command 'tail -f'.

## Status

Under development.

## Basic usage patterns

tail4J provides two main classes: *Tail* (low-level) and *TailMinusF* (high-level) running in an own thread.  

### Low-Level (*de.arthurpicht.tail4j.Tail*)

### Hello world

Initially, this one outputs the last ten lines of */var/log/syslog* to command line.
All repeated calls to *visit()* in periods of 1000 msec will add those lines, that were created in the meantime. 

```
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
```

### Custom log processing

In real world applications, you will want to do some custom processing of log statements.
In order to do so, implement the *LogstatementProcessor* interface and pass it to *Tail* on
initialization.

```
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
```

## High-Level (*de.arthurpicht.log4j.TailMinusF*)

Class *TailMinusF* provides a functionality for observing a specified file continuously by spawning an own thread.

Have a look on *TailMinusFConfBuilder* for full configuration capabilities.

### Basic usage of TailMinusF

```
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
```

### Custom log processing

Just as shown for *Tail*, here again a custom *LogstatementProcessor* can be implemented. 

```
package de.arthurpicht.tail4j.demo;

import de.arthurpicht.tail4j.LogstatementProcessor;
import de.arthurpicht.tail4j.TailMinusF;
import de.arthurpicht.tail4j.TailMinusFConfBuilder;

import java.io.File;

public class Demo2 {

    public static void main(String[] args) throws InterruptedException {

        LogstatementProcessor logstatementProcessor = logStatement -> System.out.println("Here I could do some processing of \"" + logStatement + "\" ...");

        TailMinusFConfBuilder tailMinusFConfBuilder
                = new TailMinusFConfBuilder()
                .setFile(new File("/var/log/syslog"))
                .setLogstatementProcessor(logstatementProcessor);

        TailMinusF tailMinusF = new TailMinusF(tailMinusFConfBuilder.build());

        tailMinusF.start();

        Thread.sleep(60 * 1000);

        tailMinusF.stop();

    }
}

```

### Default Exception Handling

Note that no checked exceptions are thrown by *TailMinusF*.
Instead a *ExceptionListener* is notified in case an exception occurs. 
By default, a *DefaultExceptionListener* just caches the last exception for a later processing.


```
package de.arthurpicht.tail4j.demo;

import de.arthurpicht.tail4j.DefaultExceptionListener;
import de.arthurpicht.tail4j.LogstatementProcessor;
import de.arthurpicht.tail4j.TailMinusF;
import de.arthurpicht.tail4j.TailMinusFConfBuilder;

import java.io.File;

public class Demo3 {

    public static void main(String[] args) throws InterruptedException {

        LogstatementProcessor logstatementProcessor = logStatement -> System.out.println("Here I could do some processing of \"" + logStatement + "\" ...");

        TailMinusFConfBuilder tailMinusFConfBuilder
                = new TailMinusFConfBuilder()
                .setFile(new File("/var/log/syslog"))
                .setLogstatementProcessor(logstatementProcessor);

        TailMinusF tailMinusF = new TailMinusF(tailMinusFConfBuilder.build());

        tailMinusF.start();

        Thread.sleep(60 * 1000);

        tailMinusF.stop();

        DefaultExceptionListener defaultExceptionListener = (DefaultExceptionListener) tailMinusF.getTailMinusFConf().getExceptionListener();
        if (defaultExceptionListener.isExceptionOccurred()) {
            System.out.println("Exception occurred: " + defaultExceptionListener.getLastException().getMessage());
        }

    }
}

```

### Custom Exception Handling


As shown here, an instance of *ExceptionListener* interface can be implemented, in order to provide a custom exception handling.

```
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
```

## License

Apache License 2.0