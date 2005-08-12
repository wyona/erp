package org.wyona.erp.lenya.usecases;

import org.apache.lenya.cms.publication.PublicationFactory;
import org.apache.lenya.cms.site.usecases.SiteUsecase;
import org.wyona.erp.ERP;

//import org.wyona.erp.ERP;

/**
 * Usecase to create a new task.
 */
public class NewProject extends SiteUsecase {

    protected static final String ID = "id";

    protected static final String TITLE = "title";

    protected static final String CUSTOMER = "customer";

    private String id, title, customer = "";

    private JcrRepBean repoBean = new JcrRepBean();

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
        setTitle(getParameterAsString(TITLE));
        setCustomer(getParameterAsString(CUSTOMER));
        if (getId().equals("")) {
            addErrorMessage("The id is required.");
        } else if (getTitle().equals("")) {
            addErrorMessage("The title is required.");
        } 
        super.doCheckExecutionConditions();
    }

    /**
     * @see org.apache.lenya.cms.usecase.AbstractUsecase#doExecute()
     */
    protected void doExecute() throws Exception {
        super.doExecute();
        new ERP(repoBean.getRepoConfig(), repoBean.getRepoHome()).addPerson(repoBean
                .getWorkspaceName(), getId(), getTitle(), getCustomer());
    }

    protected void doPreparation() {
        try {
            repoBean.pepareBean(getContext(), this.manager);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return Returns the repoBean.
     */
    public JcrRepBean getRepoBean() {
        return repoBean;
    }

    /**
     * @param repoBean
     *            The repoBean to set.
     */
    public void setRepoBean(JcrRepBean jrb) {
        this.repoBean = jrb;
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
     * @return Returns the customer.
     */
    public String getCustomer() {
        return customer;
    }
    /**
     * @param customer The customer to set.
     */
    public void setCustomer(String customer) {
        this.customer = customer;
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
}
