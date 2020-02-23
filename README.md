# Elinvar-Case-Study
####  Case study project for Elinvar

The project statement is to parse log file which contains multiple requests entry and exit time for different services and display how many times unique service execution completed (entry + exit) and what is maximum time taken by service.

This solution is purely based on Java. The approach followed is to find all the service ids having both entry and exit records. Store filtered records in hashmap with service id as key and ServiceData [servicename serviceid time] as value. Then we are storing the max(exit – entry) time in value of another hashmap having service name as key.

### To achieve it, below classes and resources are added:
1. ServiceData.java – used for data modeling under com.test.model.
2. LogReader.java – parser and entry point of program under com.test.log.
3. Test.log – resource for parsing under com.test.resource.

### Prerequisite : 
JAVA 8 with JAVA_HOME set

### Steps to setup project:
- Clone git Repo to local using command: git clone https://github.com/sunilpandey1989/elinvar-repo.git
- Compile JAVA code:
 -  For Windows: 
      - cd src
      - dir /s /B *.java > sources.txt
      - javac @sources.txt
 - For Linux:
     - cd src
     - $ find -name "*.java" > sources.txt
     - $ javac @sources.txt
- Build jar from compiled class files:
	jar cvfe ../elinvar-0.0.1.SNAPSHOT.jar com.test.log.LogReader com/test/log/LogReader.class  com/test/model/ServiceData.class com/test/resource/test.log
- Run the jar using command :
java -jar ../elinvar-0.0.1.SNAPSHOT.jar
- Verify the output in console.

### Docker Support :

Dockerfile is added in the project which is using java:8 as base image and on top of it, we have added our java code. To build the docker image, please run following command “**docker build -t elinvar-case .**” as a output it creates a docker image with name elinvar-case:latest

Deploy a container based on above image with command: **docker run -it elinvar-case:latest**

Once deployed the output will get displayed in the Console.
