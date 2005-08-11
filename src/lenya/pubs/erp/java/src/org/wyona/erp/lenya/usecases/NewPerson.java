package org.wyona.erp.lenya.usecases;

import org.apache.lenya.cms.publication.PublicationFactory;
import org.apache.lenya.cms.site.usecases.SiteUsecase;
import org.wyona.erp.ERP;

//import org.wyona.erp.ERP;

/**
 * Usecase to create a new task.
 */
public class NewPerson extends SiteUsecase {

    protected static final String ID = "id";

    protected static final String NAME = "name";

    protected static final String EMAIL = "eMail";

    private String id, name, eMail = "";

    private JcrRepBean jrb = new JcrRepBean();

    /**
     * @see org.apache.lenya.cms.usecase.AbstractUsecase#initParameters()
     */
    protected void initParameters() {
        super.initParameters();
        PublicationFactory factory = PublicationFactory
                .getInstance(getLogger());
        try {
            doPreparation();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @see org.apache.lenya.cms.usecase.AbstractUsecase#doCheckExecutionConditions()
     */
    protected void doCheckExecutionConditions() throws Exception {
        setId(getParameterAsString(ID));
        setName(getParameterAsString(NAME));
        setEMail(getParameterAsString(EMAIL));
        if (getId().equals("")) {
            addErrorMessage("The id is required.");
        } else if (getName().equals("")) {
            addErrorMessage("The name is required.");
        } else if (getEMail().equals("")) {
            addErrorMessage("The eMail is required.");
        }
        super.doCheckExecutionConditions();
    }

    /**
     * @see org.apache.lenya.cms.usecase.AbstractUsecase#doExecute()
     */
    protected void doExecute() throws Exception {
        super.doExecute();
        new ERP(jrb.getRepoConfig(), jrb.getRepoHome()).addPerson(jrb
                .getWorkspaceName(), getId(), getName(), getEMail());
    }

    protected void doPreparation() {
        try {
            jrb.pepareBean(getContext(), this.manager);
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
     * @param jrb
     *            The jrb to set.
     */
    public void setJrb(JcrRepBean jrb) {
        this.jrb = jrb;
    }

    /**
     * @return Returns the eMail.
     */
    public String getEMail() {
        return eMail;
    }

    /**
     * @param mail
     *            The eMail to set.
     */
    public void setEMail(String mail) {
        eMail = mail;
    }

    /**
     * @return Returns the id.
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            The id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
}
