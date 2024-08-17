FROM openjdk:17-jdk-alpine

COPY target/cart-service-1.0-SNAPSHOT.jar /app.jar

EXPOSE 8086

ENTRYPOINT ["java", "-jar", "/app.jar"]