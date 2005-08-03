

  Enterprise Resource Planning
  ----------------------------

  Requirements Command Line
  -------------------------

    JDK-1.4.2
    Apache-Ant-1.6.2
    Apache-Maven-1.0.2

  Requirements Webinterface
  -------------------------

    Apache-Lenya-1.4-dev/trunk (Apache-Cocoon-2.1.8-dev/BRANCH_2_1_X)


  Getting Started (Step-by-step instructions)
  -------------------------------------------

  1) Download Libraries:
    maven
  2) Compile: 
    ant jar
  3) Add sample task: 
    sh run-erp.sh src/repository.xml build/repotest --add-task hello sample
  4) Usage:
    sh run-erp.sh --help

Note: 
If you see "Exception in thread "main" java.lang.NoClassDefFoundError: org/wyona/erp/CLI" when
running above command (4), then repeat step 1/2.

  Getting Started with Lenya
  --------------------------

    Add the ERP publication to Lenya's local.build.properties:

      pubs.root.dirs=src/webapp/lenya/pubs:$HOME/erp/trunk/src/lenya/pubs/erp

    After building Lenya one needs to re-configure the jaas, home and configuration of JCR within cocoon.xconf

    <component class="org.apache.cocoon.jcr.JackrabbitRepository" logger="jcr" role="javax.jcr.Repository">
      <credentials login="anonymous" password=""/>
      <jaas src="file:/home/michi/src/wyona-svn/public/erp/trunk/jaas.config"/>
      <home src="file:/home/michi/src/wyona-svn/public/erp/trunk/build/repotest"/>
      <!-- <home src="context://samples/repotest"/> -->
      <configuration src="file:/home/michi/src/wyona-svn/public/erp/trunk/src/repository.xml"/>
      <!-- <configuration src="context://samples/repository.xml"/> -->
    </component>

    TODO: Overwrite this configration with XPatch

    Also one might have to configure the source factory of JCR within cocoon.xconf
    
    <component-instance class="org.apache.cocoon.jcr.source.JCRSourceFactory" name="jcr">
      <folder-node new-file="nt:file" new-folder="nt:folder" type="rep:root"/>
      <folder-node new-file="nt:file" new-folder="nt:unstructured" type="nt:unstructured"/>
      <!--
      <folder-node type="nt:folder" new-file="nt:file"/>
      -->
      <file-node content-path="jcr:content" content-type="nt:resource" type="nt:file"/>
      <file-node content-ref="jcr:content" type="nt:linkedFile"/>
    </component-instance>

  Misc
  ----

  - About Topic Maps:

      http://topicmaps.it.bond.edu.au/docs/6
      http://www.topicmaps.org/xtm/1.0/
