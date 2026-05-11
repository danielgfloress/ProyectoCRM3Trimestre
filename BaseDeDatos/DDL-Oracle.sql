-- ============================================================
--  GymCRM — DDL Oracle
--  5 tablas con relaciones:
--    1:N  clientes  → ventas
--    1:N  usuarios  → ventas
--    N:M  ventas ↔ productos  (tabla intermedia: detalle_venta)
-- ============================================================

CREATE TABLE clientes (
    id          NUMBER          GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre      VARCHAR2(100)   NOT NULL,
    email       VARCHAR2(150)   NOT NULL,
    telefono    VARCHAR2(15),
    direccion   VARCHAR2(255),
    fecha_alta  DATE            DEFAULT SYSDATE NOT NULL,
    CONSTRAINT uq_clientes_email UNIQUE (email)
);

CREATE TABLE usuarios (
    id              NUMBER        GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre          VARCHAR2(100) NOT NULL,
    email           VARCHAR2(150) NOT NULL,
    rol             VARCHAR2(50)  NOT NULL,
    password_hash   VARCHAR2(255) NOT NULL,
    CONSTRAINT uq_usuarios_email UNIQUE (email)
);

CREATE TABLE productos (
    id          NUMBER        GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre      VARCHAR2(150) NOT NULL,
    descripcion VARCHAR2(500),
    precio      NUMBER(8,2)   NOT NULL,
    categoria   VARCHAR2(100) NOT NULL
);

-- 1:N clientes → ventas  |  1:N usuarios → ventas
CREATE TABLE ventas (
    id          NUMBER        GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cliente_id  NUMBER        NOT NULL,
    usuario_id  NUMBER        NOT NULL,
    fecha       DATE          DEFAULT SYSDATE NOT NULL,
    estado      VARCHAR2(50)  DEFAULT 'activo' NOT NULL,
    total       NUMBER(10,2)  DEFAULT 0 NOT NULL,
    CONSTRAINT fk_ventas_cliente FOREIGN KEY (cliente_id) REFERENCES clientes(id),
    CONSTRAINT fk_ventas_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- N:M ventas ↔ productos
CREATE TABLE detalle_venta (
    id              NUMBER      GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    venta_id        NUMBER      NOT NULL,
    producto_id     NUMBER      NOT NULL,
    cantidad        NUMBER      DEFAULT 1 NOT NULL,
    precio_unitario NUMBER(8,2) NOT NULL,
    CONSTRAINT fk_detalle_venta    FOREIGN KEY (venta_id)    REFERENCES ventas(id),
    CONSTRAINT fk_detalle_producto FOREIGN KEY (producto_id) REFERENCES productos(id)
);
