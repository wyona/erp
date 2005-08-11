package org.wyona.erp.lenya.usecases;


import org.apache.lenya.cms.publication.PublicationFactory;
import org.apache.lenya.cms.site.usecases.SiteUsecase;
import org.wyona.erp.ERP;

//import org.wyona.erp.ERP;

/**
 * Usecase to create a new task.
 */
public class NewTask extends SiteUsecase {

    protected static final String OWNER = "owner";
    
    protected static final String TITLE = "title";
    
    protected static final String PROJECT = "project";
    
    protected static final String COMPONENT = "component";
    

    String workspaceName = "default";

    private String owner,title,project,component="";
    
    private JcrRepBean jrb= null;

    /**
     * @return Returns the owner.
     */
    public String getOwner() {
        return owner;
    }
    
    /**
     * @param owner The owner to set.
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }
    
    /**
     * @return Returns the title.
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * @see org.apache.lenya.cms.usecase.AbstractUsecase#initParameters()
     */
    protected void initParameters() {
        super.initParameters();
        PublicationFactory factory = PublicationFactory
                .getInstance(getLogger());
        if (null == jrb){
            setJrb(new JcrRepBean());
            try {
                doPreparation();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
        }
    }

    /**
     * @see org.apache.lenya.cms.usecase.AbstractUsecase#doCheckExecutionConditions()
     */
    protected void doCheckExecutionConditions() throws Exception {
        setOwner(getParameterAsString(OWNER));
        setTitle(getParameterAsString(TITLE));
        setProject(getParameterAsString(PROJECT));
        setComponent(getParameterAsString(COMPONENT));
        if (getOwner().equals("")){
            addErrorMessage("The owner is required.");
        }else if (getTitle().equals("")){
            addErrorMessage("The title is required.");
        }else{
            setOwner(owner);
            setTitle(title);
        }

        super.doCheckExecutionConditions();
    }

    /**
     * @see org.apache.lenya.cms.usecase.AbstractUsecase#doExecute()
     */
    protected void doExecute() throws Exception {
        super.doExecute();
        new ERP(jrb.getRepoConfig(), jrb.getRepoHome()).addTask(workspaceName,
                getTitle(), getOwner());
    }
    
    protected void doPreparation() {
        try {
            jrb.pepareBean(getContext(),this.manager);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return Returns the jrb.
     */
    public JcrRepBean getJrb() {
        return jrb;
    }
    /**
     * @param jrb The jrb to set.
     */
    public void setJrb(JcrRepBean jrb) {
        this.jrb = jrb;
    }
    /**
     * @return Returns the component.
     */
    public String getComponent() {
        return component;
    }
    /**
     * @param component The component to set.
     */
    public void setComponent(String component) {
        this.component = component;
    }
    /**
     * @return Returns the project.
     */
    public String getProject() {
        return project;
    }
    /**
     * @param project The project to set.
     */
    public void setProject(String project) {
        this.project = project;
    }
}
