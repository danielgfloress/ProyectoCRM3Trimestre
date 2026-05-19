-- ============================================================
--  TABLA: CLIENTES
-- ============================================================

-- 1
DECLARE
    CURSOR c_clientes IS
        SELECT id, nombre, email, fecha_alta
        FROM clientes
        ORDER BY fecha_alta DESC;
    v_reg c_clientes%ROWTYPE;
BEGIN
    OPEN c_clientes;
    LOOP
        FETCH c_clientes INTO v_reg;
        EXIT WHEN c_clientes%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE('ID: ' || v_reg.id || ' | ' || v_reg.nombre || ' | Alta: ' || TO_CHAR(v_reg.fecha_alta, 'DD/MM/YYYY'));
    END LOOP;
    CLOSE c_clientes;
END;
/

-- 2
DECLARE
    CURSOR c_clientes_2026 IS
        SELECT nombre, email, fecha_alta
        FROM clientes
        WHERE EXTRACT(YEAR FROM fecha_alta) = 2026;
    v_nombre   clientes.nombre%TYPE;
    v_email    clientes.email%TYPE;
    v_fecha    clientes.fecha_alta%TYPE;
BEGIN
    OPEN c_clientes_2026;
    LOOP
        FETCH c_clientes_2026 INTO v_nombre, v_email, v_fecha;
        EXIT WHEN c_clientes_2026%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE(v_nombre || ' — ' || v_email || ' — ' || TO_CHAR(v_fecha, 'DD/MM/YYYY'));
    END LOOP;
    CLOSE c_clientes_2026;
END;
/

-- 3
DECLARE
    CURSOR c_con_telefono IS
        SELECT nombre, telefono FROM clientes WHERE telefono IS NOT NULL;
    v_reg c_con_telefono%ROWTYPE;
BEGIN
    FOR v_reg IN c_con_telefono LOOP
        DBMS_OUTPUT.PUT_LINE(v_reg.nombre || ' → ' || v_reg.telefono);
    END LOOP;
END;
/

-- 4
DECLARE
    CURSOR c_total IS SELECT COUNT(*) AS total FROM clientes;
    v_total NUMBER;
BEGIN
    OPEN c_total;
    FETCH c_total INTO v_total;
    CLOSE c_total;
    DBMS_OUTPUT.PUT_LINE('Total de clientes: ' || v_total);
END;
/

-- 5
DECLARE
    CURSOR c_ventas_por_cliente IS
        SELECT c.nombre, COUNT(v.id) AS num_ventas
        FROM clientes c
        LEFT JOIN ventas v ON v.cliente_id = c.id
        GROUP BY c.nombre
        ORDER BY num_ventas DESC;
    v_nombre    clientes.nombre%TYPE;
    v_num       NUMBER;
BEGIN
    OPEN c_ventas_por_cliente;
    LOOP
        FETCH c_ventas_por_cliente INTO v_nombre, v_num;
        EXIT WHEN c_ventas_por_cliente%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE(v_nombre || ' → ' || v_num || ' venta(s)');
    END LOOP;
    CLOSE c_ventas_por_cliente;
END;
/

-- ============================================================
--  TABLA: USUARIOS
-- ============================================================

-- 1
DECLARE
    CURSOR c_usuarios IS SELECT nombre, email, rol FROM usuarios ORDER BY rol;
    v_reg c_usuarios%ROWTYPE;
BEGIN
    FOR v_reg IN c_usuarios LOOP
        DBMS_OUTPUT.PUT_LINE('[' || v_reg.rol || '] ' || v_reg.nombre || ' — ' || v_reg.email);
    END LOOP;
END;
/

-- 2
DECLARE
    CURSOR c_admins IS SELECT nombre, email FROM usuarios WHERE rol = 'admin';
    v_reg c_admins%ROWTYPE;
BEGIN
    OPEN c_admins;
    LOOP
        FETCH c_admins INTO v_reg;
        EXIT WHEN c_admins%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE('Admin: ' || v_reg.nombre || ' (' || v_reg.email || ')');
    END LOOP;
    CLOSE c_admins;
END;
/

-- 3
DECLARE
    CURSOR c_ventas_usuario IS
        SELECT u.nombre, COUNT(v.id) AS gestionadas
        FROM usuarios u
        LEFT JOIN ventas v ON v.usuario_id = u.id
        GROUP BY u.nombre
        ORDER BY gestionadas DESC;
    v_nombre    usuarios.nombre%TYPE;
    v_gestion   NUMBER;
BEGIN
    FOR v_nombre, v_gestion IN c_ventas_usuario LOOP
        DBMS_OUTPUT.PUT_LINE(v_nombre || ' → ' || v_gestion || ' venta(s) gestionada(s)');
    END LOOP;
END;
/

-- 4
DECLARE
    CURSOR c_por_rol IS
        SELECT rol, COUNT(*) AS total FROM usuarios GROUP BY rol ORDER BY rol;
    v_rol   usuarios.rol%TYPE;
    v_total NUMBER;
BEGIN
    OPEN c_por_rol;
    LOOP
        FETCH c_por_rol INTO v_rol, v_total;
        EXIT WHEN c_por_rol%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE('Rol: ' || v_rol || ' → ' || v_total || ' usuario(s)');
    END LOOP;
    CLOSE c_por_rol;
END;
/

-- 5
DECLARE
    CURSOR c_alfa IS SELECT nombre FROM usuarios ORDER BY nombre ASC;
    v_nombre usuarios.nombre%TYPE;
    v_pos    NUMBER := 1;
BEGIN
    OPEN c_alfa;
    LOOP
        FETCH c_alfa INTO v_nombre;
        EXIT WHEN c_alfa%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE(v_pos || '. ' || v_nombre);
        v_pos := v_pos + 1;
    END LOOP;
    CLOSE c_alfa;
END;
/

-- ============================================================
--  TABLA: PRODUCTOS
-- ============================================================

-- 1
DECLARE
    CURSOR c_productos IS SELECT nombre, categoria, precio FROM productos ORDER BY categoria;
    v_reg c_productos%ROWTYPE;
BEGIN
    FOR v_reg IN c_productos LOOP
        DBMS_OUTPUT.PUT_LINE('[' || v_reg.categoria || '] ' || v_reg.nombre || ' — ' || v_reg.precio || '€');
    END LOOP;
END;
/

-- 2
DECLARE
    CURSOR c_caros IS SELECT nombre, precio FROM productos WHERE precio > 30 ORDER BY precio DESC;
    v_nombre productos.nombre%TYPE;
    v_precio productos.precio%TYPE;
BEGIN
    OPEN c_caros;
    LOOP
        FETCH c_caros INTO v_nombre, v_precio;
        EXIT WHEN c_caros%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE(v_nombre || ' → ' || v_precio || '€');
    END LOOP;
    CLOSE c_caros;
END;
/

-- 3
DECLARE
    CURSOR c_media IS
        SELECT categoria, ROUND(AVG(precio), 2) AS precio_medio
        FROM productos
        GROUP BY categoria
        ORDER BY categoria;
    v_cat   productos.categoria%TYPE;
    v_media NUMBER;
BEGIN
    OPEN c_media;
    LOOP
        FETCH c_media INTO v_cat, v_media;
        EXIT WHEN c_media%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE('Categoría: ' || v_cat || ' → Precio medio: ' || v_media || '€');
    END LOOP;
    CLOSE c_media;
END;
/

-- 4
DECLARE
    CURSOR c_mas_vendido IS
        SELECT p.nombre, SUM(dv.cantidad) AS total_vendido
        FROM productos p
        JOIN detalle_venta dv ON dv.producto_id = p.id
        GROUP BY p.nombre
        ORDER BY total_vendido DESC;
    v_nombre productos.nombre%TYPE;
    v_total  NUMBER;
BEGIN
    FOR v_nombre, v_total IN c_mas_vendido LOOP
        DBMS_OUTPUT.PUT_LINE(v_nombre || ' → ' || v_total || ' unidades vendidas');
    END LOOP;
END;
/

-- 5
DECLARE
    CURSOR c_ingresos IS
        SELECT p.nombre, SUM(dv.cantidad * dv.precio_unitario) AS ingresos
        FROM productos p
        JOIN detalle_venta dv ON dv.producto_id = p.id
        GROUP BY p.nombre
        ORDER BY ingresos DESC;
    v_nombre  productos.nombre%TYPE;
    v_ingreso NUMBER;
BEGIN
    OPEN c_ingresos;
    LOOP
        FETCH c_ingresos INTO v_nombre, v_ingreso;
        EXIT WHEN c_ingresos%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE(v_nombre || ' → ' || v_ingreso || '€ en ingresos');
    END LOOP;
    CLOSE c_ingresos;
END;
/

-- ============================================================
--  TABLA: VENTAS
-- ============================================================

-- 1
DECLARE
    CURSOR c_ventas IS
        SELECT v.id, c.nombre AS cliente, u.nombre AS usuario, v.fecha, v.estado, v.total
        FROM ventas v
        JOIN clientes c ON c.id = v.cliente_id
        JOIN usuarios u ON u.id = v.usuario_id
        ORDER BY v.fecha;
    v_reg c_ventas%ROWTYPE;
BEGIN
    FOR v_reg IN c_ventas LOOP
        DBMS_OUTPUT.PUT_LINE('Venta #' || v_reg.id || ' | ' || v_reg.cliente || ' | ' || v_reg.usuario || ' | ' || TO_CHAR(v_reg.fecha,'DD/MM/YYYY') || ' | ' || v_reg.estado || ' | ' || v_reg.total || '€');
    END LOOP;
END;
/

-- 2
DECLARE
    CURSOR c_activas IS
        SELECT v.id, c.nombre, v.total
        FROM ventas v
        JOIN clientes c ON c.id = v.cliente_id
        WHERE v.estado = 'activo';
    v_id     ventas.id%TYPE;
    v_nombre clientes.nombre%TYPE;
    v_total  ventas.total%TYPE;
BEGIN
    OPEN c_activas;
    LOOP
        FETCH c_activas INTO v_id, v_nombre, v_total;
        EXIT WHEN c_activas%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE('Venta #' || v_id || ' — ' || v_nombre || ' — ' || v_total || '€');
    END LOOP;
    CLOSE c_activas;
END;
/

-- 3
DECLARE
    CURSOR c_por_estado IS
        SELECT estado, COUNT(*) AS num, SUM(total) AS suma
        FROM ventas
        GROUP BY estado
        ORDER BY estado;
    v_estado ventas.estado%TYPE;
    v_num    NUMBER;
    v_suma   NUMBER;
BEGIN
    OPEN c_por_estado;
    LOOP
        FETCH c_por_estado INTO v_estado, v_num, v_suma;
        EXIT WHEN c_por_estado%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE('Estado: ' || v_estado || ' → ' || v_num || ' venta(s) → ' || NVL(v_suma,0) || '€');
    END LOOP;
    CLOSE c_por_estado;
END;
/

-- 4
DECLARE
    CURSOR c_2026 IS
        SELECT id, fecha, total, estado FROM ventas
        WHERE EXTRACT(YEAR FROM fecha) = 2026
        ORDER BY fecha;
    v_reg c_2026%ROWTYPE;
BEGIN
    FOR v_reg IN c_2026 LOOP
        DBMS_OUTPUT.PUT_LINE('Venta #' || v_reg.id || ' | ' || TO_CHAR(v_reg.fecha,'DD/MM/YYYY') || ' | ' || v_reg.total || '€ | ' || v_reg.estado);
    END LOOP;
END;
/

-- 5. Ingresos totales (suma de todas las ventas activas)
DECLARE
    CURSOR c_total IS SELECT SUM(total) AS ingresos FROM ventas WHERE estado = 'activo';
    v_ingresos NUMBER;
BEGIN
    OPEN c_total;
    FETCH c_total INTO v_ingresos;
    CLOSE c_total;
    DBMS_OUTPUT.PUT_LINE('Ingresos totales (ventas activas): ' || NVL(v_ingresos,0) || '€');
END;
/

-- ============================================================
--  TABLA: DETALLE_VENTA
-- ============================================================

-- 1
DECLARE
    CURSOR c_detalle IS
        SELECT dv.id, v.id AS venta, p.nombre AS producto, dv.cantidad, dv.precio_unitario,
               dv.cantidad * dv.precio_unitario AS subtotal
        FROM detalle_venta dv
        JOIN ventas v    ON v.id = dv.venta_id
        JOIN productos p ON p.id = dv.producto_id
        ORDER BY dv.id;
    v_reg c_detalle%ROWTYPE;
BEGIN
    FOR v_reg IN c_detalle LOOP
        DBMS_OUTPUT.PUT_LINE('Detalle #' || v_reg.id || ' | Venta #' || v_reg.venta || ' | ' || v_reg.producto || ' | x' || v_reg.cantidad || ' | ' || v_reg.precio_unitario || '€/u | Subtotal: ' || v_reg.subtotal || '€');
    END LOOP;
END;
/

-- 2
DECLARE
    CURSOR c_multi IS
        SELECT dv.id, p.nombre, dv.cantidad, dv.precio_unitario
        FROM detalle_venta dv
        JOIN productos p ON p.id = dv.producto_id
        WHERE dv.cantidad > 1;
    v_reg c_multi%ROWTYPE;
BEGIN
    OPEN c_multi;
    LOOP
        FETCH c_multi INTO v_reg;
        EXIT WHEN c_multi%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE('Línea #' || v_reg.id || ' | ' || v_reg.nombre || ' | x' || v_reg.cantidad || ' | ' || v_reg.precio_unitario || '€');
    END LOOP;
    CLOSE c_multi;
END;
/

-- 3
DECLARE
    CURSOR c_subtotal IS
        SELECT dv.id, p.nombre, dv.cantidad * dv.precio_unitario AS subtotal
        FROM detalle_venta dv
        JOIN productos p ON p.id = dv.producto_id
        ORDER BY subtotal DESC;
    v_id      detalle_venta.id%TYPE;
    v_nombre  productos.nombre%TYPE;
    v_sub     NUMBER;
BEGIN
    OPEN c_subtotal;
    LOOP
        FETCH c_subtotal INTO v_id, v_nombre, v_sub;
        EXIT WHEN c_subtotal%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE('Línea #' || v_id || ' | ' || v_nombre || ' → Subtotal: ' || v_sub || '€');
    END LOOP;
    CLOSE c_subtotal;
END;
/

-- 4
DECLARE
    CURSOR c_lineas IS
        SELECT venta_id, COUNT(*) AS lineas FROM detalle_venta GROUP BY venta_id ORDER BY venta_id;
    v_venta  detalle_venta.venta_id%TYPE;
    v_lineas NUMBER;
BEGIN
    FOR v_venta, v_lineas IN c_lineas LOOP
        DBMS_OUTPUT.PUT_LINE('Venta #' || v_venta || ' → ' || v_lineas || ' línea(s)');
    END LOOP;
END;
/

-- 5
DECLARE
    CURSOR c_gran_total IS
        SELECT SUM(cantidad * precio_unitario) AS gran_total FROM detalle_venta;
    v_total NUMBER;
BEGIN
    OPEN c_gran_total;
    FETCH c_gran_total INTO v_total;
    CLOSE c_gran_total;
    DBMS_OUTPUT.PUT_LINE('Gran total de todas las líneas: ' || NVL(v_total,0) || '€');
END;
/
