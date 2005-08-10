package org.wyona.erp.lenya.usecases;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

import org.apache.avalon.framework.service.ServiceException;
import org.apache.avalon.framework.service.ServiceManager;
import org.apache.avalon.framework.service.ServiceSelector;
import org.apache.cocoon.components.ContextHelper;
import org.apache.cocoon.components.thread.RunnableManager;
import org.apache.avalon.framework.component.Component;
import org.apache.avalon.framework.component.ComponentManager;
import org.apache.avalon.framework.component.ComponentSelector;
import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.apache.cocoon.environment.Context;
import org.apache.cocoon.environment.ObjectModelHelper;
import org.apache.cocoon.environment.Request;
import org.apache.cocoon.environment.Session;
import org.apache.excalibur.source.Source;
import org.apache.excalibur.source.SourceResolver;

import org.apache.lenya.ac.Identity;

import org.apache.lenya.cms.jcr.LenyaRepository;
import org.apache.lenya.cms.metadata.dublincore.DublinCore;
import org.apache.lenya.cms.publication.Document;
import org.apache.lenya.cms.publication.DocumentIdentityMap;
import org.apache.lenya.cms.publication.DocumentManager;
import org.apache.lenya.cms.publication.PublicationFactory;
import org.apache.lenya.cms.publication.ResourceType;
import org.apache.lenya.cms.usecase.DocumentUsecase;
import org.wyona.erp.ERP;
import org.xml.sax.SAXException;

//import org.wyona.erp.ERP;

/**
 * Usecase to create a new task.
 */
public class NewTask extends DocumentUsecase {

    protected static final String OWNER = "owner";
    
    protected static final String TITLE = "title";
    
    protected static final String ELEMENT_HOME = "home";

    protected static final String ELEMENT_COMPONENT = "component";

    protected static final String ELEMENT_CONF = "configuration";

    protected static final String ATTRIBUTE_SRC = "src";

    protected static final String ATTRIBUTE_role = "role";

    protected static final String LOOKUP_ROLE = "javax.jcr.Repository";

    String workspaceName = "default";

    private String REPO_HOME, REPO_CONFIG, owner,title;

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
    }

    /**
     * @see org.apache.lenya.cms.usecase.AbstractUsecase#doCheckExecutionConditions()
     */
    protected void doCheckExecutionConditions() throws Exception {
        String owner = getParameterAsString(OWNER);
        String title = getParameterAsString(TITLE);
        if (owner.equals("")){
            addErrorMessage("The owner is required.");
        }else if (title.equals("")){
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
        doPreparation();
        resolvePath();
        new ERP(getREPO_CONFIG(), getREPO_HOME()).addTask(workspaceName,
                getTitle(), getOwner());
    }
    
    protected void doPreparation() throws ConfigurationException, SAXException,
            IOException {
        DefaultConfigurationBuilder dcb = new DefaultConfigurationBuilder();
        Map objectModel = ContextHelper.getObjectModel(getContext());
        Context context = ObjectModelHelper.getContext(objectModel);
        String servletContextPath = context.getRealPath("");
        servletContextPath = servletContextPath + "/WEB-INF/cocoon.xconf";
        Configuration config = dcb.buildFromFile(servletContextPath);
        Configuration[] configList = config.getChildren(ELEMENT_COMPONENT);
        String jcrHomeLocation, jcrRepoLocation;

        for (int i = 0; i < configList.length; i++) {
            Configuration configuration = configList[i];
            String value = configuration.getAttribute(ATTRIBUTE_role, null);
            if (null != value) {
                if (LOOKUP_ROLE.equals(value)) {
                    jcrHomeLocation = configList[i].getChild(ELEMENT_HOME)
                            .getAttribute(ATTRIBUTE_SRC);
                    jcrRepoLocation = configList[i].getChild(ELEMENT_CONF)
                            .getAttribute(ATTRIBUTE_SRC);
                    setREPO_HOME(jcrHomeLocation);
                    setREPO_CONFIG(jcrRepoLocation);
                    System.out.println("jcrHomeLocation " + jcrHomeLocation);
                    System.out.println("jcrRepoLocation " + jcrRepoLocation);
                }
            }
        }
    }

    protected void resolvePath() throws ServiceException,
            MalformedURLException, IOException {
        SourceResolver sourceResolver = null;
        Source source = null;
        String tmp = null;
        try {
            sourceResolver = (SourceResolver) this.manager
                    .lookup(org.apache.excalibur.source.SourceResolver.ROLE);
            source = sourceResolver.resolveURI(getREPO_CONFIG());
            tmp = cleanPath(source.getURI());
            setREPO_CONFIG(tmp);
            source = sourceResolver.resolveURI(getREPO_HOME());
            tmp = cleanPath(source.getURI());
            setREPO_HOME(tmp);
        } finally {
            this.manager.release(sourceResolver);
        }
    }

    /**
     * @param uri
     * @return
     */
    private String cleanPath(String uri) {
        String returnString = null;
        if (uri.startsWith("file://")) {
            returnString = uri.substring(6);
        } else if (uri.startsWith("file:/")) {
            returnString = uri.substring(5);
        }
        return returnString;
    }

    /**
     * @return Returns the rEPO_CONFIG.
     */
    public String getREPO_CONFIG() {
        return REPO_CONFIG;
    }

    /**
     * @param repo_config
     *            The rEPO_CONFIG to set.
     */
    public void setREPO_CONFIG(String repo_config) {
        REPO_CONFIG = repo_config;
    }

    /**
     * @return Returns the rEPO_HOME.
     */
    public String getREPO_HOME() {
        return REPO_HOME;
    }

    /**
     * @param repo_home
     *            The rEPO_HOME to set.
     */
    public void setREPO_HOME(String repo_home) {
        REPO_HOME = repo_home;
    }
}
