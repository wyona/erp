package org.wyona.may.cli;

import java.io.File;
import java.io.IOException;

import org.wyona.may.core.DataType;
import org.wyona.may.core.DataTypeDefinition;

import org.wyona.yarep.core.RepositoryFactory;

/**
 * Command line interface
 */
public class CLI {
    /**
     * Also see Hibernate or OJB, Graffito or Jackrabbit node-type definition (lenya_1_4_x/src/modules/jackrabbit/repository/repository/nodetypes)
     */
    public static void main(String[] args) {
        System.out.println("Hello World!");

        try {
	    RepositoryFactory repoFactory;
            repoFactory = new RepositoryFactory();
            System.out.println(repoFactory);
        } catch (Exception e) {
            System.err.println(e);
        }

        //Repository repo = RepositoryFactory.getRepository("wyona");
        // Create data type instance
        try {
            //Reader reader = repo.getReader(new Path("/invoices/invoice-1.xml"));
            //DataType dti = new DataType(reader);
            //DataType dti = new DataType(new File("/home/michi/src/wyona/wyona/misc/erp/src/lenya/pubs/erp/content/tasks/task-csf1.xml"));
            DataType dti = new DataType(new File("content/invoices/invoice-1.xml"));
            // Get data type definition registry
            DataTypeDefinition dtd = dti.getDataTypeDefinition();
	    // DataTypeRegistry.getDataTypeDefinition(...)
            System.out.println(dtd);
            String[] properties = dtd.getProperties();
            for (int i = 0; i < properties.length; i++) {
                System.out.println(dtd.getPropertyType(properties[i]));
            }
	    // dti.getXML(); // Apply controller, e.g. in the case of an invoice calculate the total amount
        } catch (IOException e) {
            System.err.println(e);
        }



/*
        Repository repo = RepositoryFactory.getRepository("wyona");

        Invoice invoice = new Invoice(DataTypeRegistry.getDataTypeDefinition("invoice", "http://www.wyona.com/erp/1.0", "1.0"));
        invoice.setCustomer("...");

        Email email = new Email(DataTypeRegistry.getDataTypeDefinition("email", "http://www.wyona.com/erp/1.0", "1.0"));
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
