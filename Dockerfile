# Use a base image with Java
FROM eclipse-temurin:17-jdk-focal

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container at /app
COPY target/ecommerce-shop-0.0.1-SNAPSHOT.jar app.jar

# Specify the command to run on container startup
ENTRYPOINT ["java","-jar","app.jar"]