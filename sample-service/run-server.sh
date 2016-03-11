#!/bin/bash
MAVEN_OPTS="-Xmx512m -XX:MaxPermSize=128m -DskipTests=true"
mvn clean tomcat7:run -pl commons,sample-api-http-binding -am
