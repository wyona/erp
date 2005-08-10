

  Enterprise Resource Planning (CLI/Win32)
  ------------------------------------

  Requirements Command Line
  -------------------------

    JDK-1.4.2
    Apache-Ant-1.6.2 (http://ant.apache.org, Binary Version)
    Apache-Maven-1.0.2 (http://maven.apache.org)

  Requirements Webinterface
  -------------------------

    Apache-Lenya-1.4-dev/trunk (Apache-Cocoon-2.1.8-dev/BRANCH_2_1_X)


  Getting Started (Step-by-step instructions)
  -------------------------------------------

  1) Download Maven.
	set MAVEN_HOME to maven directory.

	Execute maven where this readme is:
	path/to/bin/maven

	Maven will download some libraries and copy them into a repository
	location.
	It will copy to C:\Documents and Settings\{username}\.maven

	{TODO}
	Install Maven more intelligent.

	
  2) Compile: 
	path/to/bin/ant.bat jar

	2.1) Now you should manualy move .maven into a directory which meets good the classpath
		 which {TODO}
		 make maven download libraries to correct location


  3) Add sample person and task: 
	run-erp.bat src\repository.xml build\repotest --add-person sample sample sample@foo.bar
        run-erp.bat src\repository.xml build\repotest --add-task hello sample
  4) Usage:
        run-erp.bat --help