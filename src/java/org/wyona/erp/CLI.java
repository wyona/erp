package org.wyona.erp;

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

        if (args.length <= 2) {
            System.out.println("Usage: REPO_CONFIG REPO_HOME [command]");
            return;
        }
        String repoConfig = args[0]; // e.g. repository.xml
        String repoHomeDir = args[1]; // e.g. repotest
        String command = args[2];

        String workspaceName = "default";

        if (command.equals("--help")) {
            listHelp();
	} else if (command.equals("--add-task")) {
            if (args.length < 5) {
                System.out.println("Usage: REPO_CONFIG REPO_HOME --add-task TITLE OWNER [PROJECT]");
                return;
            }
            String title = args[3]; // e.g. My first task
            String owner = args[4]; // e.g. michi
            String project = null;  // e.g. lenya
            if (args.length >= 6) {
                project = args[5];
                new ERP(repoConfig, repoHomeDir).addTask(workspaceName, title, owner, project);
            } else {
                new ERP(repoConfig, repoHomeDir).addTask(workspaceName, title, owner);
            }
	} else if (command.equals("--list-tasks")) {
            new ERP(repoConfig, repoHomeDir).listTasks(workspaceName);
	} else if (command.equals("--add-project")) {
            if (args.length < 5) {
                System.out.println("Usage: REPO_CONFIG REPO_HOME --add-project ID TITLE");
                return;
            }
            String id = args[3]; // e.g. my-first-project
            String title = args[4]; // e.g. My first project
            new ERP(repoConfig, repoHomeDir).addProject(workspaceName, id, title);
	} else if (command.equals("--list-projects")) {
            new ERP(repoConfig, repoHomeDir).listProjects(workspaceName);
	} else if (command.equals("--add-person")) {
            if (args.length < 6) {
                System.out.println("Usage: REPO_CONFIG REPO_HOME --add-person ID NAME E-MAIL");
                return;
            }
            String id = args[3]; // e.g. michi
            String name = args[4]; // e.g. Michael Wechner
            String email = args[5]; // e.g. michael.wechner@wyona.com
            new ERP(repoConfig, repoHomeDir).addPerson(workspaceName, id, name, email);
	} else if (command.equals("--list-persons")) {
            new ERP(repoConfig, repoHomeDir).listPersons(workspaceName);
	} else if (command.equals("--add-company")) {
            if (args.length < 5) {
                System.out.println("Usage: " + getCompanySynopsis());
                return;
            }
            String id = args[3]; // e.g. wyona
            String name = args[4]; // e.g. Wyona AG
            new ERP(repoConfig, repoHomeDir).addCustomer(workspaceName, id, name);
	} else if (command.equals("--list-companies")) {
            new ERP(repoConfig, repoHomeDir).listCustomers(workspaceName);
	} else if (command.equals("--add-invoice")) {
            if (args.length < 4) {
                System.out.println("Usage: " + getInvoiceSynopsis());
                return;
            }
            String customerID = args[3]; // e.g. wyona
            new ERP(repoConfig, repoHomeDir).addInvoice(workspaceName, customerID);
	} else if (command.equals("--list-invoices")) {
            new ERP(repoConfig, repoHomeDir).listInvoices(workspaceName);
        } else {
            System.out.println("No such command: " + command);
        }
    }

    /**
     *
     */
    public static void listHelp() {
        System.out.println("Add Task:          REPO_CONFIG REPO_HOME --add-task TITLE OWNER [PROJECT]");
        System.out.println("Lists Tasks:       REPO_CONFIG REPO_HOME --list-tasks");

        System.out.print("\n");

        System.out.println("Add Project:       REPO_CONFIG REPO_HOME --add-project ID TITLE [DESCRIPTION]");
        System.out.println("Lists Projects:    REPO_CONFIG REPO_HOME --list-projects");

        System.out.print("\n");

        System.out.println("Add Person:        REPO_CONFIG REPO_HOME --add-person ID NAME E-MAIL");
        System.out.println("Lists Persons:     REPO_CONFIG REPO_HOME --list-persons");

        System.out.print("\n");

        System.out.println("Add Company:       " + getCompanySynopsis());
        System.out.println("Lists Companies:   REPO_CONFIG REPO_HOME --list-companies");

        System.out.print("\n");

        System.out.println("Add Invoice:       " + getInvoiceSynopsis());
        System.out.println("Lists Invoices:   REPO_CONFIG REPO_HOME --list-invoices");
    }

    /**
     *
     */
    public static String getCompanySynopsis() {
        return "REPO_CONFIG REPO_HOME --add-company ID NAME";
    }

    /**
     *
     */
    public static String getInvoiceSynopsis() {
        return "REPO_CONFIG REPO_HOME --add-invoice CUSTOMER";
    }
}
