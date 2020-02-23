FROM java:8
RUN java -version
COPY . /home/elinvar/
WORKDIR /home/elinvar/
RUN find -name "*.java" > sources.txt
RUN javac @sources.txt
WORKDIR /home/elinvar/src
CMD ["/usr/bin/java", "com.test.log.LogReader"]