CREATE OR REPLACE PROCEDURE sp_clientes_antiguedad AS
    CURSOR c_cli IS
        SELECT id, nombre, fecha_alta,
               TRUNC(SYSDATE - fecha_alta) AS dias
        FROM clientes
        ORDER BY fecha_alta;
    v_id        clientes.id%TYPE;
    v_nombre    clientes.nombre%TYPE;
    v_alta      clientes.fecha_alta%TYPE;
    v_dias      NUMBER;
    v_etiqueta  VARCHAR2(20);
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Antigüedad de clientes ===');
    OPEN c_cli;
    LOOP
        FETCH c_cli INTO v_id, v_nombre, v_alta, v_dias;
        EXIT WHEN c_cli%NOTFOUND;
        IF v_dias > 365 THEN
            v_etiqueta := 'VETERANO';
        ELSIF v_dias > 90 THEN
            v_etiqueta := 'REGULAR';
        ELSE
            v_etiqueta := 'NUEVO';
        END IF;
        DBMS_OUTPUT.PUT_LINE(v_nombre || ' | Alta: ' || TO_CHAR(v_alta,'DD/MM/YYYY') || ' | ' || v_dias || ' días | ' || v_etiqueta);
    END LOOP;
    CLOSE c_cli;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error en sp_clientes_antiguedad: ' || SQLERRM);
END sp_clientes_antiguedad;
/

EXEC sp_clientes_antiguedad;

CREATE OR REPLACE PROCEDURE sp_actualizar_direccion (
    p_id         IN clientes.id%TYPE,
    p_direccion  IN clientes.direccion%TYPE
) AS
    v_nombre clientes.nombre%TYPE;
    v_existe NUMBER := 0;
BEGIN
    SELECT COUNT(*) INTO v_existe FROM clientes WHERE id = p_id;

    IF v_existe = 0 THEN
        DBMS_OUTPUT.PUT_LINE('Error: No existe ningún cliente con ID ' || p_id);
        RETURN;
    END IF;

    SELECT nombre INTO v_nombre FROM clientes WHERE id = p_id;
    UPDATE clientes SET direccion = INITCAP(TRIM(p_direccion)) WHERE id = p_id;
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Dirección actualizada para: ' || v_nombre || ' → ' || INITCAP(TRIM(p_direccion)));
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('Error: Cliente no encontrado.');
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error en sp_actualizar_direccion: ' || SQLERRM);
END sp_actualizar_direccion;
/

EXEC sp_actualizar_direccion(1, 'calle gran vía 45, madrid');

CREATE OR REPLACE FUNCTION fn_clientes_por_anio (
    p_anio IN NUMBER
) RETURN NUMBER AS
    v_total NUMBER := 0;
BEGIN
    SELECT COUNT(*) INTO v_total
    FROM clientes
    WHERE EXTRACT(YEAR FROM fecha_alta) = p_anio;
    RETURN v_total;
EXCEPTION
    WHEN OTHERS THEN
        RETURN -1;
END fn_clientes_por_anio;
/

DECLARE
    v_res NUMBER;
BEGIN
    v_res := fn_clientes_por_anio(2026);
    DBMS_OUTPUT.PUT_LINE('Clientes dados de alta en 2026: ' || v_res);
END;
/

CREATE OR REPLACE FUNCTION fn_nombre_cliente (
    p_id IN clientes.id%TYPE
) RETURN VARCHAR2 AS
    v_nombre clientes.nombre%TYPE;
BEGIN
    SELECT UPPER(TRIM(nombre)) INTO v_nombre
    FROM clientes WHERE id = p_id;
    RETURN v_nombre;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN 'CLIENTE NO ENCONTRADO';
    WHEN OTHERS THEN
        RETURN 'ERROR: ' || SQLERRM;
END fn_nombre_cliente;
/

DECLARE
    v_res VARCHAR2(100);
BEGIN
    v_res := fn_nombre_cliente(2);
    DBMS_OUTPUT.PUT_LINE('Nombre formateado: ' || v_res);
END;
/

CREATE OR REPLACE PROCEDURE sp_usuarios_por_rol AS
    CURSOR c_roles IS SELECT DISTINCT rol FROM usuarios ORDER BY rol;
    CURSOR c_usr (p_rol VARCHAR2) IS
        SELECT nombre, email FROM usuarios WHERE rol = p_rol ORDER BY nombre;
    v_rol    usuarios.rol%TYPE;
    v_nombre usuarios.nombre%TYPE;
    v_email  usuarios.email%TYPE;
BEGIN
    OPEN c_roles;
    LOOP
        FETCH c_roles INTO v_rol;
        EXIT WHEN c_roles%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE('--- ROL: ' || UPPER(v_rol) || ' ---');
        OPEN c_usr(v_rol);
        LOOP
            FETCH c_usr INTO v_nombre, v_email;
            EXIT WHEN c_usr%NOTFOUND;
            DBMS_OUTPUT.PUT_LINE('  · ' || v_nombre || ' (' || v_email || ')');
        END LOOP;
        CLOSE c_usr;
    END LOOP;
    CLOSE c_roles;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error en sp_usuarios_por_rol: ' || SQLERRM);
END sp_usuarios_por_rol;
/

EXEC sp_usuarios_por_rol;

CREATE OR REPLACE PROCEDURE sp_cambiar_rol (
    p_id  IN usuarios.id%TYPE,
    p_rol IN usuarios.rol%TYPE
) AS
    v_nombre    usuarios.nombre%TYPE;
    v_rol_viejo usuarios.rol%TYPE;
    v_existe    NUMBER := 0;
BEGIN
    SELECT COUNT(*) INTO v_existe FROM usuarios WHERE id = p_id;

    IF v_existe = 0 THEN
        DBMS_OUTPUT.PUT_LINE('Error: Usuario con ID ' || p_id || ' no existe.');
        RETURN;
    END IF;

    IF p_rol NOT IN ('admin','recepcionista','entrenador') THEN
        DBMS_OUTPUT.PUT_LINE('Error: Rol no válido → ' || p_rol);
        RETURN;
    END IF;

    SELECT nombre, rol INTO v_nombre, v_rol_viejo FROM usuarios WHERE id = p_id;
    UPDATE usuarios SET rol = LOWER(TRIM(p_rol)) WHERE id = p_id;
    COMMIT;
    DBMS_OUTPUT.PUT_LINE(v_nombre || ': rol cambiado de [' || v_rol_viejo || '] a [' || p_rol || ']');
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('Error: Usuario no encontrado.');
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error en sp_cambiar_rol: ' || SQLERRM);
END sp_cambiar_rol;
/

EXEC sp_cambiar_rol(2, 'entrenador');

CREATE OR REPLACE FUNCTION fn_contar_por_rol (
    p_rol IN usuarios.rol%TYPE
) RETURN NUMBER AS
    v_total NUMBER := 0;
BEGIN
    SELECT COUNT(*) INTO v_total FROM usuarios WHERE LOWER(rol) = LOWER(p_rol);
    RETURN v_total;
EXCEPTION
    WHEN OTHERS THEN RETURN -1;
END fn_contar_por_rol;
/

DECLARE
    v_res NUMBER;
BEGIN
    v_res := fn_contar_por_rol('entrenador');
    DBMS_OUTPUT.PUT_LINE('Entrenadores: ' || v_res);
END;
/

CREATE OR REPLACE FUNCTION fn_email_usuario_existe (
    p_email IN usuarios.email%TYPE
) RETURN NUMBER AS
    v_total NUMBER := 0;
BEGIN
    SELECT COUNT(*) INTO v_total FROM usuarios WHERE LOWER(email) = LOWER(TRIM(p_email));
    RETURN v_total;
EXCEPTION
    WHEN OTHERS THEN RETURN -1;
END fn_email_usuario_existe;
/

DECLARE
    v_res NUMBER;
BEGIN
    v_res := fn_email_usuario_existe('ana@gymtonic.com');
    IF v_res = 1 THEN
        DBMS_OUTPUT.PUT_LINE('El email ya está registrado.');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Email disponible.');
    END IF;
END;
/

CREATE OR REPLACE PROCEDURE sp_productos_categoria AS
    CURSOR c_cats IS SELECT DISTINCT categoria FROM productos ORDER BY categoria;
    CURSOR c_prod (p_cat VARCHAR2) IS
        SELECT nombre, precio FROM productos WHERE categoria = p_cat ORDER BY precio DESC;
    v_cat    productos.categoria%TYPE;
    v_nombre productos.nombre%TYPE;
    v_precio productos.precio%TYPE;
BEGIN
    OPEN c_cats;
    LOOP
        FETCH c_cats INTO v_cat;
        EXIT WHEN c_cats%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE('=== Categoría: ' || UPPER(v_cat) || ' ===');
        OPEN c_prod(v_cat);
        LOOP
            FETCH c_prod INTO v_nombre, v_precio;
            EXIT WHEN c_prod%NOTFOUND;
            DBMS_OUTPUT.PUT_LINE('  · ' || RPAD(v_nombre, 25) || TO_CHAR(v_precio,'FM999.99') || '€');
        END LOOP;
        CLOSE c_prod;
    END LOOP;
    CLOSE c_cats;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error en sp_productos_categoria: ' || SQLERRM);
END sp_productos_categoria;
/

EXEC sp_productos_categoria;

CREATE OR REPLACE PROCEDURE sp_descuento_categoria (
    p_categoria  IN productos.categoria%TYPE,
    p_descuento  IN NUMBER
) AS
    CURSOR c_prod IS SELECT id, nombre, precio FROM productos WHERE categoria = p_categoria;
    v_id        productos.id%TYPE;
    v_nombre    productos.nombre%TYPE;
    v_precio    productos.precio%TYPE;
    v_nuevo     NUMBER;
    v_contador  NUMBER := 0;
BEGIN
    IF p_descuento <= 0 OR p_descuento >= 100 THEN
        DBMS_OUTPUT.PUT_LINE('Error: El descuento debe estar entre 1 y 99.');
        RETURN;
    END IF;

    OPEN c_prod;
    LOOP
        FETCH c_prod INTO v_id, v_nombre, v_precio;
        EXIT WHEN c_prod%NOTFOUND;
        v_nuevo := ROUND(v_precio * (1 - p_descuento / 100), 2);
        UPDATE productos SET precio = v_nuevo WHERE id = v_id;
        DBMS_OUTPUT.PUT_LINE(v_nombre || ': ' || v_precio || '€ → ' || v_nuevo || '€ (-' || p_descuento || '%)');
        v_contador := v_contador + 1;
    END LOOP;
    CLOSE c_prod;
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Descuento aplicado a ' || v_contador || ' producto(s).');
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error en sp_descuento_categoria: ' || SQLERRM);
END sp_descuento_categoria;
/

EXEC sp_descuento_categoria('cuota', 10);

CREATE OR REPLACE FUNCTION fn_precio_medio_categoria (
    p_categoria IN productos.categoria%TYPE
) RETURN NUMBER AS
    v_media NUMBER := 0;
BEGIN
    SELECT ROUND(AVG(precio), 2) INTO v_media
    FROM productos WHERE LOWER(categoria) = LOWER(p_categoria);
    IF v_media IS NULL THEN RETURN 0; END IF;
    RETURN v_media;
EXCEPTION
    WHEN OTHERS THEN RETURN -1;
END fn_precio_medio_categoria;
/

DECLARE
    v_res NUMBER;
BEGIN
    v_res := fn_precio_medio_categoria('cuota');
    DBMS_OUTPUT.PUT_LINE('Precio medio cuotas: ' || v_res || '€');
END;
/

CREATE OR REPLACE FUNCTION fn_producto_mas_caro RETURN VARCHAR2 AS
    v_nombre productos.nombre%TYPE;
BEGIN
    SELECT nombre INTO v_nombre
    FROM productos
    WHERE precio = (SELECT MAX(precio) FROM productos)
    AND ROWNUM = 1;
    RETURN v_nombre;
EXCEPTION
    WHEN NO_DATA_FOUND THEN RETURN 'Sin productos';
    WHEN OTHERS THEN RETURN 'ERROR: ' || SQLERRM;
END fn_producto_mas_caro;
/

DECLARE
    v_res VARCHAR2(150);
BEGIN
    v_res := fn_producto_mas_caro();
    DBMS_OUTPUT.PUT_LINE('Producto más caro: ' || v_res);
END;
/

CREATE OR REPLACE PROCEDURE sp_resumen_ventas AS
    CURSOR c_estados IS SELECT DISTINCT estado FROM ventas ORDER BY estado;
    CURSOR c_ventas_estado (p_estado VARCHAR2) IS
        SELECT v.id, c.nombre AS cliente, v.total, v.fecha
        FROM ventas v
        JOIN clientes c ON c.id = v.cliente_id
        WHERE v.estado = p_estado
        ORDER BY v.fecha;
    v_estado   ventas.estado%TYPE;
    v_id       ventas.id%TYPE;
    v_cliente  clientes.nombre%TYPE;
    v_total    ventas.total%TYPE;
    v_fecha    ventas.fecha%TYPE;
    v_suma     NUMBER;
BEGIN
    OPEN c_estados;
    LOOP
        FETCH c_estados INTO v_estado;
        EXIT WHEN c_estados%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE('');
        DBMS_OUTPUT.PUT_LINE('=== ESTADO: ' || UPPER(v_estado) || ' ===');
        v_suma := 0;
        OPEN c_ventas_estado(v_estado);
        LOOP
            FETCH c_ventas_estado INTO v_id, v_cliente, v_total, v_fecha;
            EXIT WHEN c_ventas_estado%NOTFOUND;
            DBMS_OUTPUT.PUT_LINE('  Venta #' || v_id || ' | ' || v_cliente || ' | ' || TO_CHAR(v_fecha,'DD/MM/YYYY') || ' | ' || v_total || '€');
            v_suma := v_suma + v_total;
        END LOOP;
        CLOSE c_ventas_estado;
        DBMS_OUTPUT.PUT_LINE('  Total ' || v_estado || ': ' || v_suma || '€');
    END LOOP;
    CLOSE c_estados;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error en sp_resumen_ventas: ' || SQLERRM);
END sp_resumen_ventas;
/

EXEC sp_resumen_ventas;

CREATE OR REPLACE PROCEDURE sp_cancelar_ventas_antiguas (
    p_dias IN NUMBER
) AS
    CURSOR c_pend IS
        SELECT id, cliente_id, fecha, TRUNC(SYSDATE - fecha) AS antiguedad
        FROM ventas
        WHERE estado = 'pendiente'
          AND TRUNC(SYSDATE - fecha) > p_dias;
    v_id         ventas.id%TYPE;
    v_cliente_id ventas.cliente_id%TYPE;
    v_fecha      ventas.fecha%TYPE;
    v_dias_ant   NUMBER;
    v_contador   NUMBER := 0;
BEGIN
    DBMS_OUTPUT.PUT_LINE('Cancelando ventas pendientes con más de ' || p_dias || ' días...');
    OPEN c_pend;
    LOOP
        FETCH c_pend INTO v_id, v_cliente_id, v_fecha, v_dias_ant;
        EXIT WHEN c_pend%NOTFOUND;
        UPDATE ventas SET estado = 'cancelado' WHERE id = v_id;
        DBMS_OUTPUT.PUT_LINE('  Venta #' || v_id || ' cancelada (' || v_dias_ant || ' días pendiente)');
        v_contador := v_contador + 1;
    END LOOP;
    CLOSE c_pend;
    IF v_contador = 0 THEN
        DBMS_OUTPUT.PUT_LINE('No hay ventas pendientes que superen ' || p_dias || ' días.');
    ELSE
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Total canceladas: ' || v_contador);
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error en sp_cancelar_ventas_antiguas: ' || SQLERRM);
END sp_cancelar_ventas_antiguas;
/

EXEC sp_cancelar_ventas_antiguas(30);

CREATE OR REPLACE FUNCTION fn_total_cliente (
    p_cliente_id IN ventas.cliente_id%TYPE
) RETURN NUMBER AS
    v_total NUMBER := 0;
BEGIN
    SELECT NVL(SUM(total), 0) INTO v_total
    FROM ventas
    WHERE cliente_id = p_cliente_id AND estado = 'activo';
    RETURN v_total;
EXCEPTION
    WHEN OTHERS THEN RETURN -1;
END fn_total_cliente;
/

DECLARE
    v_res NUMBER;
BEGIN
    v_res := fn_total_cliente(1);
    DBMS_OUTPUT.PUT_LINE('Total facturado al cliente 1: ' || v_res || '€');
END;
/

CREATE OR REPLACE FUNCTION fn_estado_mas_frecuente RETURN VARCHAR2 AS
    v_estado ventas.estado%TYPE;
BEGIN
    SELECT estado INTO v_estado
    FROM (
        SELECT estado, COUNT(*) AS cnt
        FROM ventas
        GROUP BY estado
        ORDER BY cnt DESC
    )
    WHERE ROWNUM = 1;
    RETURN v_estado;
EXCEPTION
    WHEN NO_DATA_FOUND THEN RETURN 'Sin ventas';
    WHEN OTHERS THEN RETURN 'ERROR: ' || SQLERRM;
END fn_estado_mas_frecuente;
/

DECLARE
    v_res VARCHAR2(50);
BEGIN
    v_res := fn_estado_mas_frecuente();
    DBMS_OUTPUT.PUT_LINE('Estado más frecuente en ventas: ' || v_res);
END;
/

CREATE OR REPLACE PROCEDURE sp_desglose_venta (
    p_venta_id IN detalle_venta.venta_id%TYPE
) AS
    CURSOR c_lineas IS
        SELECT p.nombre, dv.cantidad, dv.precio_unitario,
               dv.cantidad * dv.precio_unitario AS subtotal
        FROM detalle_venta dv
        JOIN productos p ON p.id = dv.producto_id
        WHERE dv.venta_id = p_venta_id;
    v_nombre    productos.nombre%TYPE;
    v_cantidad  detalle_venta.cantidad%TYPE;
    v_precio    detalle_venta.precio_unitario%TYPE;
    v_subtotal  NUMBER;
    v_total     NUMBER := 0;
    v_existe    NUMBER := 0;
BEGIN
    SELECT COUNT(*) INTO v_existe FROM ventas WHERE id = p_venta_id;
    IF v_existe = 0 THEN
        DBMS_OUTPUT.PUT_LINE('Error: Venta #' || p_venta_id || ' no existe.');
        RETURN;
    END IF;

    DBMS_OUTPUT.PUT_LINE('=== Desglose Venta #' || p_venta_id || ' ===');
    OPEN c_lineas;
    LOOP
        FETCH c_lineas INTO v_nombre, v_cantidad, v_precio, v_subtotal;
        EXIT WHEN c_lineas%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE('  · ' || RPAD(v_nombre,25) || 'x' || v_cantidad || '  ' || v_precio || '€/u  →  ' || v_subtotal || '€');
        v_total := v_total + v_subtotal;
    END LOOP;
    CLOSE c_lineas;
    DBMS_OUTPUT.PUT_LINE('  TOTAL: ' || v_total || '€');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error en sp_desglose_venta: ' || SQLERRM);
END sp_desglose_venta;
/

EXEC sp_desglose_venta(1);

CREATE OR REPLACE PROCEDURE sp_recalcular_totales AS
    CURSOR c_ventas IS SELECT id FROM ventas;
    v_venta_id  ventas.id%TYPE;
    v_nuevo_total NUMBER;
    v_contador  NUMBER := 0;
BEGIN
    DBMS_OUTPUT.PUT_LINE('Recalculando totales de ventas...');
    OPEN c_ventas;
    LOOP
        FETCH c_ventas INTO v_venta_id;
        EXIT WHEN c_ventas%NOTFOUND;
        SELECT NVL(SUM(cantidad * precio_unitario), 0)
        INTO v_nuevo_total
        FROM detalle_venta
        WHERE venta_id = v_venta_id;

        UPDATE ventas SET total = v_nuevo_total WHERE id = v_venta_id;
        DBMS_OUTPUT.PUT_LINE('  Venta #' || v_venta_id || ' → Total recalculado: ' || v_nuevo_total || '€');
        v_contador := v_contador + 1;
    END LOOP;
    CLOSE c_ventas;
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Totales actualizados: ' || v_contador || ' venta(s).');
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error en sp_recalcular_totales: ' || SQLERRM);
END sp_recalcular_totales;
/

EXEC sp_recalcular_totales;

CREATE OR REPLACE FUNCTION fn_subtotal_linea (
    p_id IN detalle_venta.id%TYPE
) RETURN NUMBER AS
    v_sub NUMBER := 0;
BEGIN
    SELECT cantidad * precio_unitario INTO v_sub
    FROM detalle_venta WHERE id = p_id;
    RETURN v_sub;
EXCEPTION
    WHEN NO_DATA_FOUND THEN RETURN 0;
    WHEN OTHERS THEN RETURN -1;
END fn_subtotal_linea;
/

DECLARE
    v_res NUMBER;
BEGIN
    v_res := fn_subtotal_linea(1);
    DBMS_OUTPUT.PUT_LINE('Subtotal línea 1: ' || v_res || '€');
END;
/

CREATE OR REPLACE FUNCTION fn_lineas_por_venta (
    p_venta_id IN detalle_venta.venta_id%TYPE
) RETURN NUMBER AS
    v_total NUMBER := 0;
BEGIN
    SELECT COUNT(*) INTO v_total FROM detalle_venta WHERE venta_id = p_venta_id;
    RETURN v_total;
EXCEPTION
    WHEN OTHERS THEN RETURN -1;
END fn_lineas_por_venta;
/

DECLARE
    v_res NUMBER;
BEGIN
    v_res := fn_lineas_por_venta(2);
    DBMS_OUTPUT.PUT_LINE('Líneas en venta #2: ' || v_res);
END;
/
