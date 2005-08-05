:: Intializing script for ERP / WIN32
:: version 1.a

:: !! This is just a temporary Batchfile for the Classpath.

:: 05.08.05, Oliver Schalch

@echo off

:: set maven repository
set MAVEN_REPO=C:\tmp\.maven\repository

:: set CLASSPATH
set CP=D:\lenyatest_1.4\erp\trunk\build\classes;%MAVEN_REPO%\jsr170\jars\jcr-1.0.jar;%MAVEN_REPO%\log4j\jars\log4j-1.2.9.jar;%MAVEN_REPO%\jackrabbit\jars\jackrabbit-api-1.0-dev-LCR209797.jar;%MAVEN_REPO%\xerces\jars\xerces-2.4.0.jar;%MAVEN_REPO%\jackrabbit\jars\jackrabbit-commons-1.0-dev-LCR209797.jar;%MAVEN_REPO%\jackrabbit\jars\jackrabbit-core-1.0-dev-LCR209797.jar;%MAVEN_REPO%\commons-collections\jars\commons-collections-3.1.jar;%MAVEN_REPO%\xml-apis\jars\xml-apis-1.0.b2.jar;%MAVEN_REPO%\concurrent\jars\concurrent-1.3.4.jar;%MAVEN_REPO%\lucene\jars\lucene-1.4.3.jar;D:\lenyatest_1.4\erp\trunk\lib