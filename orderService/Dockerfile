FROM openjdk:17-jre
ADD target/orderService-0.0.1-SNAPSHOT.jar app.jar
ADD application.yml app-config.yml
EXPOSE 8081
ENTRYPOINT ["java", "-Djava.security.edg=file:/dev/./urandom", "-jar", "app.jar"]