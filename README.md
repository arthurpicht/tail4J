# tail4J
[![Build Status](https://travis-ci.com/arthurpicht/tail4J.svg?branch=master)](https://travis-ci.com/arthurpicht/tail4J)

Java functionality for reading the tail of dynamically growing and rotating log files, inspired by the *nix command 'tail -f'.

## Basic usage patterns

### Low-Level (*de.arthurpicht.tail4j.Tail*)

### Hello world

Initially, this one outputs the last ten lines of */var/log/syslog* to command line.
All repeated calls to *visit()* in periods of 1000 msec will add those lines, that were created in the meantime. 

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

### Hello world #2

In real world applications, you will want to do some custom processing of log statements.
In order to do so, implement the *LogstatementProcessor* interface and pass it to *Tail* on
construction time.

... work in progress ... to be continued ...