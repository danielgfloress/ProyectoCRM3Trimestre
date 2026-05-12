DROP DATABASE IF EXISTS crm_gimnasio;
CREATE DATABASE crm_gimnasio;
USE crm_gimnasio;

CREATE TABLE IF NOT EXISTS clientes (
    id          INT             PRIMARY KEY AUTO_INCREMENT,
    nombre      VARCHAR(100)    NOT NULL,
    email       VARCHAR(150)    NOT NULL UNIQUE,
    telefono    VARCHAR(15),
    direccion   VARCHAR(255),
    fecha_alta  DATE            NOT NULL DEFAULT (CURRENT_DATE)
);

CREATE TABLE IF NOT EXISTS usuarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    rol VARCHAR(50) NOT NULL,
    password_hash VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS membresias (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(60) NOT NULL,
    precio DECIMAL(8 , 2 ) NOT NULL,
    duracion VARCHAR(30) NOT NULL DEFAULT '1 mes'
);

CREATE TABLE IF NOT EXISTS clases (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(80) NOT NULL,
    instructor VARCHAR(120) NOT NULL,
    horario VARCHAR(80) NOT NULL,
    capacidad_maxima INT NOT NULL DEFAULT 20,
    inscritos INT NOT NULL DEFAULT 0,
    nivel VARCHAR(20) NOT NULL DEFAULT 'principiante'
);

CREATE TABLE IF NOT EXISTS agenda (
    id INT PRIMARY KEY AUTO_INCREMENT,
    clase_id INT NOT NULL,
    cliente_id INT,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'reservado',
    CONSTRAINT fk_agenda_clase FOREIGN KEY (clase_id)
        REFERENCES clases (id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_agenda_cliente FOREIGN KEY (cliente_id)
        REFERENCES clientes (id)
        ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS configuracion (
    id INT PRIMARY KEY AUTO_INCREMENT,
    gym_nombre VARCHAR(120) NOT NULL DEFAULT 'GymCRM Centro',
    gym_email VARCHAR(150),
    gym_telefono VARCHAR(30),
    gym_direccion VARCHAR(255),
    horario_lv_abre TIME DEFAULT '07:00:00',
    horario_lv_cierra TIME DEFAULT '22:00:00',
    horario_sab_abre TIME DEFAULT '09:00:00',
    horario_sab_cierra TIME DEFAULT '20:00:00',
    usuario_id INT,
    CONSTRAINT fk_config_usuario FOREIGN KEY (usuario_id)
        REFERENCES usuarios (id)
        ON DELETE SET NULL ON UPDATE CASCADE
);
