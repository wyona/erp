#!/bin/sh

MAVEN_REPO=~/.maven/repository

CLASSPATH=build/lib/erp-cli.jar
#CLASSPATH=build/classes
CLASSPATH=$CLASSPATH:$MAVEN_REPO/jsr170/jars/jcr-1.0.jar
CLASSPATH=$CLASSPATH:$MAVEN_REPO/log4j/jars/log4j-1.2.9.jar

CLASSPATH=$CLASSPATH:$MAVEN_REPO/springframework/jars/spring-1.2.4.jar
CLASSPATH=$CLASSPATH:$MAVEN_REPO/commons-logging/jars/commons-logging-1.0.4.jar
CLASSPATH=$CLASSPATH:$MAVEN_REPO/commons-collections/jars/commons-collections-3.1.jar
CLASSPATH=$CLASSPATH:$MAVEN_REPO/xerces/jars/xerces-2.4.0.jar
CLASSPATH=$CLASSPATH:$MAVEN_REPO/xml-apis/jars/xml-apis-1.0.b2.jar
CLASSPATH=$CLASSPATH:$MAVEN_REPO/concurrent/jars/concurrent-1.3.4.jar
CLASSPATH=$CLASSPATH:$MAVEN_REPO/lucene/jars/lucene-1.4.3.jar
CLASSPATH=$CLASSPATH:src/spring
CLASSPATH=$CLASSPATH:lib

#
#for Jackrabbit
#
#CLASSPATH=$CLASSPATH:$MAVEN_REPO/jackrabbit/jars/jackrabbit-api-1.0-dev-LCR209797.jar
#CLASSPATH=$CLASSPATH:$MAVEN_REPO/jackrabbit/jars/jackrabbit-commons-1.0-dev-LCR209797.jar
#CLASSPATH=$CLASSPATH:$MAVEN_REPO/jackrabbit/jars/jackrabbit-core-1.0-dev-LCR209797.jar
#
#java -cp $CLASSPATH -Djava.security.auth.login.config==jaas.config org.wyona.erp.cli.CLI $*

#
#for jeceira
#
CLASSPATH=$CLASSPATH:src/jeceira
CLASSPATH=$CLASSPATH:$MAVEN_REPO/jeceira/jars/jeceira-0.1.1-maintenance20050909T0933.jar
CLASSPATH=$CLASSPATH:lib/doka-0.1.jar
CLASSPATH=$CLASSPATH:lib/commons-0.1.jar
CLASSPATH=$CLASSPATH:$MAVEN_REPO/commons-lang/jars/commons-lang-2.0.jar
CLASSPATH=$CLASSPATH:lib/hsqldb.jar
#CLASSPATH=$CLASSPATH:$MAVEN_REPO/hsqldb/jars/hsqldb-1.8.0.1.jar 
CLASSPATH=$CLASSPATH:lib/jug-asl-2.0rc4.jar
CLASSPATH=$CLASSPATH:lib/jug-native/

java -cp $CLASSPATH org.wyona.erp.cli.CLI $*
