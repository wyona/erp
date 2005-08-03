package org.wyona.erp.types;

/**
 *
 */
public class Person {
    protected String id;
    String name;
    String email;

    /**
     *
     */
    public Person(String id) {
        this.id = id;
    }

    /**
     *
     */
    public Person(String id, String name, String email) {
        this.id = id;
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
        return id;
    }
}
