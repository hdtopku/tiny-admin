# Use OpenJDK 22 as the base image
FROM openjdk:22-ea-16-jdk-slim

# Install Maven
RUN apt-get update && apt-get install -y maven

# Set the working directory
WORKDIR /usr/src/app

# Copy project files to the container
COPY . .

# Build the Spring Boot application
RUN mvn clean install -DskipTests

# Change to the tiny-admin-starter directory
WORKDIR /usr/src/app/tiny-admin/tiny-admin-starter

# Run the Spring Boot application
CMD ["java", "-jar", "target/tiny-admin-starter.jar"]
