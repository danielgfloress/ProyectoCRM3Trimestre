-- ============================================================
--  GymCRM — DML INSERT Oracle (5 registros por tabla)
-- ============================================================

INSERT INTO clientes (nombre, email, telefono, direccion, fecha_alta) VALUES ('Carlos Martínez', 'carlos@xtart.com',  '612345678', 'Calle Mayor 12, Madrid',       TO_DATE('2026-01-10','YYYY-MM-DD'));
INSERT INTO clientes (nombre, email, telefono, direccion, fecha_alta) VALUES ('Laura Sánchez',   'laura@xtart.com',   '623456789', 'Avenida del Sol 5, Madrid',    TO_DATE('2026-02-15','YYYY-MM-DD'));
INSERT INTO clientes (nombre, email, telefono, direccion, fecha_alta) VALUES ('Marcos López',    'marcos@xtart.com',  '634567890', 'Calle Luna 3, Madrid',         TO_DATE('2026-03-20','YYYY-MM-DD'));
INSERT INTO clientes (nombre, email, telefono, direccion, fecha_alta) VALUES ('Sofía Ruiz',      'sofia@xtart.com',   '645678901', 'Calle Olivo 8, Madrid',        TO_DATE('2026-04-05','YYYY-MM-DD'));
INSERT INTO clientes (nombre, email, telefono, direccion, fecha_alta) VALUES ('Diego Torres',    'diego@xtart.com',   '656789012', 'Avenida Central 22, Madrid',   TO_DATE('2026-05-01','YYYY-MM-DD'));
COMMIT;

INSERT INTO usuarios (nombre, email, rol, password_hash) VALUES ('Ana García',    'ana@gymtonic.com',   'admin',          'hash1');
INSERT INTO usuarios (nombre, email, rol, password_hash) VALUES ('Pedro Navarro', 'pedro@gymtonic.com', 'recepcionista',  'hash2');
INSERT INTO usuarios (nombre, email, rol, password_hash) VALUES ('Marta Díaz',    'marta@gymtonic.com', 'entrenador',     'hash3');
INSERT INTO usuarios (nombre, email, rol, password_hash) VALUES ('Raúl Jiménez',  'raul@gymtonic.com',  'entrenador',     'hash4');
INSERT INTO usuarios (nombre, email, rol, password_hash) VALUES ('Elena Moreno',  'elena@gymtonic.com', 'recepcionista',  'hash5');
COMMIT;

INSERT INTO productos (nombre, descripcion, precio, categoria) VALUES ('Cuota Mensual',    'Acceso completo al gimnasio durante un mes',         34.99, 'cuota');
INSERT INTO productos (nombre, descripcion, precio, categoria) VALUES ('Cuota Trimestral', 'Acceso completo al gimnasio durante tres meses',     89.99, 'cuota');
INSERT INTO productos (nombre, descripcion, precio, categoria) VALUES ('Clase de Yoga',    'Sesión grupal de yoga de 90 minutos',                12.00, 'clase');
INSERT INTO productos (nombre, descripcion, precio, categoria) VALUES ('Clase de Spinning','Sesión grupal de spinning de 45 minutos',            10.00, 'clase');
INSERT INTO productos (nombre, descripcion, precio, categoria) VALUES ('Personal Trainer', 'Sesión individual con entrenador personal',          40.00, 'personal trainer');
COMMIT;

INSERT INTO ventas (cliente_id, usuario_id, fecha, estado, total) VALUES (1, 2, TO_DATE('2026-01-11','YYYY-MM-DD'), 'activo',    34.99);
INSERT INTO ventas (cliente_id, usuario_id, fecha, estado, total) VALUES (2, 2, TO_DATE('2026-02-16','YYYY-MM-DD'), 'activo',    89.99);
INSERT INTO ventas (cliente_id, usuario_id, fecha, estado, total) VALUES (3, 3, TO_DATE('2026-03-21','YYYY-MM-DD'), 'pendiente', 12.00);
INSERT INTO ventas (cliente_id, usuario_id, fecha, estado, total) VALUES (4, 4, TO_DATE('2026-04-06','YYYY-MM-DD'), 'activo',    40.00);
INSERT INTO ventas (cliente_id, usuario_id, fecha, estado, total) VALUES (5, 5, TO_DATE('2026-05-11','YYYY-MM-DD'), 'cancelado', 10.00);
COMMIT;

INSERT INTO detalle_venta (venta_id, producto_id, cantidad, precio_unitario) VALUES (1, 1, 1, 34.99);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad, precio_unitario) VALUES (2, 2, 1, 89.99);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad, precio_unitario) VALUES (3, 3, 1, 12.00);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad, precio_unitario) VALUES (4, 5, 1, 40.00);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad, precio_unitario) VALUES (5, 4, 1, 10.00);
COMMIT;

SELECT * FROM clientes;
SELECT * FROM usuarios;
SELECT * FROM productos;
SELECT * FROM ventas;
SELECT * FROM detalle_venta;
