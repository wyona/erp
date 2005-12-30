package org.wyona.may.cli;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

import org.wyona.may.core.DataType;
import org.wyona.may.core.DataTypeDefinition;

import org.wyona.yarep.core.Path;
import org.wyona.yarep.core.Repository;
import org.wyona.yarep.core.RepositoryFactory;

/**
 * Command line interface
 */
public class CLI {
    /**
     * Also see Hibernate or OJB, Graffito or Jackrabbit node-type definition (lenya_1_4_x/src/modules/jackrabbit/repository/repository/nodetypes)
     */
    public static void main(String[] args) {
        System.out.println("Welcome to the Wyona May Framework!");

        try {
	    RepositoryFactory repoFactory;
            repoFactory = new RepositoryFactory();
            System.out.println(repoFactory);

	    // Get repository
            Repository repo = repoFactory.newRepository("erp-sample");

            // Get resource from repository
            Reader reader = repo.getReader(new Path("/companies/wyona/debtors/2005/invoice-001-05-01/content.xml"));
            // Create data type instance
            DataType dti = new DataType(reader);

            // Get data type definition registry
            DataTypeDefinition dtd = dti.getDataTypeDefinition();
            // DataTypeRegistry.getDataTypeDefinition(...)

            System.out.println(dtd);
            String[] properties = dtd.getProperties();
            for (int i = 0; i < properties.length; i++) {
                System.out.println(dtd.getPropertyType(properties[i]));
            }
	    // dti.getXML(); // Apply controller, e.g. in the case of an invoice calculate the total amount
        } catch (Exception e) {
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
