# filepath: /d:/Personal/Proyectos/SolarApp/Dockerfile
# Dockerfile
FROM openjdk:17-jdk-alpine

# Instalar bash
RUN apk add --no-cache bash

# Crear un directorio para la aplicación
VOLUME /tmp

# Argumento para el archivo JAR
ARG JAR_FILE=target/solarapp-0.0.1-SNAPSHOT.jar

# Copiar el archivo JAR a la imagen de Docker
COPY ${JAR_FILE} app.jar

# Copiar el script wait-for-it.sh
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

# Exponer el puerto en el que la aplicación se ejecutará
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["/bin/bash", "/wait-for-it.sh", "db:3306", "--", "java", "-jar", "/app.jar"]