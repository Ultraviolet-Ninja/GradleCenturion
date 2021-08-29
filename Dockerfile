FROM openjdk:15-alpine

ARG JAR_FILE
COPY $JAR_FILE app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]