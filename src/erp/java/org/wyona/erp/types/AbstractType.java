package org.wyona.erp.types;

/**
 *
 */
public abstract class AbstractType {
    protected String path;

    /**
     * @param path JCR path
     */
    public AbstractType(String path) {
        this.path = path;
    }

    /**
     * Get JCR path
     */
    public String getPath() {
        return path;
    }
}
