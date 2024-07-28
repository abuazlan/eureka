# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-slim

# Set the working directory in the container
WORKDIR /app

# Copy the jar file into the container
COPY *.jar app.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]

