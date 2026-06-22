CREATE DATABASE IF NOT EXISTS abarroteria_kinal_in4bv;

USE abarroteria_kinal_in4bv;

CREATE USER IF NOT EXISTS 'IN4BV'@'localhost' IDENTIFIED BY '%IndiVA4';
GRANT ALL PRIVILEGES ON abarroteria_kinal_in4bv.* TO 'IN4BV'@'localhost';
FLUSH PRIVILEGES;

CREATE TABLE roles (
    id_rol     VARCHAR(36) NOT NULL,
    nombre_rol VARCHAR(50) NOT NULL,
    PRIMARY KEY (id_rol)
);

-- "usaurios" es el nombre original, no corregir (coincide con el codigo Java)
CREATE TABLE usaurios (
    id_usuario VARCHAR(36)  NOT NULL,
    nombre     VARCHAR(100) NOT NULL,
    apellido   VARCHAR(100) NOT NULL,
    email      VARCHAR(150) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    id_rol     VARCHAR(36)  NOT NULL,
    PRIMARY KEY (id_usuario),
    FOREIGN KEY (id_rol) REFERENCES roles (id_rol)
);

CREATE TABLE Direcciones (
    id_direccion VARCHAR(60) NOT NULL,
    ciudad       VARCHAR(60),
    zona         VARCHAR(4),
    numero_casa  VARCHAR(60),
    colonia      VARCHAR(60),
    calle        VARCHAR(60),
    PRIMARY KEY (id_direccion)
);

CREATE TABLE Clientes (
    id_cliente   VARCHAR(60) NOT NULL,
    id_direccion VARCHAR(60),
    nombre       VARCHAR(60),
    apellido     VARCHAR(60),
    PRIMARY KEY (id_cliente),
    FOREIGN KEY (id_direccion) REFERENCES Direcciones (id_direccion)
);

CREATE TABLE Telefonos (
    id_telefono VARCHAR(60) NOT NULL,
    id_cliente  VARCHAR(60),
    telefono    VARCHAR(20),
    PRIMARY KEY (id_telefono),
    FOREIGN KEY (id_cliente) REFERENCES Clientes (id_cliente)
);

-- "id_productos" con s, coincide con el codigo Java
CREATE TABLE Productos (
    id_productos    VARCHAR(60)   NOT NULL,
    nombre_producto VARCHAR(60),
    stock           INT           NOT NULL DEFAULT 0,
    precio          DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id_productos)
);

CREATE TABLE Facturas (
    id_factura VARCHAR(60)   NOT NULL,
    id_cliente VARCHAR(60),
    monto      DECIMAL(10,2),
    fecha      DATETIME,
    PRIMARY KEY (id_factura),
    FOREIGN KEY (id_cliente) REFERENCES Clientes (id_cliente)
);

CREATE TABLE Detalles_facturas (
    id_detalle_factura VARCHAR(60) NOT NULL,
    id_productos       VARCHAR(60),
    id_factura         VARCHAR(60),
    cantidad_comprada  INT,
    PRIMARY KEY (id_detalle_factura),
    FOREIGN KEY (id_productos) REFERENCES Productos (id_productos),
    FOREIGN KEY (id_factura)   REFERENCES Facturas (id_factura)
);

CREATE OR REPLACE VIEW v_info_factura AS
SELECT
    CONCAT(c.nombre, ' ', c.apellido) AS nombre_completo,
    f.monto,
    f.fecha
FROM Clientes AS c
INNER JOIN Facturas AS f ON f.id_cliente = c.id_cliente;

CREATE OR REPLACE VIEW v_ventas_por_ciudad AS
SELECT
    d.ciudad,
    d.zona,
    COUNT(f.id_factura) AS total_facturas,
    SUM(f.monto)        AS ingresos_total
FROM Direcciones AS d
INNER JOIN Clientes AS c ON d.id_direccion = c.id_direccion
INNER JOIN Facturas AS f ON c.id_cliente   = f.id_cliente
GROUP BY d.ciudad, d.zona;

CREATE OR REPLACE VIEW v_estado_stock_productos AS
SELECT
    p.nombre_producto,
    p.stock                                         AS inventario_actual,
    IFNULL(SUM(df.cantidad_comprada), 0)            AS unidades_vendidas,
    p.precio                                        AS precio_unitario,
    IFNULL(SUM(df.cantidad_comprada * p.precio), 0) AS total_generado,
    CASE
        WHEN p.stock = 0   THEN 'NO HAY PRODUCTOS DISPONIBLES'
        WHEN p.stock <= 25 THEN 'POCAS UNIDADES DISPONIBLES'
        WHEN p.stock <= 50 THEN 'SE RECOMIENDA REVISAR'
        ELSE                    'VARIAS UNIDADES DISPONIBLES'
    END AS estado_stock
FROM Productos AS p
LEFT JOIN Detalles_facturas AS df ON p.id_productos = df.id_productos
GROUP BY p.nombre_producto, p.stock, p.precio
ORDER BY total_generado DESC;

CREATE OR REPLACE VIEW v_vista_abarroteria AS
SELECT
    f.id_factura,
    CONCAT(c.nombre, ' ', c.apellido)              AS cliente,
    t.telefono,
    CONCAT(d.calle, ', zona ', d.zona, ', ', d.ciudad) AS direccion,
    p.nombre_producto,
    df.cantidad_comprada                            AS cantidad,
    p.precio
FROM Facturas f
INNER JOIN Clientes          c  ON f.id_cliente    = c.id_cliente
INNER JOIN Telefonos         t  ON c.id_cliente    = t.id_cliente
INNER JOIN Direcciones       d  ON c.id_direccion  = d.id_direccion
INNER JOIN Detalles_facturas df ON f.id_factura    = df.id_factura
INNER JOIN Productos         p  ON df.id_productos = p.id_productos;

DELIMITER $

CREATE PROCEDURE sp_actualizar_precio_producto(
    IN p_id_producto  VARCHAR(60),
    IN p_nuevo_precio DECIMAL(10,2)
)
BEGIN
    UPDATE Productos
    SET    precio = p_nuevo_precio
    WHERE  id_productos = p_id_producto;
END $

CREATE PROCEDURE sp_crear_factura_cliente(
    IN p_id_factura VARCHAR(60),
    IN p_id_cliente VARCHAR(60),
    IN p_monto      DECIMAL(10,2)
)
BEGIN
    DECLARE v_existe_cliente INT;

    SELECT COUNT(*) INTO v_existe_cliente
    FROM Clientes
    WHERE id_cliente = p_id_cliente;

    -- Solo inserta si el cliente existe, de lo contrario lanza error
    IF v_existe_cliente > 0 THEN
        INSERT INTO Facturas VALUES (p_id_factura, p_id_cliente, p_monto, NOW());
    ELSE
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Error: ID de cliente inexistente';
    END IF;
END $

CREATE PROCEDURE sp_detalle_facturas_stock(
    IN p_id_detalle_factura VARCHAR(60),
    IN p_id_producto        VARCHAR(60),
    IN p_id_factura         VARCHAR(60),
    IN p_cantidad_comprada  INT
)
BEGIN
    DECLARE v_stock_actual INT;

    SELECT stock INTO v_stock_actual
    FROM   Productos
    WHERE  id_productos = p_id_producto;

    -- Validaciones antes de insertar: producto existe, hay stock, cantidad disponible
    IF v_stock_actual IS NULL THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'ID de producto inexistente';

    ELSEIF v_stock_actual <= 0 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Stock agotado (0 unidades)';

    ELSEIF p_cantidad_comprada > v_stock_actual THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Cantidad solicitada excede el stock disponible';

    ELSE
        -- Transaccion: insertar detalle y descontar stock atomicamente
        START TRANSACTION;
            INSERT INTO Detalles_facturas
                (id_detalle_factura, id_productos, id_factura, cantidad_comprada)
            VALUES
                (p_id_detalle_factura, p_id_producto, p_id_factura, p_cantidad_comprada);

            UPDATE Productos
            SET    stock = stock - p_cantidad_comprada
            WHERE  id_productos = p_id_producto;
        COMMIT;
    END IF;
END $

DELIMITER ;
