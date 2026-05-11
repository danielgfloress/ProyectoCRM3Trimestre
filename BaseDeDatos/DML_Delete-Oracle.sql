-- ============================================================
--  GymCRM — DML DELETE Oracle (5 registros por tabla)
--  Orden obligatorio: primero hijos, luego padres (respetar FK)
-- ============================================================

-- DETALLE_VENTA (depende de ventas y productos)
BEGIN
    DELETE FROM detalle_venta WHERE id = 1;
    DELETE FROM detalle_venta WHERE id = 2;
    DELETE FROM detalle_venta WHERE id = 3;
    DELETE FROM detalle_venta WHERE id = 4;
    DELETE FROM detalle_venta WHERE id = 5;
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error DELETE detalle_venta: ' || SQLERRM);
END;
/

-- VENTAS (depende de clientes y usuarios)
BEGIN
    DELETE FROM ventas WHERE id = 1;
    DELETE FROM ventas WHERE id = 2;
    DELETE FROM ventas WHERE id = 3;
    DELETE FROM ventas WHERE id = 4;
    DELETE FROM ventas WHERE id = 5;
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error DELETE ventas: ' || SQLERRM);
END;
/

-- PRODUCTOS
BEGIN
    DELETE FROM productos WHERE id = 1;
    DELETE FROM productos WHERE id = 2;
    DELETE FROM productos WHERE id = 3;
    DELETE FROM productos WHERE id = 4;
    DELETE FROM productos WHERE id = 5;
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error DELETE productos: ' || SQLERRM);
END;
/

-- USUARIOS
BEGIN
    DELETE FROM usuarios WHERE id = 1;
    DELETE FROM usuarios WHERE id = 2;
    DELETE FROM usuarios WHERE id = 3;
    DELETE FROM usuarios WHERE id = 4;
    DELETE FROM usuarios WHERE id = 5;
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error DELETE usuarios: ' || SQLERRM);
END;
/

-- CLIENTES (último, no tiene dependencias hacia arriba)
BEGIN
    DELETE FROM clientes WHERE id = 1;
    DELETE FROM clientes WHERE id = 2;
    DELETE FROM clientes WHERE id = 3;
    DELETE FROM clientes WHERE id = 4;
    DELETE FROM clientes WHERE id = 5;
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error DELETE clientes: ' || SQLERRM);
END;
/

SELECT * FROM detalle_venta;
SELECT * FROM ventas;
SELECT * FROM productos;
SELECT * FROM usuarios;
SELECT * FROM clientes;
