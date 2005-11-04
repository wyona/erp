

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

   See at the bottom of http://maven.apache.org/download.html


 Building
 --------

 1) Change directory to "may"
 2) Compile Java classes: mvn compile
 3) Generate documentation: mvn site


 Running
 -------

 1) Run tests: mvn test
 2) View documentation: may/target/site/index.html
