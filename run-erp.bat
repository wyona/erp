:: Intializing script for ERP / WIN32
:: version 1.a

:: 

:: 05.08.05, Oliver Schalch

@echo off

:: JAVA_BIN, JAVA_HOME

set JAVA_BIN=C:\j2sdk1.4.2_08\bin
set JAVA_HOME=C:\j2sdk1.4.2_08

:: set maven repository
set MAVEN_REPO=D:\lenyatest_1.4\src\.maven\repository

:: set CLASSPATH (remember that CLASSPATH dont like spaces)

:: need changes to your system
set CP=D:\lenyatest_1.4\src\erp-trunk\build\lib\erp-cli.jar

set CP=%CP%;%MAVEN_REPO%\jsr170\jars\jcr-1.0.jar
set CP=%CP%;%MAVEN_REPO%\log4j\jars\log4j-1.2.9.jar
set CP=%CP%;%MAVEN_REPO%\jackrabbit\jars\jackrabbit-api-1.0-dev-LCR209797.jar
set CP=%CP%;%MAVEN_REPO%\xerces\jars\xerces-2.4.0.jar
set CP=%CP%;%MAVEN_REPO%\jackrabbit\jars\jackrabbit-commons-1.0-dev-LCR209797.jar
set CP=%CP%;%MAVEN_REPO%\jackrabbit\jars\jackrabbit-core-1.0-dev-LCR209797.jar
set CP=%CP%;%MAVEN_REPO%\commons-collections\jars\commons-collections-3.1.jar
set CP=%CP%;%MAVEN_REPO%\xml-apis\jars\xml-apis-1.0.b2.jar
set CP=%CP%;%MAVEN_REPO%\concurrent\jars\concurrent-1.3.4.jar
set CP=%CP%;%MAVEN_REPO%\lucene\jars\lucene-1.4.3.jar

:: need changes to your system
set CP=%CP%;D:\lenyatest_1.4\src\erp-trunk\lib


java -cp %CP% -Djava.security.auth.login.config==jaas.config org.wyona.erp.cli.CLI %1 %2 %3 %4 %5 %6 %7 %8 %9
