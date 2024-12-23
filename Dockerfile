# Use an official OpenJDK runtime as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged application into the container
COPY target/fleet-management-app.jar app.jar

# Expose the application port
EXPOSE 8081

LABEL authors="tsaki"

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]