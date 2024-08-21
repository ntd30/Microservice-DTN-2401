FROM openjdk:17-jdk

LABEL authors="ntd30"

WORKDIR /app

COPY target/auth-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8888

CMD ["java", "-jar", "app.jar"]