package org.wyona.erp.types;

/**
 *
 */
public class Project {
    String id;
    String title;
    String customer;
    String description;

    /**
     *
     */
    public Project(String id) {
        this.id = id;
    }

    /**
     *
     */
    public Project(String id, String title) {
        this.id = id;
        this.title = title;
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
