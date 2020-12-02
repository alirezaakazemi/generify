FROM artifactory.asredanesh.com/docker/openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ARG CERT=target/*.jar
#COPY CERT.CERT /

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=docker","/app.jar"]
#####
