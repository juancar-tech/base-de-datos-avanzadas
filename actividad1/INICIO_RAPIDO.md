# 🚀 Guía de Inicio Rápido

## Prerrequisitos

Antes de levantar la aplicación, asegúrate de tener:

- ✅ **Java 21** instalado y configurado
- ✅ **Maven 3.9+** instalado
- ✅ **PostgreSQL 16** instalado y ejecutándose
- ✅ **Puerto 5432** disponible para PostgreSQL
- ✅ **Puerto 8080** disponible para la aplicación

## Pasos para Levantar la Aplicación

### 1. Verificar Java y Maven

```bash
java -version
# Debe mostrar: openjdk version "21" o similar

mvn -version
# Debe mostrar: Apache Maven 3.9.x o superior
```

### 2. Iniciar PostgreSQL

**Windows:**
```bash
# Si PostgreSQL está como servicio, ya debería estar corriendo
# Verificar con:
pg_ctl status -D "C:\Program Files\PostgreSQL\16\data"
```

**Linux/Mac:**
```bash
sudo systemctl start postgresql
# o
brew services start postgresql
```

### 3. Crear la Base de Datos

```bash
# Conectarse a PostgreSQL
psql -U profesor -d postgres

# Crear la base de datos
CREATE DATABASE estaciones_db;

# (Opcional) Ejecutar el script DDL manualmente
\c estaciones_db
\i src/main/resources/db/schema.sql

# Salir
\q
```

**Nota:** Si no ejecutas el script DDL manualmente, Hibernate lo creará automáticamente con `spring.jpa.hibernate.ddl-auto=update`.

### 4. Configurar Variables de Entorno (Opcional)

Las credenciales por defecto están configuradas en `application.properties`:
- Usuario: `profesor`
- Password: `postgres`

Si necesitas cambiarlas, puedes usar variables de entorno:

**Windows (PowerShell):**
```powershell
$env:DB_USERNAME="profesor"
$env:DB_PASSWORD="postgres"
```

**Windows (CMD):**
```cmd
set DB_USERNAME=profesor
set DB_PASSWORD=postgres
```

**Linux/Mac:**
```bash
export DB_USERNAME=profesor
export DB_PASSWORD=postgres
```

### 5. Compilar el Proyecto

```bash
# Limpiar y compilar
mvn clean compile

# O compilar y empaquetar
mvn clean package -DskipTests
```

### 6. Ejecutar la Aplicación

```bash
# Opción 1: Con Maven
mvn spring-boot:run

# Opción 2: Con el JAR generado
java -jar target/sistema-gestion-estaciones-1.0.0.jar

# Opción 3: Con perfil específico
mvn spring-boot:run -Dspring-boot.run.profiles=jpa
```

### 7. Verificar que la Aplicación Está Corriendo

Deberías ver en la consola:
```
Started EstacionesApplication in X.XXX seconds
```

Y luego puedes acceder a:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/api-docs
- **Health Check**: http://localhost:8080/actuator/health (si está habilitado)

## Probar los Endpoints

### 1. Swagger UI

Abre en tu navegador:
```
http://localhost:8080/swagger-ui.html
```

Aquí podrás ver y probar todos los endpoints disponibles.

### 2. Probar con cURL

```bash
# Obtener empresa con más estaciones terrestres
curl http://localhost:8080/api/empresas/mas-estaciones-terrestres

# Buscar estaciones cercanas (ejemplo: Madrid)
curl "http://localhost:8080/api/estaciones/cercanas?lat=40.4168&lon=-3.7038&radio=10"
```

## Solución de Problemas Comunes

### Error: "Connection refused" a PostgreSQL

**Problema:** PostgreSQL no está corriendo o no está en el puerto 5432.

**Solución:**
```bash
# Verificar que PostgreSQL está corriendo
psql -U postgres -c "SELECT version();"

# Si no está corriendo, iniciarlo
# Windows: Servicios > PostgreSQL
# Linux: sudo systemctl start postgresql
```

### Error: "Database does not exist"

**Problema:** La base de datos `estaciones_db` no existe.

**Solución:**
```sql
CREATE DATABASE estaciones_db;
```

### Error: "Authentication failed"

**Problema:** Credenciales incorrectas.

**Solución:**
- Verificar usuario y contraseña en `application.properties`
- O configurar variables de entorno `DB_USERNAME` y `DB_PASSWORD`

### Error: "Port 8080 already in use"

**Problema:** Otra aplicación está usando el puerto 8080.

**Solución:**
- Cambiar el puerto en `application.properties`:
  ```properties
  server.port=8081
  ```
- O detener la aplicación que está usando el puerto 8080

### Error de Compilación: "Java version mismatch"

**Problema:** No estás usando Java 21.

**Solución:**
```bash
# Verificar versión de Java
java -version

# Si no es Java 21, configurar JAVA_HOME
# Windows:
set JAVA_HOME=C:\Program Files\Java\jdk-21
# Linux/Mac:
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk
```

## Próximos Pasos

Una vez que la aplicación esté corriendo:

1. **Probar los endpoints** desde Swagger UI
2. **Ejecutar el job de Spring Batch** para cargar datos:
   ```bash
   # Habilitar jobs en application.properties:
   # spring.batch.job.enabled=true
   ```
3. **Cargar datos de prueba** desde las APIs del Ministerio de Energía
4. **Revisar los logs** para ver el funcionamiento

## Comandos Útiles

```bash
# Ver logs en tiempo real
tail -f logs/application.log

# Compilar sin tests
mvn clean package -DskipTests

# Ejecutar tests
mvn test

# Limpiar proyecto
mvn clean

# Ver dependencias
mvn dependency:tree
```

## Estructura de Logs

Los logs se mostrarán en la consola con:
- ✅ **DEBUG**: Información detallada de la aplicación
- ✅ **INFO**: Información general
- ✅ **SQL**: Consultas SQL ejecutadas (si `spring.jpa.show-sql=true`)

¡Listo! Tu aplicación debería estar corriendo. 🎉

