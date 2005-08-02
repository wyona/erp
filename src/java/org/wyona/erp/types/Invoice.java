package org.wyona.erp.types;

/**
 *
 */
public class Invoice {
    String id;
    Customer customer;

    /**
     *
     */
    public Invoice(Customer customer) {
        this.customer = customer;
        id = customer.getID() + "xxx";
    }

    /**
     *
     */
    public String getID() {
        return id;
    }
}
