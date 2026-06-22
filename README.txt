ABARROTERIA KINAL — Guia de configuracion
==========================================

REQUISITOS
----------
- JDK 21
- OpenJFX (JavaFX) 21
- MySQL 8.x
- Apache NetBeans 21
- Apache Ant (viene incluido con NetBeans)


PASO 1 — BASE DE DATOS
-----------------------
1. Abre MySQL Workbench e inicia sesion con tu usuario root.
2. Ve a File > Open SQL Script y abre el archivo: abarroteria_kinal_final.sql
3. Ejecuta el script completo con Ctrl+Shift+Enter.
4. Verifica que se creo la base de datos "abarroteria_kinal_in4bv" con sus tablas.
5. Inserta los roles necesarios ejecutando:

   INSERT INTO roles (id_rol, nombre_rol) VALUES
       ('ROL-001', 'Administrador'),
       ('ROL-002', 'Cajero');


PASO 2 — CREDENCIALES
----------------------
Abre el archivo:
   abarroteria-kinal/src/com/jguerrakinal/abarroteriakinal/config/Credentials.java

Cambia los valores por los de tu instalacion local de MySQL:

   public static final String URL_DB = "jdbc:mysql://localhost:3306/abarroteria_kinal_in4bv";
   public static final String USER_DB = "root";
   public static final String PASS_DB = "tu_contraseña";

IMPORTANTE: Si tu usuario de MySQL no es root, ejecuta esto en Workbench
para asegurarte de que acepta conexiones JDBC:

   ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'tu_contraseña';
   FLUSH PRIVILEGES;


PASO 3 — JAVAFX (MODULE PATH)
------------------------------
1. Descarga OpenJFX 21 desde: https://gluonhq.com/products/javafx/
2. Extrae la carpeta en tu PC (ejemplo: C:\javafx-sdk-21\lib)
3. En NetBeans, clic derecho en el proyecto > Properties
4. Ve a Libraries > Run > VM Options y agrega:

   --module-path "C:\javafx-sdk-21\lib" --add-modules javafx.controls,javafx.fxml

   (Ajusta la ruta segun donde extrajiste JavaFX)


PASO 4 — EJECUTAR
-----------------
1. Abre el proyecto en Apache NetBeans 21:
   File > Open Project > selecciona la carpeta abarroteria-kinal

2. Clic derecho en el proyecto > Clean and Build

3. Presiona F6 o Run > Run Project


USO DE LA APP
-------------
- En la pantalla de registro completa todos los campos.
- En el campo "Rol del Usuario" escribe exactamente: ROL-001 o ROL-002
  ROL-001 = Administrador
  ROL-002 = Cajero
- Las contraseñas se guardan encriptadas con BCrypt automaticamente.


ESTRUCTURA DE LA BASE DE DATOS
-------------------------------
abarroteria_kinal_in4bv
  roles              — tipos de usuario (Administrador, Cajero)
  usuarios           — usuarios registrados con contrasena BCrypt
  direcciones        — direcciones de clientes
  clientes           — clientes de la abarroteria
  telefonos          — telefonos de clientes
  productos          — inventario de productos
  facturas           — facturas generadas
  detalles_facturas  — detalle de productos por factura


AUTOR
-----
Jonathan Guerra
Proyecto academico — Fundación Kinal 2026
