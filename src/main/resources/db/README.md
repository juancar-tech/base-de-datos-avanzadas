# Scripts de Base de Datos

## schema.sql

Script DDL completo para crear todas las tablas, índices, constraints y funciones de la base de datos PostgreSQL.

### Uso

1. **Crear la base de datos**:
```sql
CREATE DATABASE estaciones_db;
```

2. **Conectarse a la base de datos**:
```bash
psql -U profesor -d estaciones_db
```

3. **Ejecutar el script**:
```bash
psql -U profesor -d estaciones_db -f src/main/resources/db/schema.sql
```

**Nota:** Las credenciales por defecto son:
- Usuario: `profesor`
- Password: `postgres`

O desde psql:
```sql
\i src/main/resources/db/schema.sql
```

### Estructura

El script crea:

- **Tablas de referencia**:
  - `comunidad_autonoma`
  - `provincia`
  - `municipio`
  - `empresa`
  - `producto_petrolifero`

- **Tablas de estaciones terrestres**:
  - `estacion_servicio_terrestre`
  - `precio_terrestre`

- **Tablas de estaciones marítimas**:
  - `estacion_servicio_maritima`
  - `precio_maritimo`

- **Índices**: Optimizados para búsquedas geográficas y por ubicación

- **Constraints**: Validaciones de integridad referencial y de datos

- **Vistas**: Vista útil para consultas completas

- **Funciones**: Función para calcular distancias geográficas

### Notas

- El script usa `CREATE TABLE IF NOT EXISTS` para evitar errores si las tablas ya existen
- Todos los índices usan `CREATE INDEX IF NOT EXISTS`
- Las foreign keys tienen `ON DELETE RESTRICT` para mantener integridad
- Los precios tienen constraints para validar que sean positivos y menores a 999.999
- Las coordenadas geográficas tienen validación de rango

