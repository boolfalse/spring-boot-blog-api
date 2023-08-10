# Use the official Maven image with Java 17
FROM maven:3.8.3-openjdk-17-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy the project's pom.xml and download the dependencies
COPY pom.xml .

# Download the project dependencies
RUN mvn dependency:go-offline -B

# Copy the rest of the application code
COPY src ./src

# Build the application
RUN mvn package

# Create a new image that only contains the compiled JAR and Java 17
FROM openjdk:17-slim AS runtime

# Set the working directory in the container
WORKDIR /app

# Copy the compiled JAR from the build image
COPY --from=build /app/target/*.jar ./project.jar

# Run the Spring Boot application
CMD ["java", "-jar", "project.jar"]
