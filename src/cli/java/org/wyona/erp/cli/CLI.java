package org.wyona.erp.cli;

import org.wyona.erp.ERP;

/**
 * Command Line Interface to add, modify, rename, move and delete data
 */
public class CLI {

    /**
     *
     */
    public static void main(String[] args) {
    	if (args.length == 0) {
            System.out.println("Usage: --help");
            return;
        }

        if (args[0].equals("--help")) {
            listHelp();
            return;
        }

        String command = args[0];

        String workspaceName = "default";

        if (command.equals("--help")) {
           listHelp();
   	    } else if (command.equals("--add-task")) {
            if (args.length < 3) {
                System.out.println("Usage: --add-task TITLE OWNER [PROJECT]");
                return;
            }
            String title = args[1]; // e.g. My first task
            String owner = args[2]; // e.g. michi
            String project = null;  // e.g. lenya
            if (args.length >= 4) {
                project = args[3];
                try {
                    new ERP().addTask(workspaceName, title, owner, project);
                } catch (Exception e) {
                    System.err.println(e);
                }
            } else {
                try {
                    new ERP().addTask(workspaceName, title, owner);
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        } else if (command.equals("--list-tasks")) {
            new ERP().listTasks(workspaceName);
        } else if (command.equals("--add-project")) {
            if (args.length < 3) {
                System.out.println("Usage: " + getProjectSynopsis());
                return;
            }
            String id = args[1]; // e.g. my-first-project
            String title = args[2]; // e.g. My first project
            //String description = null; //
            String customer = null; //
            if (args.length >= 4) {
                customer = args[3];
                try {
                    new ERP().addProject(workspaceName, id, title, customer);
                } catch (Exception e) {
                    System.err.println(e);
                }
            } else {
                try {
                    new ERP().addProject(workspaceName, id, title);
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        } else if (command.equals("--list-projects")) {
            new ERP().listProjects(workspaceName);
        } else if (command.equals("--add-person")) {
            if (args.length < 4) {
                System.out.println("Usage: --add-person ID NAME E-MAIL");
                return;
            }
            String id = args[1]; // e.g. michi
            String name = args[2]; // e.g. Michael Wechner
            String email = args[3]; // e.g. michael.wechner@wyona.com
            try {
                new ERP().addPerson(workspaceName, id, name, email);
            } catch (Exception e) {
                System.err.println(e);
            }
        } else if (command.equals("--list-persons")) {
            new ERP().listPersons(workspaceName);
        } else if (command.equals("--add-company")) {
            if (args.length < 3) {
                System.out.println("Usage: " + getCompanySynopsis());
                return;
            }
            String id = args[1]; // e.g. wyona
            String name = args[2]; // e.g. Wyona AG
            try {
                new ERP().addCustomer(workspaceName, id, name);
            } catch (Exception e) {
                System.err.println(e);
            }
        } else if (command.equals("--list-companies")) {
            new ERP().listCustomers(workspaceName);
        } else if (command.equals("--add-invoice")) {
            if (args.length < 2) {
                System.out.println("Usage: " + getInvoiceSynopsis());
                return;
            }
            String customerID = args[1]; // e.g. wyona
            try {
                new ERP().addInvoice(workspaceName, customerID);
            } catch (Exception e) {
                System.err.println(e);
            }
        } else if (command.equals("--list-invoices")) {
            new ERP().listInvoices(workspaceName);
        } else if (command.equals("--get-topic-map")) {
            System.out.println(new ERP().getTopicMap(workspaceName));
        } else {
            System.out.println("No such command: " + command);
        }
    }

    /**
     *
     */
    public static void listHelp() {
        System.out.println("Add Task:          --add-task TITLE OWNER [PROJECT]");
        System.out.println("Lists Tasks:       --list-tasks");

        System.out.print("\n");

        System.out.println("Add Project:       " + getProjectSynopsis());
        System.out.println("Lists Projects:    --list-projects");

        System.out.print("\n");

        System.out.println("Add Person:        --add-person ID NAME E-MAIL");
        System.out.println("Lists Persons:     --list-persons");

        System.out.print("\n");

        System.out.println("Add Company:       " + getCompanySynopsis());
        System.out.println("Lists Companies:   --list-companies");

        System.out.print("\n");

        System.out.println("Add Invoice:       " + getInvoiceSynopsis());
        System.out.println("Lists Invoices:    --list-invoices");

        System.out.print("\n");

        System.out.println("Add Contracts:     " + getContractSynopsis());
        System.out.println("Lists Contracts:   --list-contracts");

        System.out.print("\n");

        System.out.println("Add Offers:        " + getOfferSynopsis());
        System.out.println("Lists Offers:      --list-offers");

        System.out.print("\n");

        System.out.println("Get Topic Map:     " + getTopicMapSynopsis());
    }

    /**
     *
     */
    public static String getCompanySynopsis() {
        return "--add-company ID NAME";
    }

    /**
     *
     */
    public static String getInvoiceSynopsis() {
        return "--add-invoice CUSTOMER";
    }

    /**
     *
     */
    public static String getProjectSynopsis() {
        return "--add-project ID TITLE [CUSTOMER]";
        //return "--add-project ID TITLE [-description=DESCRIPTION] [-customer=CUSTOMER]";
    }

    /**
     *
     */
    public static String getTopicMapSynopsis() {
        return "--get-topic-map";
    }

    /**
     *
     */
    public static String getContractSynopsis() {
        return "--add-contract";
    }

    /**
     *
     */
    public static String getOfferSynopsis() {
        return "--add-offer";
    }
}
