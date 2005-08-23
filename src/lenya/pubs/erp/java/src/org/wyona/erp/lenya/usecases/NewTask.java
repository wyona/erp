package org.wyona.erp.lenya.usecases;

import java.util.Hashtable;

import org.apache.lenya.cms.site.usecases.SiteUsecase;
import org.wyona.erp.ERP;
import org.wyona.erp.exception.ERPException;

//import org.wyona.erp.ERP;

/**
 * Usecase to create a new task.
 */
public class NewTask extends SiteUsecase {

    protected static final String OWNER = "owner";

    protected static final String TITLE = "title";

    protected static final String PROJECT = "project";

    protected static final String COMPONENT = "component";

    private TaskBean taskBean = new TaskBean();

    private JcrRepBean repoBean = new JcrRepBean();

    /**
     * @see org.apache.lenya.cms.usecase.AbstractUsecase#initParameters()
     */
    protected void initParameters() {
        super.initParameters();
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
        getTaskBean().setOwner(getParameterAsString(OWNER));
        getTaskBean().setTitle(getParameterAsString(TITLE));
        getTaskBean().setProject(getParameterAsString(PROJECT));
        getTaskBean().setComponent(getParameterAsString(COMPONENT));
        if (getTaskBean().getOwner().equals("")) {
            addErrorMessage("The owner is required.");
        } else if (getTaskBean().getTitle().equals("")) {
            addErrorMessage("The title is required.");
        }

        super.doCheckExecutionConditions();
    }

    /**
     * @see org.apache.lenya.cms.usecase.AbstractUsecase#doExecute()
     */
    protected void doExecute() throws Exception {
        super.doExecute();
        new ERP(repoBean.getRepoConfig(), repoBean.getRepoHome()).addTask(
                repoBean.getWorkspaceName(), getTaskBean().getTitle(),
                getTaskBean().getOwner());
    }

    protected void doPreparation() {
        try {
            repoBean.pepareBean(getContext(), this.manager);
            Hashtable listBean;
            try {
                listBean = new ERP(repoBean.getRepoConfig(), repoBean.getRepoHome()).personsBean(repoBean.getWorkspaceName());
                setParameter("listBean",listBean);
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            } catch (ERPException e) {
                addErrorMessage(e.getMessage());
            }
            
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
     * @return Returns the taskBean.
     */
    public TaskBean getTaskBean() {
        return taskBean;
    }

    /**
     * @param taskBean
     *            The taskBean to set.
     */
    public void setTaskBean(TaskBean taskBean) {
        this.taskBean = taskBean;
    }
}
