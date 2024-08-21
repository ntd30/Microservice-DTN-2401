FROM openjdk:17-jdk

LABEL authors="ntd30"

WORKDIR /app

COPY target/load-balancer-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8800

CMD ["java", "-jar", "app.jar"]