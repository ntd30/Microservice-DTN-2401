FROM openjdk:17-jdk

LABEL autors="ntd30"

WORKDIR /app

COPY target/api-gateway-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8762

CMD ["java", "-jar", "app.jar"]