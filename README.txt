
 W Y O N A  -  MAY  - E R P
 ==========================


 0.) Contents
 ------------

 1.) Preparation
 2.) Building
 3.) Running
 4.) Miscellaneous



 1.) Preparation
 ---------------

 UNIX-like Operating Systems:

   1) Add the Maven bin directory to your path, e.g. export PATH=/home/wyona/src/erp/trunk/tools/maven-2.0.x_lcrXXX/bin:$PATH
   2) Make sure that JAVA_HOME is set

 Windows:

   1) Add the Maven bin directory to the PATH of the user variables, e.g. D:\src\erp\trunk\tools\maven-2.0.x_lcr330863\bin;%PATH%
   2) Add the Maven directory to the M2_HOME user variable, e.g. D:\src\erp\trunk\tools\maven-2.0.x_lcr330863

   Also see at the bottom of http://maven.apache.org/download.html



 2.) Building
 ------------

 1) Change directory to "may"
 2) Compile Java classes: mvn compile (Offline mode: mvn -o compile)
 3) Generate documentation: mvn site (Offline mode: mvn -o site)
 3.1) View documentation: may/target/site/index.html



 3.) Running
 -----------

 1) Run the Command Line Interface: java --classpath target/classes org.wyona.may.App
 2) Run the Web Server: ./startup.sh
 3) Run tests: mvn test



 4.) Miscellaneous
 -----------------

 Installing Apache 2.1.9-beta on Ubuntu Linux
 --------------------------------------------

 1) apt-get install gcc
 2) apt-get install g++
 3) apt-get install libc6-dev
 4) apt-get install make
 5) ./configure --prefix=/home/USERNAME/build/httpd-2.1.9-beta --enable-proxy --enable-proxy-http --enable-proxy-balancer --enable-status --enable-proxy-ajp
 6) http://127.0.0.1:8080/balancer-manager
 7) http://httpd.apache.org/docs/2.1/new_features_2_2.html
    http://httpd.apache.org/docs/2.1/mod/mod_proxy_balancer.html
    http://httpd.apache.org/docs/2.1/mod/mod_proxy.html#access
    http://tomcat.apache.org/tomcat-5.0-doc/balancer-howto.html
    http://tomcat.apache.org/faq/connectors.html#vs
    http://tomcat.apache.org/connectors-doc/howto/apache.html

  Building and installing mod_jk
  ------------------------------

  1) cd jakarta-tomcat-connectors-1.2.15-src/jk/native
  2) ./configure --with-apxs=/home/USERNAME/build/httpd-2.1.9-beta/bin/apxs
  3) make
  4) make install
  5) vi /home/USERNAME/build/httpd-2.1.9-beta/conf/httpd.conf (LoadModule jk_module modules/mod_jk.so)
