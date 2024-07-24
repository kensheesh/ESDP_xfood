FROM maven:3.6.3-openjdk-17-slim AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

# skipp tests
RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:17-jdk

VOLUME /data
VOLUME /var/log/app
VOLUME /config

EXPOSE 5051

WORKDIR /app

ENV SPRING_PROFILES_ACTIVE=prod

COPY --from=build /app/target/xfood-1.0.0-SNAPSHOT.jar app.jar

COPY .env /app/.env

ENTRYPOINT ["java", "-jar", "app.jar"]