

  Enterprise Resource Planning
  ============================


  0. Contents
  --------

    1) Requirements Command Line
    2) Getting Started with the Command Line
    3) Requirements Webinterface (Apache Lenya)
    4) Building the ERP Lenya publication
    5) Misc
    6) Acknowledgements



  1. Requirements Command Line
  ----------------------------

    JDK-1.4.2
    Apache-Ant-1.6.2
    Apache-Maven-1.0.2



  2. Getting Started with the Command Line
  ----------------------------------------

  1) Download Libraries:
     maven

  2) Build ERP:
     ant jar

  3) Use ERP:
     a) Add sample person
        Linux/UNIX:  sh run-erp.sh src/repository.xml build/repotest --add-person alice Alice alice@wyona.org
        Windows:     run-erp.bat src/repository.xml build/repotest --add-person ID NAME E-MAIL

     b) Add sample task: 
        Linux/UNIX:  sh run-erp.sh src/repository.xml build/repotest --add-task my_first_task alice
        Windows:     run-erp.bat src/repository.xml build/repotest --add-task TITLE OWNER [PROJECT]

     c) Usage:
        Linux/UNIX:  sh run-erp.sh --help
        Windows:     run-erp.bat --help



  3. Requirements Webinterface
  ----------------------------

    Apache-Lenya-1.4-dev/trunk (Apache-Cocoon-2.1.8-dev/BRANCH_2_1_X)


  4. Building the ERP Lenya publication
  -------------------------------------
  I. Prepare ERP
  --------------------------
  1) Download Libraries:
     maven
  2) Since we will use maven - http://maven.apache.org/reference/properties.html - we need to create a
     file called build.properties. That is similar to a local.build.properties from ant.
     Copy project.properties onto build.properties
  3) Edit "ERP Properties" and "ERP JCR Properties" to match your local settings.
  4) Add sample person (not yet in lenya)
    sh run-erp.sh src/repository.xml build/repotest --add-person ID NAME E-MAIL
  5) Deploy erp to lenya
    maven erp:deploy
  
  II. Prepare Lenya
  --------------------------
  1) Add the ERP publication to Lenya's local.build.properties:
    pubs.root.dirs=src/webapp/lenya/pubs:$HOME/erp/trunk/src/lenya/pubs/erp
  2) Build lenya:
    cd $LENYA_TRUNK;./build.sh
    
  III. Start working
  --------------------------
  1) *Optional* If you want to use the ERP-Rep instead of the LENYA-Rep, patch the xconf.
    maven erp:patch-cocoon.xconf
  2) Now you can run lenya from within the $ERP_HOME by:
    maven lenya:run
  3) Add a task via Archive->new->Task


  5. Misc
  -------

  - About Topic Maps:

      http://topicmaps.it.bond.edu.au/docs/6
      http://www.topicmaps.org/xtm/1.0/


  6. Acknowledgements
  -------------------

  - The employees of Wyona
