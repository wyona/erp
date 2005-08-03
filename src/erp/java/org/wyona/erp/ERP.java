package org.wyona.erp;

import org.apache.jackrabbit.core.jndi.RegistryHelper;
//import org.apache.jackrabbit.core.value.StringValue;

import javax.jcr.Credentials;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.PropertyType;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.Value;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.util.Hashtable;

import org.apache.log4j.Category;

import org.wyona.erp.types.Customer;
import org.wyona.erp.types.Invoice;
import org.wyona.erp.types.Owner;
import org.wyona.erp.types.Person;
import org.wyona.erp.types.Project;
import org.wyona.erp.types.Task;

/**
 *
 */
public class ERP {

    private static Category log = Category.getInstance(ERP.class);

    private String REPO_NAME = "erp-repo";
    private String USERID = "michiii";
    private char[] PASSWORD = "".toCharArray();

    String PERSONS_NODE_NAME = "persons";
    String COMPANIES_NODE_NAME = "companies";
    String INVOICES_NODE_NAME = "invoices";
    String PROJECTS_NODE_NAME = "projects";

    Context context;

    /**
     *
     */
    public ERP(String repoConfig, String repoHomeDir) {
        // NOTE: Is being set within the shell script
        //System.setProperty("java.security.auth.login.config", "jaas.config");
        try {
            bindRepository(repoConfig, repoHomeDir);
        } catch(Exception e) {
            log.error(e);
        }
    }

    /**
     * Add a new task to the repository
     *
     * @param title Title of task
     * @param owner Owner of task
     */
    public void addTask(String workspaceName, String title, String ownerID) {
        log.info("Attempting to add task: " + title + " (" + ownerID + ")");

        addTask(workspaceName, title, ownerID, null);
    }

    /**
     * Add a new task to the repository
     *
     * @param title Title of task
     * @param owner Owner of task
     * @param project Project associated with task
     */
    public void addTask(String workspaceName, String title, String ownerID, String projectID) {
        log.info("Attempting to add task: " + title + ", " + ownerID + ", " + projectID);

        Owner owner = new Owner(ownerID);
        if (!existsOwner(workspaceName, owner)) {
            log.warn("No such owner: " + owner + " - Adding task aborted - You might want to add a person first (--help).");
            return;
        }

        Project project = null;	 
        if (projectID != null) {
            project = new Project(projectID);
            if (!existsProject(workspaceName, project)) {
                log.warn("No such project: " + project + " - Adding task aborted.");
                return;
            }
        }

        Task task = new Task(title, owner, project);

        Session session = null;
        try {
            Repository repo = getRepository();
            Credentials credentials = new SimpleCredentials(USERID, PASSWORD);
            session = repo.login(credentials, workspaceName);
            Node rootNode = session.getRootNode();
	    log.info("Name of root node: " + rootNode.getPrimaryNodeType().getName());

            Node tasksNode = null;
            if (!rootNode.hasNode("tasks")) {
                tasksNode = rootNode.addNode("tasks");
            } else {
                tasksNode = rootNode.getNode("tasks");
            }

            String relPath = "task-" + System.currentTimeMillis();
            if (!tasksNode.hasNode(relPath)) {
                Node taskNode = tasksNode.addNode(relPath);
                //taskNode.addMixin("mix:versionable");
                taskNode.addMixin("mix:referenceable");
                taskNode.setProperty("title", title);
                setTopic(taskNode, "task");
                //taskNode.setProperty("title", new StringValue(title));

                // Create association/reference with owner
	        Node ownerNode = rootNode.getNode(PERSONS_NODE_NAME + "/" + owner.getID());
                createBidirectionalAssociation(taskNode, "task", ownerNode, "owner");

                // Create association with project
                if (project != null) {
	            Node projectNode = rootNode.getNode(PROJECTS_NODE_NAME + "/" + project.getID());
                    createBidirectionalAssociation(taskNode, "task", projectNode, "project");
                }
	        log.info("UUID of task node: " + taskNode.getUUID());
	        log.info("Name of task node: " + taskNode.getName());
	        log.info("Path of task node: " + taskNode.getPath());
                session.checkPermission("/tasks" + relPath, "add_node");
                //session.checkPermission("/", "read");
                //session.checkPermission("/", "add_node");
                session.save();
            } else {
                log.info("Node already exists: " + relPath);
            }
        } catch (Exception e) {
            log.error(e);
        } finally {
            if (session != null) session.logout();
        }
    }

    /**
     * List all tasks
     */
    public void listTasks(String workspaceName) {
        list(workspaceName, "tasks", "Task");
    }

    /**
     * Add a new project to the repository
     *
     * @param id ID of project
     * @param title Title of project
     * @param customer Customer associated with project
     */
    public void addProject(String workspaceName, String id, String title, String customerID) {
        log.info("Attempting to add project: " + id + ", " + title + ", " + customerID);

        Customer customer = null;	 
        if (customerID != null) {
            customer = new Customer(customerID);
            if (!existsCompany(workspaceName, customer)) {
                log.warn("No such customer: " + customer + " - Adding project aborted.");
                return;
            }
        }

        Project project = new Project(id, title);

        Session session = null;
        try {
            Repository repo = getRepository();
            Credentials credentials = new SimpleCredentials(USERID, PASSWORD);
            session = repo.login(credentials, workspaceName);
            Node rootNode = session.getRootNode();

            Node projectsNode = getNode(rootNode, PROJECTS_NODE_NAME);

            String relPath = id;
            if (!projectsNode.hasNode(relPath)) {
                Node projectNode = projectsNode.addNode(relPath);
                projectNode.addMixin("mix:referenceable");
                projectNode.setProperty("title", title);
	        log.info("UUID of project node: " + projectNode.getUUID());
	        log.info("Name of project node: " + projectNode.getName());
	        log.info("Path of project node: " + projectNode.getPath());

                // Create association with customer
                if (customer != null) {
	            Node customerNode = rootNode.getNode(COMPANIES_NODE_NAME + "/" + customer.getID());
                    createBidirectionalAssociation(projectNode, "project", customerNode, "customer");
                }

                session.checkPermission("/" +PROJECTS_NODE_NAME + relPath, "add_node");
                session.save();
            } else {
                log.info("Node already exists: " + relPath);
            }
        } catch (Exception e) {
            log.error(e);
        } finally {
            if (session != null) session.logout();
        }
    }

    /**
     * Add a new project to the repository
     *
     * @param id ID of project
     * @param title Title of project
     */
    public void addProject(String workspaceName, String id, String title) {
        addProject(workspaceName, id, title, null);

/*
        log.info("Attempting to add project: " + id + ", " + title);

        Project project = new Project(id, title);

        Session session = null;
        try {
            Repository repo = getRepository();
            Credentials credentials = new SimpleCredentials(USERID, PASSWORD);
            session = repo.login(credentials, workspaceName);
            Node rootNode = session.getRootNode();

            Node projectsNode = getNode(rootNode, PROJECTS_NODE_NAME);

            String relPath = id;
            if (!projectsNode.hasNode(relPath)) {
                Node projectNode = projectsNode.addNode(relPath);
                projectNode.addMixin("mix:referenceable");
                projectNode.setProperty("title", title);
	        log.info("UUID of project node: " + projectNode.getUUID());
	        log.info("Name of project node: " + projectNode.getName());
	        log.info("Path of project node: " + projectNode.getPath());
                session.checkPermission("/" +PROJECTS_NODE_NAME + relPath, "add_node");
                session.save();
            } else {
                log.info("Node already exists: " + relPath);
            }
        } catch (Exception e) {
            log.error(e);
        } finally {
            if (session != null) session.logout();
        }
*/
    }

    /**
     * List all projects
     */
    public void listProjects(String workspaceName) {
        list(workspaceName, PROJECTS_NODE_NAME, "Project");
    }

    /**
     * Add a new person to the repository
     *
     * @param id ID of person, e.g. michi
     * @param name Name of person, e.g. Michael Wechner
     */
    public void addPerson(String workspaceName, String id, String name, String email) {
        log.info("Attempting to add person: " + id + ", " + name + ", " + email);

        Person person = new Person(id, name, email);

        Session session = null;
        try {
            Repository repo = getRepository();
            Credentials credentials = new SimpleCredentials(USERID, PASSWORD);
            session = repo.login(credentials, workspaceName);
            Node rootNode = session.getRootNode();

            Node personsNode = null;
            if (!rootNode.hasNode(PERSONS_NODE_NAME)) {
                personsNode = rootNode.addNode(PERSONS_NODE_NAME);
            } else {
                personsNode = rootNode.getNode(PERSONS_NODE_NAME);
            }

            String relPath = id;
            if (!personsNode.hasNode(relPath)) {
                Node personNode = personsNode.addNode(relPath);
                personNode.addMixin("mix:referenceable");
                personNode.setProperty("name", name);
                personNode.setProperty("email", email);
	        log.info("UUID of person node: " + personNode.getUUID());
	        log.info("Name of person node: " + personNode.getName());
	        log.info("Path of person node: " + personNode.getPath());
                session.checkPermission("/"+ PERSONS_NODE_NAME + relPath, "add_node");
                session.save();
            } else {
                log.info("Node already exists: " + relPath);
            }
        } catch (Exception e) {
            log.error(e);
        } finally {
            if (session != null) session.logout();
        }
    }

    /**
     * List all persons
     */
    public void listPersons(String workspaceName) {
        list(workspaceName, PERSONS_NODE_NAME, "Person");
    }

    /**
     * Add a new customer to the repository
     *
     * @param id ID of customer
     * @param name Name of Customer
     */
    public void addCustomer(String workspaceName, String id, String name) {
        log.info("Attempting to add customer: " + id + ", " + name);

        Customer customer = new Customer(id, name);

        Session session = null;
        try {
            Repository repo = getRepository();
            Credentials credentials = new SimpleCredentials(USERID, PASSWORD);
            session = repo.login(credentials, workspaceName);
            Node rootNode = session.getRootNode();

            Node companiesNode = getNode(rootNode, COMPANIES_NODE_NAME);

            String relPath = id;
            if (!companiesNode.hasNode(relPath)) {
                Node companyNode = companiesNode.addNode(relPath);
                companyNode.addMixin("mix:referenceable");
                companyNode.setProperty("name", name);
	        log.info("UUID of customer node: " + companyNode.getUUID());
	        log.info("Name of customer node: " + companyNode.getName());
	        log.info("Path of customer node: " + companyNode.getPath());
                session.checkPermission("/" + COMPANIES_NODE_NAME + relPath, "add_node");
                session.save();
            } else {
                log.info("Node already exists: " + relPath);
            }
        } catch (Exception e) {
            log.error(e);
        } finally {
            if (session != null) session.logout();
        }
    }

    /**
     * List all customers
     */
    public void listCustomers(String workspaceName) {
        list(workspaceName, COMPANIES_NODE_NAME, "Customer");
    }

    /**
     * Add a new invoice to the repository
     *
     * @param customerID Customer ID associated with invoice
     */
    public void addInvoice(String workspaceName, String customerID) {
        log.info("Attempting to add invoice: " + customerID);

        Customer customer = new Customer(customerID);
        if (!existsCompany(workspaceName, customer)) {
            log.warn("No such customer: " + customer + " - Adding task aborted - You might want to add a customer first (--help).");
            return;
        }
        Invoice invoice = new Invoice(customer);

        Session session = null;
        try {
            Repository repo = getRepository();
            Credentials credentials = new SimpleCredentials(USERID, PASSWORD);
            session = repo.login(credentials, workspaceName);
            Node rootNode = session.getRootNode();

            Node invoicesNode = getNode(rootNode, INVOICES_NODE_NAME);

            String relPath = invoice.getID();
            if (!invoicesNode.hasNode(relPath)) {
                Node invoiceNode = invoicesNode.addNode(relPath);
                invoiceNode.addMixin("mix:referenceable");
	        log.info("UUID of invoice node: " + invoiceNode.getUUID());
	        log.info("Name of invoice node: " + invoiceNode.getName());
	        log.info("Path of invoice node: " + invoiceNode.getPath());
	        Node customerNode = rootNode.getNode(COMPANIES_NODE_NAME + "/" + customer.getID());
                createBidirectionalAssociation(invoiceNode, "invoice", customerNode, "customer");
                session.checkPermission("/" + INVOICES_NODE_NAME + relPath, "add_node");
                session.save();
            } else {
                log.info("Node already exists: " + relPath);
            }
        } catch (Exception e) {
            log.error(e);
        } finally {
            if (session != null) session.logout();
        }
    }

    /**
     * List all invoices
     */
    public void listInvoices(String workspaceName) {
        list(workspaceName, INVOICES_NODE_NAME, "Invoice");
    }

    /**
     * List all instances of a specific type
     */
    public void list(String workspaceName, String relPath, String typeName) {
        log.info("Attempting to list all instances of type " + typeName);

        Session session = null;
        try {
            Repository repo = getRepository();
            Credentials credentials = new SimpleCredentials(USERID, PASSWORD);
            session = repo.login(credentials, workspaceName);
            Node rootNode = session.getRootNode();

            Node typeNode = rootNode.getNode(relPath);

            if (typeNode != null) {
                NodeIterator nit = typeNode.getNodes();
                log.info("List " + nit.getSize() + " " + typeName + ":");
                while (nit.hasNext()) {
                    Node instanceNode = nit.nextNode();
                    log.info("");
                    log.info("UUID of " + typeName + " node: " + instanceNode.getUUID());
                    log.info("Name of " + typeName + " node: " + instanceNode.getName());
                    log.info("Path of " + typeName + " node: " + instanceNode.getPath());

                    // List properties
                    PropertyIterator propprit = instanceNode.getProperties();
                    while (propprit.hasNext()) {
                        Property prop = propprit.nextProperty();
                        log.info("Property of " + typeName + " node: " + prop.getName() + " , " + prop.getDefinition().isMultiple() + " , " + PropertyType.nameFromValue(prop.getDefinition().getRequiredType()));

                        if (prop.getDefinition().getRequiredType() == PropertyType.REFERENCE) {
                        Node reference = prop.getNode();
                        if (reference != null) {
                            log.info("Reference of " + typeName + " node: " + reference.getName() + " , " + reference.getUUID());
                        }
                        }
                    }

                    // List associations
		    if (instanceNode.hasNode("associations")) {
                        log.info("Node " + instanceNode.getPath() + " has associations:");
                        PropertyIterator prit = instanceNode.getNode("associations").getProperties();
                        while (prit.hasNext()) {
                            Property prop = prit.nextProperty();
                            log.info("Association of " + typeName + " node: " + prop.getName());
                            if (prop.getDefinition().isMultiple()) {
                                log.info("Property has multiple values: " + prop.getName());
                                Value[] values = prop.getValues();
                                for (int i = 0; i < values.length; i++) {
                                    log.info("Value: " + values[i].getString());
                                }
                            }
                        }
                        log.info("");
                    } else {
                        log.info("Node " + instanceNode.getPath() + " has no associations yet.");
                    }

                    // List references
                    PropertyIterator prit = instanceNode.getReferences();
                    if (!prit.hasNext()) log.info("Node (" + instanceNode.getName() + ") has no references.");
                    while (prit.hasNext()) {
                        Property prop = prit.nextProperty();
                        log.info("Reference of " + typeName + " node: " + prop.getName() + ", " + prop.getNode().getPath());
                    }
                }
            } else {
                log.warn("No such node: " + relPath);
            }
        } catch (PathNotFoundException e) {
            log.warn("No such path: " + relPath);
        } catch (Exception e) {
            log.error(e);
        } finally {
            if (session != null) session.logout();
        }
    }

    /**
     * Check if person exists
     */
    public boolean existsOwner(String workspaceName, Owner owner) {
        return exists(workspaceName, PERSONS_NODE_NAME + "/" + owner.getID());
    }

    /**
     * Check if project exists
     */
    public boolean existsProject(String workspaceName, Project project) {
        return exists(workspaceName, PROJECTS_NODE_NAME + "/" + project.getID());
    }

    /**
     * Check if customer exists
     */
    public boolean existsCompany(String workspaceName, Customer customer) {
        return exists(workspaceName, COMPANIES_NODE_NAME + "/" + customer.getID());
    }

    /**
     * Check if instance exists
     */
    public boolean exists(String workspaceName, String relPath) {

        Session session = null;
        try {
            Repository repo = getRepository();
            Credentials credentials = new SimpleCredentials(USERID, PASSWORD);
            session = repo.login(credentials, workspaceName);
            Node rootNode = session.getRootNode();

            Node typeNode = rootNode.getNode(relPath);
        } catch (PathNotFoundException e) {
            log.warn("No such path: " + relPath);
            return false;
        } catch (Exception e) {
            log.error(e);
            return false;
        } finally {
            if (session != null) session.logout();
        }
        return true;
    }

    /**
     *
     */
    private Repository getRepository() throws NamingException {
        return (Repository) context.lookup(REPO_NAME);
    }

    /**
     *
     */
    private void bindRepository(String repoConfig, String repoHomeDir) throws NamingException, RepositoryException {
        log.info("Bind repository (JNDI): " + REPO_NAME);
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.jackrabbit.core.jndi.provider.DummyInitialContextFactory");
        env.put(Context.PROVIDER_URL, "localhost");

        context = new InitialContext(env);
        RegistryHelper.registerRepository(context, REPO_NAME, repoConfig, repoHomeDir, true);
    }

    /**
     *
     */
    private void createBidirectionalAssociation(Node node1, String role1, Node node2, String role2) {
	String associationsRelPath = "associations";

        try {
            node1.setProperty(role2, node2);
        } catch (Exception e) {
            log.error(e);
        }

        Node associations1 = null;
        try {
            associations1 = node1.getNode(associationsRelPath);

            log.info("Associations do already exist: " + node1.getName());
        } catch (PathNotFoundException e) {
            try {
                log.warn("No associations yet: " + node1.getName());
                 associations1 = node1.addNode(associationsRelPath);
                 associations1.addMixin("mix:referenceable");
                log.warn("Associations have been created: " + node1.getName());
            } catch (Exception ee) {
                log.error(ee);
            }
        } catch (Exception e) {
            log.error(e);
        }

        Node associations2 = null;
        try {
            associations2 = node2.getNode(associationsRelPath);

            log.info("Associations do already exist: " + node2.getName());
        } catch (PathNotFoundException e) {
            try {
                log.warn("No associations yet: " + node2.getName());
                 associations2 = node2.addNode(associationsRelPath);
                 associations2.addMixin("mix:referenceable");
                log.warn("Associations have been created: " + node2.getName());
            } catch (Exception ee) {
                log.error(ee);
            }
        } catch (Exception e) {
            log.error(e);
        }

        try {
            updateValues(associations1, role2, node2);
            updateValues(associations2, role1, node1);
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     *
     */
    private void updateValues(Node associations2, String role1, Node associatedNode) throws Exception {
            if (associations2.hasProperty(role1)) {
                Property prop = associations2.getProperty(role1);
                if (associations2.getProperty(role1).getDefinition().isMultiple()) {
                    log.info("Property has multiple values: " + prop.getName());
                    Value[] values = prop.getValues();
                    String[] newValues = new String[values.length + 1];
                    for (int i = 0; i < values.length; i++) {
                        newValues[i] = values[i].getString();
                    }
                    newValues[newValues.length - 1] = associatedNode.getUUID();
                    associations2.setProperty(role1, (String[])null);
                    associations2.setProperty(role1, newValues);
                } else {
                    log.warn("Property has NOT multiple values: " + prop.getName() + ", " + prop.getValue().getString());
                    String[] values = new String[2];
                    values[0] = prop.getValue().getString();
                    values[1] = associatedNode.getUUID();
                    log.warn("Property has been updated: " + role1);
                    associations2.setProperty(role1, (String)null);
                    associations2.setProperty(role1, values);
                }
            } else {
                log.info("Property does not exist yet: " + role1);
                String[] values = new String[1];
                values[0] = associatedNode.getUUID();
                log.warn("Property has been created: " + role1);
                associations2.setProperty(role1, values);
            }
    }

    /**
     *
     */
    private Node getNode(Node rootNode, String name) throws Exception {
        if (!rootNode.hasNode(name)) {
            log.warn("Attempting to create node: " + name);
            return rootNode.addNode(name);
        } else {
            return rootNode.getNode(name);
        }
    }

    /**
     *
     */
    private void setTopic(Node node, String topic) throws Exception {
        node.setProperty("topic", topic);
    }

    /**
     *
     */
    public String getTopicMap(String workspaceName) {
        StringBuffer sb = new StringBuffer("<?xml version=\"1.0\"?>");
	sb.append("<topicMap xmlns=\"http://www.topicmaps.org/xtm/1.0/\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">");

	sb.append("<topic id=\"task\">");
	sb.append("<baseName><baseNameString>Task</baseNameString></baseName>");
	sb.append("</topic>");

	sb.append("<topic id=\"project\">");
	sb.append("<baseName><baseNameString>Project</baseNameString></baseName>");
	sb.append("</topic>");

	sb.append("<topic id=\"person\">");
	sb.append("<baseName><baseNameString>Person</baseNameString></baseName>");
	sb.append("</topic>");

	sb.append("<topic id=\"company\">");
	sb.append("<baseName><baseNameString>Company</baseNameString></baseName>");
	sb.append("</topic>");

	sb.append("<topic id=\"invoice\">");
	sb.append("<baseName><baseNameString>Invoice</baseNameString></baseName>");
	sb.append("</topic>");

	sb.append("</topicMap>");
        return sb.toString();
    }
}