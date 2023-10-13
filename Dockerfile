FROM openjdk:8
EXPOSE 8080
ADD target/RestAPIProject-0.0.1-SNAPSHOT.jar RestAPIProject-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/RestAPIProject-0.0.1-SNAPSHOT.jar"]
