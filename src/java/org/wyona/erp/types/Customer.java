package org.wyona.erp.types;

/**
 *
 */
public class Customer {
    String id;
    String name;

    /**
     *
     */
    public Customer(String id) {
        this.id = id;
    }

    /**
     *
     */
    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
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
