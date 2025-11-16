# Actividad 1 - Sistema de Gestión de Estaciones de Servicio

Sistema Spring Boot empresarial para gestionar estaciones de servicio consumiendo APIs del Ministerio de Energía español.

## Características

- ✅ Arquitectura Hexagonal (Puertos y Adaptadores)
- ✅ Validación XML contra esquemas XSD con JAXB
- ✅ Persistencia intercambiable (JPA/JDBC)
- ✅ Spring Batch para procesos ETL
- ✅ PostgreSQL 16 con consultas geográficas optimizadas
- ✅ Java 21 con Records y Pattern Matching
- ✅ API REST documentada con Swagger/OpenAPI

## Requisitos

- Java 21 (LTS)
- Maven 3.9+
- PostgreSQL 16
- Spring Boot 3.2+

## Configuración

### 1. Base de Datos PostgreSQL

```sql
CREATE DATABASE estaciones_db;
```

### 2. Variables de Entorno

Las credenciales por defecto están configuradas en `application.properties`:
- Usuario: `postgres`
- Password: `postgres`

Si necesitas cambiarlas, puedes usar variables de entorno:

```bash
export DB_USERNAME=postgres
export DB_PASSWORD=postgres
```

### 3. Esquemas XSD

Los archivos XSD ya están incluidos en `src/main/resources/schemas/`:
- ✅ `estaciones-terrestres.xsd`
- ✅ `postes-maritimos.xsd`
- ✅ `comunidades-autonomas.xsd`
- ✅ `provincias.xsd`
- ✅ `municipios.xsd`
- ✅ `productos-petroliferos.xsd`

Las clases JAXB para mapear XML ya están creadas en `src/main/java/com/estaciones/infrastructure/xml/model/`

### 4. Ejecutar la Aplicación

```bash
mvn spring-boot:run
```

O con perfil específico:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=jpa
```

## API Endpoints

### Swagger UI
- **Swagger UI** (interfaz web): http://localhost:8080/swagger-ui.html
- **OpenAPI JSON** (para SOAPUI/Postman): http://localhost:8080/api-docs
- **OpenAPI YAML**: http://localhost:8080/api-docs.yaml

**Nota para SOAPUI:** Usa la URL `/api-docs` (JSON), NO `/swagger-ui.html` (HTML). Ver [SOAPUI_IMPORT.md](SOAPUI_IMPORT.md) para más detalles.

### Endpoints Disponibles

#### GET /api/empresas/mas-estaciones-terrestres
Obtiene la empresa con más estaciones terrestres.

**Respuesta:**
```json
{
  "data": {
    "idEmpresa": 123,
    "nombreRotulo": "REPSOL",
    "totalEstaciones": 4583
  },
  "status": "SUCCESS",
  "timestamp": "2025-01-09T13:26:00"
}
```

#### GET /api/estaciones/cercanas
Busca estaciones cercanas a una ubicación.

**Parámetros:**
- `lat` (required): Latitud
- `lon` (required): Longitud
- `radio` (required): Radio de búsqueda en kilómetros
- `combustible` (optional): ID del producto petrolífero

**Ejemplo:**
```
GET /api/estaciones/cercanas?lat=38.9942&lon=-1.8585&radio=10&combustible=Gasóleo A
```

**Respuesta:**
```json
{
  "data": [
    {
      "idEess": "12345",
      "direccion": "Calle Principal 123",
      "empresa": "CEPSA",
      "margen": "D",
      "precio": 1.449,
      "distanciaKm": 3.2
    }
  ],
  "status": "SUCCESS",
  "timestamp": "2025-01-09T13:26:00"
}
```

## Estructura del Proyecto

Ver [ESTRUCTURA_PROYECTO.md](ESTRUCTURA_PROYECTO.md) para detalles completos.

```
src/main/java/
├── domain/              # Capa de dominio (sin dependencias)
│   ├── model/          # Objetos de dominio (Records)
│   └── repository/     # Interfaces de repositorio (puertos)
├── infrastructure/      # Capa de infraestructura
│   ├── persistence/    # Implementaciones de persistencia
│   ├── xml/            # Clientes REST y validadores XML
│   └── batch/          # Jobs Spring Batch
├── application/         # Capa de aplicación
│   ├── service/        # Servicios de aplicación
│   └── dto/            # DTOs para API REST
└── adapter/
    └── rest/           # Controladores REST
```

## Arquitectura

El proyecto sigue una **arquitectura hexagonal** que separa:

- **Dominio**: Lógica de negocio pura (sin dependencias)
- **Infraestructura**: Implementaciones técnicas (JPA, REST, XML)
- **Aplicación**: Casos de uso y servicios
- **Adaptadores**: Puntos de entrada (REST controllers)

## Validación XML

Todos los XML recibidos de las APIs externas se validan automáticamente contra esquemas XSD antes de procesarse. Los errores de validación se capturan y loguean con detalles de línea y columna.

## Spring Batch

Jobs ETL configurados para:
- Validación XML obligatoria
- Transacciones por chunks de 100 registros
- Reintento automático en fallos transitorios
- Auditoría de ejecuciones

## Desarrollo

### Compilar

```bash
mvn clean compile
```

### Tests

```bash
mvn test
```

### Generar DDL

El DDL se genera automáticamente en `schema.sql` al iniciar la aplicación.

## Notas

- ✅ Los esquemas XSD están incluidos en `src/main/resources/schemas/`
- ✅ Las clases JAXB están creadas manualmente en `src/main/java/com/estaciones/infrastructure/xml/model/`
- ✅ Los mappers de JAXB a objetos de dominio están implementados
- ✅ Los job de Spring Batch para cargar datos de estaciones están completos con validación XML
- ⚠️ El perfil JDBC está configurado pero pendiente de implementar
