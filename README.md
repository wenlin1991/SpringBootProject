# Password Service


[TOC]

## 1. Introduction 

### 1.1 Purpose 

This document is going to give the reader a general understanding of Password Service, a restful API software. With this document, the reader should be able to build, configure, run and debug a Password Service. 



### 1.2 Document Conventions 

- **Bold**
  - A heading 
- *Italics*
  - URL 
  - File path
- `Highlight with gray`
  - Shell command or code appearing in a sentence. 



## 2. Core Functionality 

Password Service is a minimal HTTP service that exposes the user and group information on a UNIX-like system that is usually locked away in the UNIX */etc/passwd* and */etc/groups*. The service is a toy project, which is not recomended for any prduction usages. 



## 3. Developer's Guide 

### 3.1 Build 

- First, clone the project into your local system. 
  - `git clone https://github.com/wenlin1991/SpringBootProject.git`
- Go to the project folder: *~/path/to/PasswordService* and issue the following command:
  - `mvn clean install` 
  - This command will trigger maven to run the integration test and unit test of the project automatically. 
  - After issue the above command, you can file the jar at *~/path/toPasswordService/PasswordService-0.0.1-SNAPSHOT.jar*

### 3.2 Run & Debug

- Run with script
  - You can find the `runJob.sh` script in the `test` folder within the cloned project. Please use the following command to run the script `./runJob.sh` in terminal.
- The `runJob.sh` script already enabled debug mode. Currently, the debug port is `5005`, please change the port accordingly. 



### 3.3 `runJob.sh` Script Detail 

- Script code 

```bash
#!/bin/bash
  
JAVA=/usr/bin/java
PROFILE=prod
DEBUG=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005
JAR=PasswordService-0.0.1-SNAPSHOT.jar
USERFILE=passwd
GROUPFILE=group

$JAVA $DEBUG -jar $JAR --spring.profiles.active=$PROFILE --data.userFile=$USERFILE --data.groupFile=$GROUPFILE
```

- Details
  - JAVA: your java home
  - PROFILE: active spring profile
  - DEBUG: command to connect to JVM 
  - JAR: path to the jar file 
  - USERFILE: path to the customize `passwd` file 
  - GROUPFILE: path to the customize `group` file 

> **Caution: **
>
> - **PROFILE** : **test**;  **USERFILE** : ; **GROUPFILE**: ; 
>   - no data will be loaded into database.
> -  **PROFILE** : **dev** / **prod** ; **USERFILE** : ; **GROUPFILE**: ; 
>   - Will load data from */etc/passwd* and */etc/group*



## 4. DataBase Schema 

### 4.1 SystemUser 
| FIELD   | TYPE         | NULL | KEY     | DEFAUL |
| ------- | ------------ | ---- | ------- | ------ |
| UID     | Integer(10)  | No   | Primary | Null   |
| COMMENT | Varchar(255) | YES  |         | Null   |
| GID     | Integer(10)  | NO   |         | Null   |
| HOME    | Varchar(255) | YES  |         | Null   |
| NAME    | Varchar(255) | YES  |         | Null   |
| SHELL   | Varchar(255) | YES  |         | Null   |


### 4.2 SystemGroup

| FIELD   | TYPE         | NULL | KEY     | DEFAUL |
| ------- | ------------ | ---- | ------- | ------ |
| GID     | Integer(10)  | No   | Primary | Null   |
| MEMBERS | Varchar(255) | YES  |         | Null   |
| NAME    | Varchar(255) | YES  |         | Null   |













