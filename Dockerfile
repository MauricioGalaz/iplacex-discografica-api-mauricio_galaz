# STAGE 1: Construcción
FROM gradle:7.6.0-jdk21 AS builder

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar los archivos necesarios
COPY build.gradle settings.gradle ./
COPY src ./src

# Construir el proyecto
RUN gradle build --no-daemon

# STAGE 2: Ejecución
FROM openjdk:21-jdk-slim

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el archivo JAR generado desde el contenedor builder
COPY --from=builder /app/build/libs/*.jar discografia-api.jar

# Exponer el puerto 8080
EXPOSE 8080

# Ejecutar la aplicación
CMD ["java", "-jar", "discografia-api.jar"]
