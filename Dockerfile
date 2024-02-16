FROM maven:3.8.4-openjdk-17-slim AS builder

WORKDIR /app

COPY . .

RUN mvn clean package


FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/ecommerce-shop-0.0.1-SNAPSHOT.jar /app/ecommerceshop.jar

CMD ["java", "-jar", "ecommerceshop.jar"]