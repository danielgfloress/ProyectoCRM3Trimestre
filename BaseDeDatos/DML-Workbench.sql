-- ============================================================
--  GymCRM — DML INSERT MySQL Workbench
--  Cubre las 5 tablas del modelo académico + tablas del frontend
-- ============================================================

USE crm_gimnasio;

-- ------------------------------------------------------------
--  Modelo académico
-- ------------------------------------------------------------
INSERT INTO clientes (nombre, email, telefono, direccion, fecha_alta) VALUES
    ('Carlos Martínez', 'carlos@xtart.com',  '612345678', 'Calle Mayor 12, Madrid',      '2026-01-10'),
    ('Laura Sánchez',   'laura@xtart.com',   '623456789', 'Avenida del Sol 5, Madrid',   '2026-02-15'),
    ('Marcos López',    'marcos@xtart.com',  '634567890', 'Calle Luna 3, Madrid',        '2026-03-20'),
    ('Sofía Ruiz',      'sofia@xtart.com',   '645678901', 'Calle Olivo 8, Madrid',       '2026-04-05'),
    ('Diego Torres',    'diego@xtart.com',   '656789012', 'Avenida Central 22, Madrid',  '2026-05-01');

INSERT INTO usuarios (nombre, email, rol, password_hash) VALUES
    ('Ana García',    'ana@gymtonic.com',   'admin',         'hash1'),
    ('Pedro Navarro', 'pedro@gymtonic.com', 'recepcionista', 'hash2'),
    ('Marta Díaz',    'marta@gymtonic.com', 'entrenador',    'hash3'),
    ('Raúl Jiménez',  'raul@gymtonic.com',  'entrenador',    'hash4'),
    ('Elena Moreno',  'elena@gymtonic.com', 'recepcionista', 'hash5');

INSERT INTO productos (nombre, descripcion, precio, categoria) VALUES
    ('Cuota Mensual',    'Acceso completo al gimnasio durante un mes',     34.99, 'cuota'),
    ('Cuota Trimestral', 'Acceso completo al gimnasio durante tres meses', 89.99, 'cuota'),
    ('Clase de Yoga',    'Sesión grupal de yoga de 90 minutos',            12.00, 'clase'),
    ('Clase de Spinning','Sesión grupal de spinning de 45 minutos',        10.00, 'clase'),
    ('Personal Trainer', 'Sesión individual con entrenador personal',      40.00, 'personal trainer');

INSERT INTO ventas (cliente_id, usuario_id, fecha, estado, total) VALUES
    (1, 2, '2026-01-11', 'activo',    34.99),
    (2, 2, '2026-02-16', 'activo',    89.99),
    (3, 3, '2026-03-21', 'pendiente', 12.00),
    (4, 4, '2026-04-06', 'activo',    40.00),
    (5, 5, '2026-05-11', 'cancelado', 10.00);

INSERT INTO detalle_venta (venta_id, producto_id, cantidad, precio_unitario) VALUES
    (1, 1, 1, 34.99),
    (2, 2, 1, 89.99),
    (3, 3, 1, 12.00),
    (4, 5, 1, 40.00),
    (5, 4, 1, 10.00);

-- ------------------------------------------------------------
--  Tablas del frontend JS
-- ------------------------------------------------------------
INSERT INTO membresias (nombre, precio, duracion) VALUES
    ('Básica',     25.00, '1 mes'),
    ('Premium',    40.00, '1 mes'),
    ('VIP',        65.00, '1 mes'),
    ('Estudiante', 18.00, '1 mes');

INSERT INTO clases (nombre, instructor, horario, capacidad_maxima, inscritos, nivel) VALUES
    ('Spinning',           'Carlos Ruiz',   'Lun, Mié y Vie — 09:00', 20, 20, 'avanzado'),
    ('Yoga',               'Sofía Mendez',  'Mar y Jue — 10:30',      20, 15, 'principiante'),
    ('Pilates',            'Laura Vega',    'Lun y Mié — 12:00',      15, 12, 'intermedio'),
    ('Boxeo',              'Miguel Torres', 'Mar y Jue — 19:00',      18, 10, 'intermedio'),
    ('Musculación Guiada', 'Roberto Díaz',  'Lun a Vie — 18:00',      10,  8, 'avanzado'),
    ('Zumba',              'Ana Flores',    'Mié y Vie — 20:00',      25, 18, 'principiante');

INSERT INTO configuracion
    (gym_nombre, gym_email, gym_telefono, gym_direccion,
     horario_lv_abre, horario_lv_cierra, horario_sab_abre, horario_sab_cierra, usuario_id)
VALUES
    ('GymCRM Centro', 'info@gymtonic.com', '621 003 320', 'Calle Tridente 67, Alicante',
     '07:00:00', '22:00:00', '09:00:00', '20:00:00', 1);

INSERT INTO agenda (clase_id, cliente_id, fecha, hora, estado) VALUES
    (1, 1,    '2026-05-08', '09:00', 'confirmado'),
    (2, 2,    '2026-05-08', '10:30', 'reservado'),
    (6, 4,    '2026-05-09', '20:00', 'espera'),
    (3, 3,    '2026-05-10', '12:00', 'confirmado'),
    (5, NULL, '2026-05-12', '18:00', 'reservado');
