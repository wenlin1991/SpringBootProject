#!/bin/bash
  
JAVA=/usr/bin/java
PROFILE=prod
DEBUG=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005
JAR=PasswordService-0.0.1-SNAPSHOT.jar
USERFILE=passwd
GROUPFILE=group

$JAVA $DEBUG -jar $JAR --spring.profiles.active=$PROFILE --data.userFile=$USERFILE --data.groupFile=$GROUPFILE
