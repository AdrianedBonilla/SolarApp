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
    monthlyConsumptionClient DOUBLE NULL,
    installationTypeClient VARCHAR(100) NOT NULL,
    siteConditionsClient TEXT NOT NULL,
    contractorId BIGINT NOT NULL,
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