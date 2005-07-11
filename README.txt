

  Enterprise Resource Planning
  ----------------------------

  Requirements
  ------------

    JDK-1.4.2
    Apache-Ant-1.6.2
    Apache-Maven-1.0.2
    Apache-Lenya-1.4-dev

  Getting Started /Step-by-step instructions
  -----------------------------------------
  1) Download Libraries:
  maven
  2) Compile: 
  ant jar
  3) Add sample task: 
  sh run-erp.sh src/repository.xml reposample --add-task hello sample

  Run
  ---

    Usage:      sh run-erp.sh --help
    Add Task:   sh run-erp.sh src/repository.xml repotest --add-task title owner
    List Tasks: sh run-erp.sh src/repository.xml repotest --list-tasks
    ...

  Misc
  ----

  - About Topic Maps:

      http://topicmaps.it.bond.edu.au/docs/6
      http://www.topicmaps.org/xtm/1.0/
