FROM openjdk:17-jdk-alpine

VOLUME /tmp

COPY target/account-service-1.0-SNAPSHOT.jar /app.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","/app.jar"]