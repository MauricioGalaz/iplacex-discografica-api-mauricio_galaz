# STAGE 1: Construcción
FROM gradle:7.6-jdk17 AS build
WORKDIR /app
COPY ./build.gradle . 
COPY ./settings.gradle . 
COPY ./src ./src 
RUN gradle clean build --no-daemon

# STAGE 2: Ejecución
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar discografia-1.jar
EXPOSE 8080
CMD ["java", "-jar", "discografia-1.jar"]

