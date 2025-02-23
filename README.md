# Proyecto SolarApp

## Configuración del Proyecto

### Paso 1: Crear la Carpeta `resources`
Asegúrate de que la carpeta `resources` esté presente en el directorio `src/main` de tu proyecto. Si no existe, créala.

### Paso 2: Crear la base de datos `solar` en MySQL
Crea o edita el archivo `application.properties` en la carpeta `resources` con la siguiente configuración:

```plaintext
spring.application.name=solarapp
# Configuración de la base de datos
spring.datasource.url=jdbc:mysql://localhost:3306/solar
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl

# Configuración de seguridad
spring.security.user.name=usuario
spring.security.user.password=contraseña

# Configuración de correo
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=tu_email@gmail.com
spring.mail.password=tu_contraseña
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

# Configuración de Freemarker
spring.freemarker.template-loader-path=classpath:/templates/
spring.freemarker.suffix=.html

# Configuración de OpenAPI Swagger
springdoc.swagger-ui.path=/swagger-ui.html

```

### Paso 2: Crear la base de datos `solar` en MySQL
Ejecuta los siguientes comandos SQL para crear la base de datos y las tablas necesarias:

```plaintext
-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS solar;

-- Usar la base de datos
USE solar;

-- Crear la tabla enterprises
CREATE TABLE IF NOT EXISTS enterprises (
    idEnterprise BIGINT AUTO_INCREMENT PRIMARY KEY,
    nitEnterprise VARCHAR(50) UNIQUE NOT NULL,
    nameEnterprise VARCHAR(255) NOT NULL
);

-- Insertar datos en la tabla enterprises
INSERT INTO enterprises (nitEnterprise, nameEnterprise) VALUES
    ('1234567890', 'Rayitos de Sol'),
    ('2345678901', 'Energía Verde'),
    ('3456789012', 'Soluciones Solares'),
    ('4567890123', 'Paneles Fotovoltaicos Ltda.'),
    ('5678901234', 'Instalaciones Fotovoltaicas S.A.');

-- Crear la tabla contractors
CREATE TABLE IF NOT EXISTS contractors (
    idContractor BIGINT AUTO_INCREMENT PRIMARY KEY,
    nameContractor VARCHAR(255) NOT NULL,
    emailContractor VARCHAR(255) UNIQUE NOT NULL,
    passwordContractor VARCHAR(255) NOT NULL,
    phoneContractor VARCHAR(50) NOT NULL,
    locationContractor VARCHAR(255),
    expertiseContractor VARCHAR(255),
    nitEnterprise VARCHAR(50) NOT NULL,
    CONSTRAINT fk_contractor_enterprise FOREIGN KEY (nitEnterprise) REFERENCES enterprises(nitEnterprise) ON DELETE CASCADE
);

-- Crear la tabla clients
CREATE TABLE IF NOT EXISTS clients (
    idClient BIGINT AUTO_INCREMENT PRIMARY KEY,
    emailClient VARCHAR(255) NOT NULL UNIQUE,
    passwordClient VARCHAR(255) NOT NULL,
    nameClient VARCHAR(255) NOT NULL,
    phoneClient VARCHAR(20) NOT NULL,
    cityClient VARCHAR(100) NOT NULL,
    neighborhoodClient VARCHAR(100) NOT NULL,
    monthlyConsumptionClient INT NO NULL,
    installationTypeClient VARCHAR(255) NOT NULL,
    contractorId BIGINT,
    CONSTRAINT fk_contractor FOREIGN KEY (contractorId) REFERENCES contractors(idContractor) ON DELETE CASCADE
);


-- Crear la tabla departments
CREATE TABLE IF NOT EXISTS departments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    solarHoursPerDay DOUBLE NOT NULL,
    kWhValue DOUBLE NOT NULL
);

-- Insertar datos en la tabla departments
INSERT INTO departments (name, solarHoursPerDay, kWhValue) VALUES
    ('Cundinamarca', 4.5, 710.42),
    ('Antioquia', 5.0, 764.43),
    ('Valle del Cauca', 5.5, 739.43),
    ('Atlántico', 6.0, 788.88),
    ('Bolívar', 5.8, 792.07),
    ('Santander', 5.2, 806.87),
    ('Risaralda', 5.0, 780.18),
    ('Caldas', 4.8, 759.6),
    ('Magdalena', 6.2, 792.07),
    ('Norte de Santander', 5.7, 802.83);

-- Crear la tabla quotations
CREATE TABLE IF NOT EXISTS quotations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    projectCost DOUBLE NOT NULL,
    systemPower DOUBLE NOT NULL,
    energyGeneration DOUBLE NOT NULL,
    monthlySavings DOUBLE NOT NULL,
    contractorId BIGINT NOT NULL,
    CONSTRAINT fk_quotation_contractor FOREIGN KEY (contractorId) REFERENCES contractors(idContractor) ON DELETE CASCADE
);

-- Crear la tabla contacts
CREATE TABLE IF NOT EXISTS contacts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nameContact VARCHAR(255) NOT NULL,
    emailContact VARCHAR(255) NOT NULL,
    messageContact TEXT NOT NULL
);

CREATE TABLE subsidies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    level VARCHAR(50) NOT NULL,
    client_id BIGINT NOT NULL,
    FOREIGN KEY (client_id) REFERENCES clients(idClient)
);

```

### Paso 4: Documentacion de las APIs
Accede a la documentación de las APIs en Swagger:

```
http://localhost:8080/swagger-ui/index.html#/
```

### Paso 5: Ejecutar la Aplicación
Para ejecutar la aplicación, utiliza el siguiente comando en la raíz del proyecto:

```
mvn spring-boot:run
```

## Despliegue con Docker
Esta sección describe cómo configurar y desplegar la aplicación SolarApp utilizando Docker y Docker Compose.

#### Prerrequisitos
Docker Desktop instalado y en funcionamiento.
Docker Compose instalado.

### Paso 1: Crear el Dockerfile
El `Dockerfile` define cómo se construye la imagen de Docker para tu aplicación Spring Boot. Asegúrate de que el archivo `Dockerfile` esté en la raíz de tu proyecto.

```
# filepath: /d:/Talento tech proyecto/SolarApp/Dockerfile
# Utilizar una imagen base de OpenJDK
FROM openjdk:17-jdk-alpine

# Crear un directorio para la aplicación
VOLUME /tmp

# Argumento para el archivo JAR
ARG JAR_FILE=target/solarapp-0.0.1-SNAPSHOT.jar

# Copiar el archivo JAR a la imagen de Docker
COPY ${JAR_FILE} app.jar

# Exponer el puerto en el que la aplicación se ejecutará
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java","-jar","/app.jar"]

```

### Paso 2: Crear el archivo `docker-compose.yml`
El archivo `docker-compose.yml` define los servicios que se ejecutarán en contenedores Docker, incluyendo la base de datos y la aplicación Spring Boot. Asegúrate de que el archivo `docker-compose.yml` esté en la raíz de tu proyecto.

```
# filepath: /d:/Talento tech proyecto/SolarApp/docker-compose.yml
version: '3.8'

services:
  db:
    image: mysql:8.0
    container_name: solarapp-db
    environment:
      MYSQL_ROOT_PASSWORD: 
      MYSQL_DATABASE: solar
      MYSQL_USER: root
      MYSQL_PASSWORD: 
    ports:
      - "3307:3306"  # Cambiar el puerto expuesto a 3307
    volumes:
      - db_data:/var/lib/mysql

  app:
    build:
      context: .
      dockerfile: Dockerfile
    container_name: solarapp-backend
    environment:
      SPRING_APPLICATION_NAME: solarapp
      SPRING_DATASOURCE_URL: jdbc:mysql://db:3306/solar
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD:
      SPRING_JPA_HIBERNATE_DDL_AUTO: validate
      SPRING_JPA_SHOW_SQL: "true"
      SPRING_SECURITY_USER_NAME: 
      SPRING_SECURITY_USER_PASSWORD: 
      SPRING_JPA_HIBERNATE_NAMING_PHYSICAL_STRATEGY: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
      SPRING_MAIL_HOST: smtp.gmail.com
      SPRING_MAIL_PORT: 587
      SPRING_MAIL_USERNAME: 
      SPRING_MAIL_PASSWORD: 
      SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH: "true"
      SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE: "true"
      SPRING_FREEMARKER_TEMPLATE_LOADER_PATH: classpath:/templates/
      SPRING_FREEMARKER_SUFFIX: .html
      SPRINGDOC_SWAGGER_UI_PATH: /swagger-ui.html
    ports:
      - "8080:8080"
    depends_on:
      - db

volumes:
  db_data:
  
  ```

### Paso 3: Construir y Ejecutar los Contenedores
1. **Construir el Proyecto:** Asegúrate de que el archivo JAR de tu aplicación esté generado en el directorio `target`.
```
mvn clean package

```
2. **Construir las Imágenes Docker:** Usa el siguiente comando para construir las imágenes Docker.
```
docker-compose build
```
3. **Ejecutar los Contenedores:** Usa el siguiente comando para ejecutar los contenedores.
```
docker-compose up
```
### Paso 4: Verificar la Configuración 
1. **Acceder a la Aplicación:** Una vez que los contenedores estén en funcionamiento, puedes acceder a tu aplicación en http://localhost:8080.
1. **Acceder a la Base de Datos:** Puedes acceder a la base de datos MySQL en localhost:3306 utilizando las credenciales definidas en el archivo `docker-compose.yml`.
3. **Documentación de las APIs:** Accede a la documentación de las APIs en Swagger en http://localhost:8080/swagger-ui.html.