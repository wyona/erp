package org.wyona.may;

import java.io.File;

/**
 *
 */
public class DataType {

    /**
     *
     */
    public DataType(File config) {
        System.out.println(config);
    }

    /**
     *
     */
    public String[] getProperties() {
        return new String[0];
    }

    /**
     *
     */
    public String getPropertyType(String property) {
        return null;
    }

    /**
     *
     */
    public void setProperty(String property, Object object) {
    }

    /**
     * This should rather be handled by a Writer!
     */
    public void write(String path) {
    }

    /**
     * This should rather be handled by a Reader!
     */
    public void read(String path) {
    }
}
