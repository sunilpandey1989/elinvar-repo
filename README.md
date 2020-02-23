# elinvar-case-study
Case study project for Elinvar

The project statement of the project is to parse a log file which contains multiple requests entry and exit time and print how many times service execution completed (entry + exit) and what is maximum time taken by service.

This project is purely based on Java. The approach we followed is to find all the service ids having both entry and exit records. Store filtered records in hashmap with service id as key and ServiceData [servicename serviceid time] as value. Then we are storing the max(exit – entry) time in value of another hashmap having service name as key.

To achieve it, below classes are added:
1. ServiceData.java – used for data modeling.
2. LogReader.java – parser and entry point of program.
3. Test.log – resource for parsing.

Prerequisite : 
JAVA 8 with JAVA_HOME set

Steps to setup project:
1. Clone git Repo to local using command: git clone https://github.com/sunilpandey1989/elinvar-repo.git
2. Compile JAVA code:
2.1. For Windows: 
cd src
dir /s /B *.java > sources.txt
javac @sources.txt
2.2. For Linux:
3. Build jar from compiled class files:
jar cvfe ../elinvar-0.0.1.SNAPSHOT.jar com.test.log.LogReader com/test/log/LogReader.class  com/test/model/ServiceData.class com/test/resource/test.log
4. Run the jar using command :
java -jar ../elinvar-0.0.1.SNAPSHOT.jar
5. Verify the output

Docker Support :

Dockerfile is added in the project which is using java:8 as base image and on top of it, we have added our java code. To build the docker image, please run following command “docker build -t elinvar-case .” as a output it creates a docker image with name elinvar-case:latest

Deploy a container based on above image with command: docker run -it elinvar-case:latest

Once deployed the output will get displayed in the Console.
