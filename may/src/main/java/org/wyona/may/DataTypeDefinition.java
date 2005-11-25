package org.wyona.may;

import java.io.File;

/**
 *
 */
public class DataTypeDefinition {
    private String name = "invoice";
    private String namespace = "http://www.wyona.org/may/1.0";
    // NOTE: One cannot require to put a version into the namespace, hence a separate version
    private String version = "1.0";

    /**
     *
     */
    public DataTypeDefinition(File config) {
        System.out.println(config);
    }

    /**
     *
     */
    public String toString() {
        return name + " " + namespace + " " + version;
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
}
