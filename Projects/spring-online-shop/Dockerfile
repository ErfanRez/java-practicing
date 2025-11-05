# Use a multi-stage build to reduce the final image size
# Stage 1: Build the application
FROM gradle:7.4.2-jdk17 AS build
WORKDIR /home/gradle/src
COPY . .
RUN gradle build --no-daemon

# Stage 2: Create the final image
FROM eclipse-temurin:17.0.11_9-jdk-alpine
WORKDIR /app
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]