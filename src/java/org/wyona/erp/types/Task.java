package org.wyona.erp.types;

/**
 *
 */
public class Task {
    String title;
    Owner owner;
    String status;
    String description;
    String type;
    String priority;
    String severity;
    String project;
    String component;
    String invoice;
    class Effort {
        String hours;
        String days;
    }

    /**
     *
     */
    public Task(String title, Owner owner) {
        this.title = title;
        this.owner = owner;
    }

    /**
     *
     */
    public Task(String title, Owner owner, String project) {
        this.title = title;
        this.owner = owner;
        this.project = project;
    }
}
