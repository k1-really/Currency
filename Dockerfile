FROM openjdk:21-jdk-slim
LABEL authors="KiReally"
COPY target/*.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]
