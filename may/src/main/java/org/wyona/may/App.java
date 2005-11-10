package org.wyona.may;

import java.io.File;

/**
 * Command line interface
 */
public class App {
    /**
     * Also see Hibernate or OJB, Graffito or Jackrabbit node-type definition (lenya_1_4_x/src/modules/jackrabbit/repository/repository/nodetypes)
     */
    public static void main(String[] args) {
        System.out.println("Hello World!");
        DataType dt = new DataType(new File("data-types/odt/jcr.xml"));
        String[] properties = dt.getProperties();
        for (int i = 0; i < properties.length; i++) {
            System.out.println(dt.getPropertyType(properties[i]));
        }

/*
        Repository repo = RepositoryFactory.getRepository("wyona");

        Invoice invoice = new Invoice(DataTypeRegistry.getDataType("invoice","http://www.wyona.com/erp/1.0"));
        invoice.setCustomer("...");

        Email email = new Email(DataTypeRegistry.getDataType("email","http://www.wyona.com/erp/1.0"));
        email.setFrom("michael.wechner@wyona.com");
        email.setSubject("Hello World");
        email.setSource("...");

        DataTypeWriter writer = repo.getWriter("/invoices/foobar");
        writer.write(invoice);

        DataTypeReader reader = repo.getReader("/invoices/foobar");
        Invoice inv = reader.read();
*/
    }
}
