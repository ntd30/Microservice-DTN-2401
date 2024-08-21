FROM openjdk:17-jdk

LABEL authors="ntd30"

WORKDIR /app

COPY target/order-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE ${APP_PORT}

CMD ["java", "-jar", "app.jar"]