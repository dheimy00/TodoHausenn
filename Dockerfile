FROM openjdk:8-jdk-alpine
ADD target/TodoHausenn-0.0.1.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
