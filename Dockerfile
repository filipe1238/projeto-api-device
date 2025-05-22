FROM maven:3.9-amazoncorretto-21-debian AS build
WORKDIR /app


COPY pom.xml /app/
COPY src /app/src
RUN mvn -f /app/pom.xml clean package

FROM openjdk:21

COPY --from=build /app/target/projeto-api-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 80

ENTRYPOINT ["java", "-jar", "/app/app.jar"]