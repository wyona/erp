package org.wyona.erp.types;

/**
 *
 */
public class Person extends AbstractType {
    protected String id;
    String name;
    String email;

    /**
     *
     */
    public Person(String path) {
        super(path);
        id = new java.io.File(path).getName();
    }

    /**
     *
     */
    public Person(String path, String name, String email) {
        super(path);
        this.name = name;
        this.email = email;
    }

    /**
     *
     */
    public String getID() {
        return id;
    }

    /**
     *
     */
    public String toString() {
        return id + ", " + name + ", " + email;
    }
}
