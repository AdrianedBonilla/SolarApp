# Proyecto SolarApp

## Configuración del Proyecto

### Paso 1: Crear la Carpeta `resources`
Asegúrate de que la carpeta `resources` esté presente en el directorio `src/main` de tu proyecto. Si no existe, créala.

```plaintext
src/main/resources/application.properties

spring.application.name=solarapp
spring.datasource.url=jdbc:mysql://localhost:3306/solar
spring.datasource.username=
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.security.user.name=
spring.security.user.password=
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=
spring.mail.password=
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.freemarker.template-loader-path=classpath:/templates/

```

### Paso 2: Crear la base de datos `solar` en MySQL

```plaintext
CREATE DATABASE solar;

CREATE TABLE IF NOT EXISTS enterprises (
    idEnterprise BIGINT AUTO_INCREMENT PRIMARY KEY,
    nitEnterprise VARCHAR(50) UNIQUE NOT NULL,
    nameEnterprise VARCHAR(255) NOT NULL
);

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
    contractor_id BIGINT NOT NULL,
    CONSTRAINT fk_contractor FOREIGN KEY (contractor_id) REFERENCES contractors(idContractor) ON DELETE CASCADE
);

CREATE TABLE cities (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    solarHoursPerDay DOUBLE NOT NULL
);

CREATE TABLE quotations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    projectCost DOUBLE NOT NULL,
    systemPower DOUBLE NOT NULL,
    energyGeneration DOUBLE NOT NULL,
    monthlySavings DOUBLE NOT NULL
);

CREATE TABLE contacts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nameContact VARCHAR(255) NOT NULL,
    emailContact VARCHAR(255) NOT NULL,
    messageContact TEXT NOT NULL
);

INSERT INTO cities (name, solarHoursPerDay) VALUES
('Bogotá', 4.5),
('Medellín', 5.0),
('Cali', 5.5),
('Barranquilla', 6.0),
('Cartagena', 5.8),
('Bucaramanga', 5.2),
('Pereira', 5.0),
('Manizales', 4.8),
('Santa Marta', 6.2),
('Cúcuta', 5.7);

INSERT INTO enterprises (nitEnterprise, nameEnterprise) VALUES
    ('1234567890', 'Rayitos de Sol'),
    ('2345678901', 'Energía Verde'),
    ('3456789012', 'Soluciones Solares'),
    ('4567890123', 'Paneles Fotovoltaicos Ltda.'),
    ('5678901234', 'Instalaciones Fotovoltaicas S.A.'):

```
