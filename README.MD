# 📋 Proyecto CRM — 3er Trimestre

**Curso:** 1º de DAM (Desarrollo de Aplicaciones Multiplataforma)  
**Alumnos:** Carlos Muñoz · Daniel Garcia  
**Proyecto:** GymTonic CRM — Sistema de gestión para gimnasio

---

## 📁 Estructura del proyecto

```
ProyectoCRM3Trimestre/
├── BaseDeDatos/          → Asignatura: Bases de Datos
├── LenguajeDeMarcas/     → Asignatura: Lenguaje de Marcas
└── Programacion/         → Asignatura: Programación
```

---

## 🗃️ Bases de Datos

### Descripción
Scripts SQL para dos entornos distintos: **MySQL (Workbench)** y **Oracle**. Incluye DDL, DML con datos de prueba, cursores y procedimientos/funciones PL/SQL.

### Archivos
| Archivo | Descripción |
|---|---|
| `DDL-Workbench.sql` | Creación de tablas en MySQL |
| `DML-Workbench.sql` | Inserción de datos de prueba en MySQL |
| `DDL-Oracle.sql` | Creación de tablas en Oracle |
| `DML_Create-Oracle.sql` | Inserción de datos en Oracle |
| `DML_Update-Oracle.sql` | Actualizaciones en Oracle |
| `DML_Delete-Oracle.sql` | Eliminaciones en Oracle |
| `DML_Cursores-Oracle.sql` | Cursores PL/SQL |
| `PLSQL_Procedimientos_Funciones-Oracle.sql` | Procedimientos y funciones PL/SQL |

### Aplicaciones necesarias

| Herramienta | Versión recomendada | Descarga |
|---|---|---|
| MySQL Server | 8.x | https://dev.mysql.com/downloads/mysql/ |
| MySQL Workbench | 8.x | https://dev.mysql.com/downloads/workbench/ |
| Oracle Database XE | 21c | https://www.oracle.com/database/technologies/xe-downloads.html |
| SQL Developer | 23.x | https://www.oracle.com/tools/downloads/sqldev-downloads.html |

### ▶️ Cómo ejecutar — MySQL (Workbench)

1. Abre **MySQL Workbench** y conéctate al servidor local.
2. Ve a `File → Open SQL Script` y abre `DDL-Workbench.sql`.
3. Pulsa el rayo (**⚡ Execute All**) para crear la base de datos `crm_gimnasio` y sus tablas.
4. Abre `DML-Workbench.sql` y ejecútalo para insertar los datos de prueba.

### ▶️ Cómo ejecutar — Oracle (SQL Developer)

1. Abre **SQL Developer** y crea una conexión local con tu usuario Oracle.
2. Abre y ejecuta los scripts en este orden:
   1. `DDL-Oracle.sql` — crea las tablas
   2. `DML_Create-Oracle.sql` — inserta datos
   3. `DML_Update-Oracle.sql` — actualizaciones
   4. `DML_Delete-Oracle.sql` — eliminaciones
   5. `DML_Cursores-Oracle.sql` — cursores
   6. `PLSQL_Procedimientos_Funciones-Oracle.sql` — procedimientos y funciones
3. Para ver la salida de los procedimientos, activa `DBMS Output` desde `View → DBMS Output` y pulsa el **+** verde para conectarlo.

### ⚠️ Notas importantes
- La base de datos MySQL se llama `crm_gimnasio`. El script DDL la crea automáticamente (`DROP DATABASE IF EXISTS` + `CREATE DATABASE`).
- En Oracle no existe `CREATE DATABASE` desde script normal; usa el esquema de tu usuario directamente.
- Los scripts de Oracle y MySQL **no son intercambiables** — cada uno usa sintaxis propia (e.g., `AUTO_INCREMENT` vs `GENERATED ALWAYS AS IDENTITY`).

---

## 🌐 Lenguaje de Marcas

### Descripción
Interfaz web del CRM GymTonic. Single Page Application con sidebar de navegación, módulos CRUD para clientes, usuarios, instructores, clases, agenda y configuración del gimnasio. Toda la lógica está en JavaScript vanilla con `localStorage`.

### Estructura
```
LenguajeDeMarcas/
├── index.html
├── css/
│   └── style.css
├── js/
│   ├── menu.js
│   ├── fecha.js
│   ├── configuracion.js
│   ├── crud-cliente.js
│   ├── crud-usuarios.js
│   ├── crud-instructores.js
│   ├── crud-clases.js
│   └── crud-agenda.js
└── pages/
    ├── clientes.html
    ├── usuarios.html
    ├── instructores.html
    ├── clases.html
    ├── agenda.html
    └── configuracion.html
```

### Aplicaciones necesarias

| Herramienta | Versión recomendada | Notas |
|---|---|---|
| Cualquier navegador actual funciona |
| Visual Studio Code *(recomendado)* | Última versión | Con extensión **Live Server** |
| Extensión Live Server | 5.x | Para evitar problemas con CORS al cargar JS |

### ▶️ Cómo ejecutar

**Opción A — Visual Studio Code + Live Server (recomendado):**
1. Abre la carpeta `LenguajeDeMarcas/` en Visual Studio Code.
2. Instala la extensión **Live Server** (de Ritwick Dey) si no la tienes.
3. Haz clic derecho sobre `index.html` → **"Open with Live Server"**.
4. El navegador abrirá automáticamente `http://127.0.0.1:5500/index.html`.

**Opción B — Directamente en el navegador:**
1. Navega hasta la carpeta `LenguajeDeMarcas/`.
2. Haz doble clic sobre `index.html`.
3. El archivo se abrirá en tu navegador por defecto.

---

## ☕ Programación

### Descripción
Aplicación de consola en **Java** que implementa el mismo CRM GymTonic con menús interactivos por terminal. Gestión de clientes, usuarios, membresías, clases, agenda y configuración del gimnasio. Se conecta a una base de datos MySQL mediante JDBC.

### Estructura
```
Programacion/
├── pom.xml                          ← Dependencias Maven
└── src/main/java/org/gymtonic/
    ├── Main.java                    ← Punto de entrada
    ├── Export.java                  ← Exportación de datos
    ├── database/
    │   └── DatabaseConnection.java  ← Conexión MySQL
    ├── controllers/                 ← Lógica de negocio
    ├── menus/                       ← Menús de consola
    ├── models/                      ← Entidades (Cliente, Clase, etc.)
    ├── repository/                  ← Acceso a datos (JDBC)
    └── service/                     ← Capa de servicios
```

### Aplicaciones necesarias

| Herramienta | Versión recomendada | Descarga |
|---|---|---|
| JDK (Java Development Kit) | **17** (LTS) | https://adoptium.net/ |
| Apache Maven | 3.9.x | https://maven.apache.org/download.cgi |
| MySQL Server | 8.x | https://dev.mysql.com/downloads/mysql/ |
| IntelliJ IDEA *(recomendado)* | Community / Ultimate | https://www.jetbrains.com/idea/download/ |

### Dependencias Maven (pom.xml)
```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>8.4.0</version>
</dependency>
```
Maven descarga el conector automáticamente; no necesitas instalarlo manualmente.

### ⚙️ Configuración de la base de datos
Antes de ejecutar, asegúrate de que MySQL esté corriendo y la base de datos esté creada (ver sección **Bases de Datos → MySQL**). Los datos de conexión están en `DatabaseConnection.java`:

```java
URL      → jdbc:mysql://localhost:3306/crm_gimnasio
USER     → root
PASSWORD → 1234
```

Si tu instalación de MySQL tiene otra contraseña, edita ese archivo antes de compilar.

### ▶️ Cómo ejecutar — IntelliJ IDEA (recomendado)

1. Abre IntelliJ IDEA.
2. Selecciona `File → Open` y abre la carpeta `Programacion/`.
3. IntelliJ detectará el `pom.xml` automáticamente. Acepta importar como proyecto Maven.
4. Espera a que descargue las dependencias (barra de progreso inferior).
5. Abre `src/main/java/org/gymtonic/Main.java`.
6. Pulsa el **▶ botón verde** junto al método `main` → `Run 'Main'`.
7. El menú principal aparecerá en la consola integrada.

### ▶️ Cómo ejecutar — Línea de comandos (Maven)

```bash
# 1. Situarse en la carpeta del módulo
cd ProyectoCRM3Trimestre/Programacion

# 2. Compilar y empaquetar
mvn compile

# 3. Ejecutar la clase principal
mvn exec:java -Dexec.mainClass="org.gymtonic.Main"
```

### ⚠️ Notas importantes
- El proyecto requiere **Java 17** exactamente (definido en `pom.xml`). Con Java 11 o 21 puede dar errores de compilación o warnings.
- MySQL debe estar **arrancado y accesible en `localhost:3306`** antes de lanzar la aplicación; si no, saltará una excepción de conexión al intentar cualquier operación con la BD.
- La aplicación es de **consola pura** — no tiene interfaz gráfica.
- El archivo compilado reside en `target/classes/`; si haces cambios en el código, debes recompilar (`mvn compile`) antes de volver a ejecutar.

---

## 🔗 Resumen de dependencias por asignatura

| Asignatura | Tecnología | Versión |
|---|---|---|
| Bases de Datos | MySQL Server + Workbench | 8.x |
| Bases de Datos | Oracle XE + SQL Developer | 21c / 23.x |
| Lenguaje de Marcas | HTML5 + CSS3 + JavaScript ES6+ | — |
| Lenguaje de Marcas | Font Awesome (CDN) | 6.4.0 |
| Programación | Java (JDK) | **17** |
| Programación | Apache Maven | 3.9.x |
| Programación | MySQL Connector/J | 8.4.0 |

---

*Proyecto de fin de 3er trimestre · 1º DAM · Carlos Muñoz & Daniel Garcia*
