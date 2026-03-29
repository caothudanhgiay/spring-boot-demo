# Stage 1: Build the application using Gradle
#     docker build -t spring-boot-demo .
FROM gradle:9.3.1-jdk17-alpine AS build
WORKDIR /home/gradle/src
COPY build.gradle settings.gradle ./
COPY src ./src
RUN gradle build --no-daemon

# Stage 2: Create the final, smaller image to run the application
#     docker run -p 8080:8080 spring-boot-demo
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
