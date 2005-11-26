package org.wyona.may.core;

import java.io.File;

/**
 *
 */
public class DataType {

    /**
     *
     */
    public DataType(File xml) {
        System.out.println(xml);
    }

    /**
     * Get Data Type Definition
     */
    public DataTypeDefinition getDataTypeDefinition() {
        return new DataTypeDefinition(new File("invoice/dt.xml"));
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
