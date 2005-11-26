package org.wyona.may.core;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 */
public class DataType {

    /**
     *
     */
    public DataType(File xmlFile) throws IOException {
        System.out.println(xmlFile);
        try {
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            //DefaultHandler saxHandler = new DataTypeHandler();
            saxParser.parse(xmlFile, new DefaultHandler());
        } catch (ParserConfigurationException e) {
            System.err.println(e);
        } catch (SAXException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    /**
     * Get Data Type Definition
     */
    public DataTypeDefinition getDataTypeDefinition() {
        return new DataTypeDefinition(new File("invoice/dt.xml"));
    }

    /**
     *
     */
    public void setProperty(String property, Object object) {
    }

    /**
     * This should rather be handled by a Writer!
     */
    public void write(String path) {
    }

    /**
     * This should rather be handled by a Reader!
     */
    public void read(String path) {
    }
}
