package org.wyona.may.core;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.Reader;

import org.apache.tools.ant.util.ReaderInputStream;

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
     * @param file The XML file to be read
     */
    public DataType(File file) throws IOException {
        System.out.println(file);
        try {
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            //DefaultHandler saxHandler = new DataTypeHandler();
            saxParser.parse(file, new DefaultHandler());
        } catch (ParserConfigurationException e) {
            System.err.println(e);
            throw new IOException(e.toString());
        } catch (SAXException e) {
            System.err.println(e);
            throw new IOException(e.toString());
        }
    }

    /**
     *
     */
    public DataType(InputStream is) throws IOException {
        try {
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            //DefaultHandler saxHandler = new DataTypeHandler();
            saxParser.parse(is, new DefaultHandler());
        } catch (ParserConfigurationException e) {
            System.err.println(e);
            throw new IOException(e.toString());
        } catch (SAXException e) {
            System.err.println(e);
            throw new IOException(e.toString());
        }
    }

    /**
     *
     */
    public DataType(Reader reader) throws IOException {
        try {
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            //DefaultHandler saxHandler = new DataTypeHandler();
            saxParser.parse(new ReaderInputStream(reader), new DefaultHandler());
        } catch (ParserConfigurationException e) {
            System.err.println(e);
            throw new IOException(e.toString());
        } catch (SAXException e) {
            System.err.println(e);
            throw new IOException(e.toString());
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
