package org.wyona.erp;

import org.apache.jackrabbit.core.jndi.RegistryHelper;
//import org.apache.jackrabbit.core.value.StringValue;

import javax.jcr.Credentials;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.util.Hashtable;

import org.apache.log4j.Category;

import org.wyona.erp.types.Owner;
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
            log.warn("No such owner: " + owner + " - Adding task aborted.");
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
                //taskNode.setProperty("title", new StringValue(title));
                taskNode.setProperty("owner", owner.getID());
                if (project != null) {
                    taskNode.setProperty("project", project.getID());
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
     */
    public void addProject(String workspaceName, String id, String title) {
        log.info("Attempting to add project: " + id + ", " + title);

        Project project = new Project(id, title);

        Session session = null;
        try {
            Repository repo = getRepository();
            Credentials credentials = new SimpleCredentials(USERID, PASSWORD);
            session = repo.login(credentials, workspaceName);
            Node rootNode = session.getRootNode();

            Node projectsNode = null;
            if (!rootNode.hasNode("projects")) {
                projectsNode = rootNode.addNode("projects");
            } else {
                projectsNode = rootNode.getNode("projects");
            }

            String relPath = id;
            if (!projectsNode.hasNode(relPath)) {
                Node projectNode = projectsNode.addNode(relPath);
                projectNode.addMixin("mix:referenceable");
                projectNode.setProperty("title", title);
	        log.info("UUID of project node: " + projectNode.getUUID());
	        log.info("Name of project node: " + projectNode.getName());
	        log.info("Path of project node: " + projectNode.getPath());
                session.checkPermission("/projects" + relPath, "add_node");
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
     * List all projects
     */
    public void listProjects(String workspaceName) {
        list(workspaceName, "projects", "Project");
    }

    /**
     * Add a new owner to the repository
     *
     * @param id ID of owner, e.g. michi
     * @param name Name of owner, e.g. Michael Wechner
     */
    public void addOwner(String workspaceName, String id, String name, String email) {
        log.info("Attempting to add owner: " + id + ", " + name + ", " + email);

        Owner owner = new Owner(id, name, email);

        Session session = null;
        try {
            Repository repo = getRepository();
            Credentials credentials = new SimpleCredentials(USERID, PASSWORD);
            session = repo.login(credentials, workspaceName);
            Node rootNode = session.getRootNode();

            Node ownersNode = null;
            if (!rootNode.hasNode("owners")) {
                ownersNode = rootNode.addNode("owners");
            } else {
                ownersNode = rootNode.getNode("owners");
            }

            String relPath = id;
            if (!ownersNode.hasNode(relPath)) {
                Node ownerNode = ownersNode.addNode(relPath);
                ownerNode.addMixin("mix:referenceable");
                ownerNode.setProperty("name", name);
                ownerNode.setProperty("email", email);
	        log.info("UUID of owner node: " + ownerNode.getUUID());
	        log.info("Name of owner node: " + ownerNode.getName());
	        log.info("Path of owner node: " + ownerNode.getPath());
                session.checkPermission("/owners" + relPath, "add_node");
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
     * List all owners
     */
    public void listOwners(String workspaceName) {
        list(workspaceName, "owners", "Owner");
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
     * Check if owner exists
     */
    public boolean existsOwner(String workspaceName, Owner owner) {
        return exists(workspaceName, "owners/" + owner.getID());
    }

    /**
     * Check if project exists
     */
    public boolean existsProject(String workspaceName, Project project) {
        return exists(workspaceName, "projects/" + project.getID());
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
}
