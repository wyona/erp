package org.wyona.erp.types;

/**
 *
 */
public class Owner {
    protected String id;
    String name;
    String email;

    /**
     *
     */
    public Owner(String id) {
        this.id = id;
    }

    /**
     *
     */
    public Owner(String id, String name, String email) {
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
