FROM openjdk:17

VOLUME /data
VOLUME /var/log/app

EXPOSE 5051

WORKDIR /app

ENV SPRING_PROFILES_ACTIVE=prod

ARG JAR_FILE=target/xfood-1.0.0-SNAPSHOT.jar

ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]