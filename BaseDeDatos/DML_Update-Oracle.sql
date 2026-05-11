-- ============================================================
--  GymCRM — DML UPDATE Oracle (5 registros por tabla)
-- ============================================================

-- CLIENTES
BEGIN
    UPDATE clientes SET telefono  = '699000001'                             WHERE id = 1;
    UPDATE clientes SET direccion = 'Calle Nueva 1, Madrid'                 WHERE id = 2;
    UPDATE clientes SET email     = 'marcos.lopez@xtart.com'                WHERE id = 3;
    UPDATE clientes SET nombre    = 'Sofía Ruiz García'                     WHERE id = 4;
    UPDATE clientes SET fecha_alta = TO_DATE('2026-05-10','YYYY-MM-DD')     WHERE id = 5;
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error UPDATE clientes: ' || SQLERRM);
END;
/

-- USUARIOS
BEGIN
    UPDATE usuarios SET rol           = 'entrenador'              WHERE id = 2;
    UPDATE usuarios SET email         = 'marta.diaz@gymtonic.com' WHERE id = 3;
    UPDATE usuarios SET password_hash = 'hash4_nuevo'             WHERE id = 4;
    UPDATE usuarios SET nombre        = 'Raúl Jiménez Pérez'      WHERE id = 4;
    UPDATE usuarios SET email         = 'elena.m@gymtonic.com'    WHERE id = 5;
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error UPDATE usuarios: ' || SQLERRM);
END;
/

-- PRODUCTOS
BEGIN
    UPDATE productos SET precio      = 36.99                              WHERE id = 1;
    UPDATE productos SET precio      = 92.99                              WHERE id = 2;
    UPDATE productos SET descripcion = 'Sesión grupal de yoga 90 minutos' WHERE id = 3;
    UPDATE productos SET nombre      = 'Clase de Spinning Avanzado'       WHERE id = 4;
    UPDATE productos SET precio      = 45.00                              WHERE id = 5;
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error UPDATE productos: ' || SQLERRM);
END;
/

-- VENTAS
BEGIN
    UPDATE ventas SET estado = 'cancelado'                              WHERE id = 1;
    UPDATE ventas SET total  = 100.00                                   WHERE id = 2;
    UPDATE ventas SET estado = 'activo'                                 WHERE id = 3;
    UPDATE ventas SET fecha  = TO_DATE('2026-04-10','YYYY-MM-DD')       WHERE id = 4;
    UPDATE ventas SET total  = 0.00                                     WHERE id = 5;
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error UPDATE ventas: ' || SQLERRM);
END;
/

-- DETALLE_VENTA
BEGIN
    UPDATE detalle_venta SET cantidad        = 2     WHERE id = 1;
    UPDATE detalle_venta SET precio_unitario = 85.00 WHERE id = 2;
    UPDATE detalle_venta SET cantidad        = 3     WHERE id = 3;
    UPDATE detalle_venta SET precio_unitario = 38.00 WHERE id = 4;
    UPDATE detalle_venta SET cantidad        = 2     WHERE id = 5;
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error UPDATE detalle_venta: ' || SQLERRM);
END;
/

SELECT * FROM clientes;
SELECT * FROM usuarios;
SELECT * FROM productos;
SELECT * FROM ventas;
SELECT * FROM detalle_venta;
