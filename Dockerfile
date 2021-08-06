FROM gradle:7.1.1-jdk8-hotspot
ENV APP_HOME=/usr/app
WORKDIR $APP_HOME

RUN ["apt-get", "update"]
RUN ["apt-get", "install", "-y", "--no-install-recommends", "openjfx"]
RUN ["apt-get", "install", "-y", "build-essential"]
RUN ["rm", "-rf", "/var/lib/apt/lists/*"]

COPY build.gradle settings.gradle gradlew $APP_HOME
COPY gradle $APP_HOME/gradle
COPY src $APP_HOME/src

RUN ["chmod", "+x", "gradlew"]
RUN ["./gradlew", "build"]
ENTRYPOINT ["./gradlew", "run"]