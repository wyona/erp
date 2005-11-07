package org.wyona.may;

import java.io.File;

/**
 * Command line interface
 */
public class App {
    /**
     *
     */
    public static void main(String[] args) {
        System.out.println("Hello World!");
        DataType dt = new DataType(new File("data-types/odt/jcr.xml"));
    }
}
