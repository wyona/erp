/*
 * Created on Aug 16, 2005
 *
 */
package org.wyona.erp.lenya.usecases;

import java.io.Serializable;

/**
 * @author thorsten
 *
 */
public class TaskBean implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 3485696328707381131L;
    private String owner, title, project, component = "";
    /**
     * @return Returns the owner.
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner
     *            The owner to set.
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * @return Returns the title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * @return Returns the component.
     */
    public String getComponent() {
        return component;
    }

    /**
     * @param component
     *            The component to set.
     */
    public void setComponent(String component) {
        this.component = component;
    }

    /**
     * @return Returns the project.
     */
    public String getProject() {
        return project;
    }

    /**
     * @param project
     *            The project to set.
     */
    public void setProject(String project) {
        this.project = project;
    }
}
