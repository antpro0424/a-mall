FROM openjdk:17-jdk-alpine

COPY target/item-service-1.0-SNAPSHOT.jar /app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "/app.jar"]

