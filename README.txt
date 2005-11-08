
 W Y O N A  -  E R P
 ===================


 Contents
 --------

 1) Preparation
 2) Building
 3) Running


 Preparation
 -----------

 UNIX-like Operating Systems:

   1) Add the Maven bin directory to your path, e.g. export PATH=/home/wyona/src/erp/trunk/tools/maven-2.0.x_lcrXXX/bin:$PATH
   2) Make sure that JAVA_HOME is set

 Windows:

   1) Add the Maven bin directory to the PATH of the user variables, e.g. D:\src\erp\trunk\tools\maven-2.0.x_lcr330863\bin;%PATH%
   2) Add the Maven directory to the M2_HOME user variable, e.g. D:\src\erp\trunk\tools\maven-2.0.x_lcr330863

   Also see at the bottom of http://maven.apache.org/download.html


 Building
 --------

 1) Change directory to "may"
 2) Compile Java classes: mvn compile
 3) Generate documentation: mvn site
 3.1) View documentation: may/target/site/index.html


 Running
 -------

 1) Run the Command Line Interface: java --classpath target/classes org.wyona.may.App
 2) Run the Web Server: ./startup.sh
 3) Run tests: mvn test
