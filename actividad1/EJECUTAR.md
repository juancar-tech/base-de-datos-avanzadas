# Guía Rápida: Ejecutar el Proyecto

## Prerrequisitos

1. **PostgreSQL 16** debe estar corriendo
2. **Base de datos** `estaciones_db` debe existir
3. **Java 21** instalado
4. **Maven 3.9+** instalado

## Pasos para Ejecutar

### 1. Navegar a la carpeta del proyecto

```bash
cd actividad1
```

### 2. Verificar la base de datos

Asegúrate de que PostgreSQL esté corriendo y que la base de datos exista:

```sql
-- Conectar a PostgreSQL
psql -U postgres

-- Crear la base de datos si no existe
CREATE DATABASE estaciones_db;

-- Verificar que existe
\l
```

### 3. Verificar credenciales

Revisa `src/main/resources/application.properties` y ajusta las credenciales si es necesario:
- Usuario por defecto: `user_estaciones`
- Password por defecto: `lkj456`
- Base de datos: `estaciones_db`

O usa variables de entorno:
```bash
export DB_USERNAME=postgres
export DB_PASSWORD=postgres
```

### 4. Compilar el proyecto

```bash
mvn clean compile
```

### 5. Ejecutar la aplicación

#### Opción A: Con Maven (Recomendado)
```bash
mvn spring-boot:run
```

#### Opción B: Con perfil específico
```bash
# Perfil JPA (por defecto)
mvn spring-boot:run -Dspring-boot.run.profiles=jpa

# Perfil JDBC (si está implementado)
mvn spring-boot:run -Dspring-boot.run.profiles=jdbc
```

#### Opción C: Generar JAR y ejecutar
```bash
# Compilar y empaquetar
mvn clean package

# Ejecutar el JAR
java -jar target/sistema-gestion-estaciones-1.0.0.jar
```

### 6. Verificar que está corriendo

La aplicación debería iniciar en: **http://localhost:8080**

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs (JSON)**: http://localhost:8080/api-docs
- **Health Check**: http://localhost:8080/actuator/health

## Solución de Problemas

### Error: "Connection refused" o "No se puede conectar a PostgreSQL"
- Verifica que PostgreSQL esté corriendo: `pg_isready` o `psql -U postgres`
- Verifica que el puerto 5432 esté disponible
- Revisa las credenciales en `application.properties`

### Error: "Database does not exist"
```sql
CREATE DATABASE estaciones_db;
```

### Error: "Table does not exist"
El esquema se crea automáticamente con `spring.jpa.hibernate.ddl-auto=update`, pero puedes ejecutar manualmente:
```bash
psql -U postgres -d estaciones_db -f src/main/resources/db/schema.sql
```

### Puerto 8080 ocupado
Cambia el puerto en `application.properties`:
```properties
server.port=8081
```

## Scripts de Ayuda

### Windows (PowerShell)
```powershell
# Ejecutar desde la raíz del repositorio
cd actividad1
mvn spring-boot:run
```

### Linux/Mac
```bash
# Ejecutar desde la raíz del repositorio
cd actividad1
mvn spring-boot:run
```

## Comandos Útiles

```bash
# Limpiar y compilar
mvn clean compile

# Ejecutar tests
mvn test

# Ver dependencias
mvn dependency:tree

# Verificar configuración
mvn help:effective-pom
```

