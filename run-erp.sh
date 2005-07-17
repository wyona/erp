#!/bin/sh

MAVEN_REPO=~/.maven/repository

CLASSPATH=build/classes
CLASSPATH=$CLASSPATH:$MAVEN_REPO/jsr170/jars/jcr-1.0.jar
#CLASSPATH=$CLASSPATH:lib/jcr-0.16.4.1.jar
CLASSPATH=$CLASSPATH:$MAVEN_REPO/log4j/jars/log4j-1.2.9.jar
CLASSPATH=$CLASSPATH:$MAVEN_REPO/jackrabbit/jars/jackrabbit-api-1.0-dev-LCR209797.jar
CLASSPATH=$CLASSPATH:$MAVEN_REPO/jackrabbit/jars/jackrabbit-commons-1.0-dev-LCR209797.jar
CLASSPATH=$CLASSPATH:$MAVEN_REPO/jackrabbit/jars/jackrabbit-core-1.0-dev-LCR209797.jar
#CLASSPATH=$CLASSPATH:lib/jackrabbit-0.16.4.1-dev.jar
CLASSPATH=$CLASSPATH:$MAVEN_REPO/commons-collections/jars/commons-collections-3.1.jar
CLASSPATH=$CLASSPATH:$MAVEN_REPO/xerces/jars/xerces-2.4.0.jar
CLASSPATH=$CLASSPATH:$MAVEN_REPO/xml-apis/jars/xml-apis-1.0.b2.jar
CLASSPATH=$CLASSPATH:$MAVEN_REPO/concurrent/jars/concurrent-1.3.4.jar
CLASSPATH=$CLASSPATH:$MAVEN_REPO/lucene/jars/lucene-1.4.3.jar
CLASSPATH=$CLASSPATH:lib

java -cp $CLASSPATH -Djava.security.auth.login.config==jaas.config org.wyona.erp.CLI $*
