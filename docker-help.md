#FROM bellsoft/liberica-openjdk-alpine

# Stage 1: Build the JAR
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Build the application (skipping tests for faster builds)
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar /app/myapp.jar

# Expose the application port
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "myapp.jar"]