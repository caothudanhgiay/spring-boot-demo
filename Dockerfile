# Stage 1: Build the application using the Gradle Wrapper
# This ensures the exact Gradle version from the project is used
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /workspace/app

# Copy the Gradle wrapper and build files
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle .

# Make gradlew executable
RUN chmod +x ./gradlew

# Download dependencies first to leverage Docker cache.
RUN ./gradlew dependencies --no-daemon

# Copy the source code
COPY src ./src

# Build the application with detailed error output
RUN ./gradlew build --no-daemon --stacktrace

# Stage 2: Create the final, smaller image to run the application
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /workspace/app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
