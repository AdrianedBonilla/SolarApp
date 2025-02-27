-- Crear la tabla enterprises si no existe
CREATE TABLE IF NOT EXISTS enterprises (
    nitEnterprise VARCHAR(50) PRIMARY KEY,
    nameEnterprise VARCHAR(255) NOT NULL
);

-- Insertar datos en la tabla enterprises
INSERT INTO enterprises (nitEnterprise, nameEnterprise) VALUES
    ('1234567890', 'Rayitos de Sol'),
    ('2345678901', 'Energía Verde'),
    ('3456789012', 'Soluciones Solares'),
    ('4567890123', 'Paneles Fotovoltaicos Ltda.'),
    ('5678901234', 'Instalaciones Fotovoltaicas S.A.');

-- Crear la tabla contractors si no existe
CREATE TABLE IF NOT EXISTS contractors (
    idContractor BIGINT AUTO_INCREMENT PRIMARY KEY,
    nameContractor VARCHAR(255) NOT NULL,
    emailContractor VARCHAR(255) NOT NULL,
    passwordContractor VARCHAR(255) NOT NULL,
    phoneContractor VARCHAR(50) NOT NULL,
    locationContractor VARCHAR(255) NOT NULL,
    expertiseContractor VARCHAR(255) NOT NULL,
    nitEnterprise VARCHAR(50),
    FOREIGN KEY (nitEnterprise) REFERENCES enterprises(nitEnterprise)
);

-- Insertar datos en la tabla contractors
INSERT INTO contractors (nameContractor, emailContractor, passwordContractor, phoneContractor, locationContractor, expertiseContractor, nitEnterprise) VALUES
    ('Juano Pérez', 'juano.perez@example.com', 'securepassword', '+573001234567', 'Medellín, Colombia', 'Instalación de paneles solares', '1234567890'),
    ('María Gómez', 'maria.gomez@example.com', 'securepassword', '+573002345678', 'Bogotá, Colombia', 'Mantenimiento de sistemas solares', '2345678901'),
    ('Carlos Rodríguez', 'carlos.rodriguez@example.com', 'securepassword', '+573003456789', 'Cali, Colombia', 'Diseño de sistemas solares', '3456789012'),
    ('Ana Martínez', 'ana.martinez@example.com', 'securepassword', '+573004567890', 'Barranquilla, Colombia', 'Consultoría en energía solar', '4567890123');

-- Crear la tabla departments si no existe
CREATE TABLE IF NOT EXISTS departments (
    idDepartment BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    solarHoursPerDay DECIMAL(3,1) NOT NULL,
    kWhValue DECIMAL(10,2) NOT NULL
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
    ('Norte de Santander', 5.7, 802.83),
    ('Boyacá', 5.3, 728.9), 
    ('Cauca', 4.7, 745.32), 
    ('Cesar', 6.1, 809.33), 
    ('Chocó', 4.3, 733.23), 
    ('Córdoba', 6.0, 778.21), 
    ('Guainía', 4.6, 709.89), 
    ('Guaviare', 4.9, 725.55), 
    ('Huila', 5.6, 755.33), 
    ('La Guajira', 6.4, 815.12), 
    ('Meta', 5.1, 732.43), 
    ('Nariño', 4.4, 711.78), 
    ('Putumayo', 4.3, 704.66), 
    ('Quindío', 4.8, 763.12), 
    ('San Andrés y Providencia', 6.5, 789.65), 
    ('Sucre', 5.9, 765.44), 
    ('Tolima', 5.4, 746.88), 
    ('Arauca', 5.8, 769.14), 
    ('Caquetá', 4.2, 702.34), 
    ('Casanare', 5.6, 751.48), 
    ('Vaupés', 4.1, 698.21), 
    ('Vichada', 5.0, 723.67), 
    ('Amazonas', 4.3, 699.89);