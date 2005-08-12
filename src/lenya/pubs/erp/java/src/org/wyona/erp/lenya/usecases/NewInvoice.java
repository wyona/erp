package org.wyona.erp.lenya.usecases;

import org.apache.lenya.cms.publication.PublicationFactory;
import org.apache.lenya.cms.site.usecases.SiteUsecase;
import org.wyona.erp.ERP;

//import org.wyona.erp.ERP;

/**
 * Usecase to create a new task.
 */
public class NewInvoice extends SiteUsecase {

    protected static final String INVOICE = "invoice";

    private String invoice = "";

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
        setInvoice(getParameterAsString(INVOICE));
        if (getInvoice().equals("")) {
            addErrorMessage("The invoice is required.");
        } 
        super.doCheckExecutionConditions();
    }

    /**
     * @see org.apache.lenya.cms.usecase.AbstractUsecase#doExecute()
     */
    protected void doExecute() throws Exception {
        super.doExecute();
        new ERP(repoBean.getRepoConfig(), repoBean.getRepoHome()).addCustomer(repoBean
                .getWorkspaceName(), getInvoice(), getName());
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
     * @return Returns the invoice.
     */
    public String getInvoice() {
        return invoice;
    }

    /**
     * @param invoice
     *            The invoice to set.
     */
    public void setInvoice(String id) {
        this.invoice = id;
    }
}
