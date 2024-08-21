FROM openjdk:17-jdk

LABEL authors="ntd30"

WORKDIR /app

COPY target/rabbitmq-client-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8989

CMD ["java", "-jar", "app.jar"]