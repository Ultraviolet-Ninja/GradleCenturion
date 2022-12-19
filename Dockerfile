#V1 of the Dockerfile
#FROM openjdk:16-alpine
#
#ARG JAR_FILE
#COPY $JAR_FILE app.jar
#
#ENTRYPOINT ["java", "-jar", "app.jar"]

FROM gradle:7.4.0-jdk17-alpine

ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME

COPY necessaryFiles/build.gradle necessaryFiles/settings.gradle $APP_HOME

COPY necessaryFiles/wrapper ${APP_HOME}gradle/wrapper
COPY --chown=gradle:gradle . /home/gradle/src
USER root
RUN chown -R gradle /home/gradle/src

#COPY necessaryFiles/java/module-info.java ${APP_HOME}src/main/java

RUN gradle build -i --stacktrace
COPY necessaryFiles/java ${APP_HOME}src/main/java
COPY necessaryFiles/resources ${APP_HOME}src/main/resources
RUN gradle clean build

ENTRYPOINT gradle run