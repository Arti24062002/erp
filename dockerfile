# Use a base image with Java runtime environment
FROM openjdk:11-jre-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the host to the container
COPY target/erp-0.0.1-SNAPSHOT.jar /app/erp.jar

# Expose the port the application runs on
EXPOSE 8080

# Define the command to run the JAR file
ENTRYPOINT ["java", "-jar", "/app/erp.jar"]
