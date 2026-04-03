# Using a more reliable base image tag
FROM amazoncorretto:17-alpine

# Set the working directory
WORKDIR /app

# Copy the built JAR file (ensure the 'target' folder exists after Maven build)
COPY target/*.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
