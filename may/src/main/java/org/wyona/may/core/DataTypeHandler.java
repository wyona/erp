package org.wyona.may.core;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import org.apache.log4j.Category;

/**
 * http://www.saxproject.org/quickstart.html
 */
public class DataTypeHandler extends DefaultHandler {

    private static Category log = Category.getInstance(DataTypeHandler.class);
    private String rootElementQName = null;

    /**
     *
     */
    public DataTypeHandler() {
        super();
    }

    /**
     *
     */
    public String getDataTypeDefinitionName() {
        return "michi";
    }

    /**
     *
     */
    public void startDocument() {
    }

    /**
     *
     */
    public void startElement(String uri, String name, String qName, Attributes attributes) {
        log.debug("URI: " + uri + "\n");
        log.debug("Name: " + name);
        log.debug("qName: " + qName);

        if (rootElementQName == null) {
            rootElementQName = qName;
            log.error("Root Element qName: " + rootElementQName);

	    log.error("Attributes length: " + attributes.getLength());
            for (int i = 0; i < attributes.getLength(); i++) {
                log.error("Attribute: " + attributes.getQName(i) + " = " + attributes.getValue(i) + " (" + attributes.getURI(i) + ")");
            }

            String dtName = attributes.getValue("http://www.wyona.org/erp/1.0","dt-id");
            if (dtName != null) {
               log.error("DataType Name: " + dtName);
            } else {
               log.warn("No DataType Name specified!");
            }
        }
    }
}
