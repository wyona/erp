/*
 * Created on Aug 11, 2005
 *
 */
package org.wyona.erp.lenya.usecases;

import java.io.Serializable;
import java.util.Map;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.apache.avalon.framework.service.ServiceManager;
import org.apache.cocoon.components.ContextHelper;
import org.apache.cocoon.environment.Context;
import org.apache.cocoon.environment.ObjectModelHelper;
import org.apache.excalibur.source.Source;
import org.apache.excalibur.source.SourceResolver;

/**
 * @author thorsten
 *  
 */
public class JcrRepBean implements Serializable {
    protected static final String ELEMENT_HOME = "home";

    protected static final String ELEMENT_COMPONENT = "component";

    protected static final String ELEMENT_CONF = "configuration";

    protected static final String ATTRIBUTE_SRC = "src";

    protected static final String ATTRIBUTE_ROLE = "role";

    protected static final String LOOKUP_ROLE = "javax.jcr.Repository";
    
    protected static final String COCOON_XCONF = "/WEB-INF/cocoon.xconf";
    
    private String workspaceName = "default";

    private String repoHome, repoConfig = "";

    /**
     * @return Returns the rEPO_CONFIG.
     */
    public String getRepoConfig() {
        return repoConfig;
    }

    /**
     * @param repo_config
     *            The rEPO_CONFIG to set.
     */
    public void setRepoConfig(String repo_config) {
        repoConfig = repo_config;
    }

    /**
     * @return Returns the rEPO_HOME.
     */
    public String getRepoHome() {
        return repoHome;
    }

    /**
     * @param repo_home
     *            The rEPO_HOME to set.
     */
    public void setRepoHome(String repo_home) {
        repoHome = repo_home;
    }

    protected void pepareBean(org.apache.avalon.framework.context.Context getContext,ServiceManager manager){
        DefaultConfigurationBuilder dcb = new DefaultConfigurationBuilder();
        Map objectModel = ContextHelper.getObjectModel(getContext);
        Context context = ObjectModelHelper.getContext(objectModel);
        String servletContextPath = context.getRealPath("");
        servletContextPath = servletContextPath + COCOON_XCONF;
        try {
            Configuration config = dcb.buildFromFile(servletContextPath);
            Configuration[] configList = config.getChildren(ELEMENT_COMPONENT);
            String jcrHomeLocation, jcrRepoLocation;

            for (int i = 0; i < configList.length; i++) {
                Configuration configuration = configList[i];
                String value = configuration.getAttribute(ATTRIBUTE_ROLE, null);
                if (null != value) {
                    if (LOOKUP_ROLE.equals(value)) {
                        jcrHomeLocation = configList[i].getChild(ELEMENT_HOME)
                                .getAttribute(ATTRIBUTE_SRC);
                        jcrRepoLocation = configList[i].getChild(ELEMENT_CONF)
                                .getAttribute(ATTRIBUTE_SRC);
                        setRepoHome(jcrHomeLocation);
                        setRepoConfig(jcrRepoLocation);
                    }
                }
            }
            resolvePath(manager);  
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
                
    }

    protected void resolvePath(ServiceManager manager) {
        SourceResolver sourceResolver = null;
        Source source = null;
        String tmp = null;
        try {
            sourceResolver = (SourceResolver) manager
                    .lookup(org.apache.excalibur.source.SourceResolver.ROLE);
            source = sourceResolver.resolveURI(getRepoConfig());
            tmp = cleanPath(source.getURI());
            setRepoConfig(tmp);
            source = sourceResolver.resolveURI(getRepoHome());
            tmp = cleanPath(source.getURI());
            setRepoHome(tmp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            manager.release(sourceResolver);
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
     * @return Returns the workspaceName.
     */
    public String getWorkspaceName() {
        return workspaceName;
    }
    /**
     * @param workspaceName The workspaceName to set.
     */
    public void setWorkspaceName(String workspaceName) {
        this.workspaceName = workspaceName;
    }
}
