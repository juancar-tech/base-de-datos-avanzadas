# Guía de Spring Batch - Cargar Datos

## Estado Actual

✅ **El batch está preparado** para cargar datos desde las APIs del Ministerio de Energía.

### Jobs Disponibles

- ✅ **cargarComunidadesAutonomasJob** - Carga comunidades autónomas desde la API

### Jobs Pendientes

- ⚠️ Cargar provincias
- ⚠️ Cargar municipios
- ⚠️ Cargar productos petrolíferos
- ⚠️ Cargar estaciones terrestres
- ⚠️ Cargar estaciones marítimas

## Cómo Lanzar los Jobs

### Opción 1: Desde la API REST (Recomendado)

Una vez que la aplicación esté corriendo, puedes lanzar los jobs mediante endpoints REST:

#### Lanzar Job de Comunidades Autónomas

```bash
# Con cURL
curl -X POST http://localhost:8080/api/batch/jobs/cargar-comunidades-autonomas

# O desde Swagger UI
# http://localhost:8080/swagger-ui.html
# Busca el endpoint: POST /api/batch/jobs/cargar-comunidades-autonomas
```

#### Lanzar Cualquier Job por Nombre

```bash
curl -X POST "http://localhost:8080/api/batch/jobs/ejecutar?jobName=cargarComunidadesAutonomasJob"
```

### Opción 2: Habilitar Auto-ejecución al Iniciar

Si quieres que el job se ejecute automáticamente al iniciar la aplicación:

1. Edita `src/main/resources/application.properties`:
```properties
# Cambiar de false a true
spring.batch.job.enabled=true
```

2. Reinicia la aplicación:
```bash
mvn spring-boot:run
```

**Nota:** Esto ejecutará TODOS los jobs configurados al iniciar. No es recomendado para producción.

### Opción 3: Desde Código Java

Si necesitas ejecutar el job desde código:

```java
@Autowired
private BatchService batchService;

public void cargarDatos() {
    try {
        Long executionId = batchService.ejecutarCargarComunidadesAutonomas();
        System.out.println("Job iniciado con ID: " + executionId);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```

## Verificar Ejecución

### 1. Ver Logs en Consola

Cuando ejecutes el job, verás en los logs:

```
INFO  - Iniciando job: cargarComunidadesAutonomasJob
INFO  - Obteniendo comunidades autónomas desde API
INFO  - Parseadas X comunidades autónomas
DEBUG - Procesando comunidad autónoma: Andalucía
DEBUG - Guardada comunidad autónoma: Andalucía
...
INFO  - Job completado exitosamente
```

### 2. Verificar en Base de Datos

```sql
-- Conectarse a PostgreSQL
psql -U profesor -d estaciones_db

-- Verificar comunidades autónomas cargadas
SELECT * FROM comunidad_autonoma;

-- Ver cantidad
SELECT COUNT(*) FROM comunidad_autonoma;
```

### 3. Verificar Estado del Job

Los jobs de Spring Batch se registran en las tablas de Spring Batch:

```sql
-- Ver ejecuciones de jobs
SELECT * FROM batch_job_execution ORDER BY start_time DESC LIMIT 10;

-- Ver detalles de una ejecución
SELECT * FROM batch_job_execution_params WHERE job_execution_id = <ID>;

-- Ver estado de steps
SELECT * FROM batch_step_execution ORDER BY start_time DESC LIMIT 10;
```

## Flujo del Job

El job `cargarComunidadesAutonomasJob` realiza:

1. **Reader**: 
   - Consume API REST: `GET /Listados/ComunidadesAutonomas/`
   - Obtiene XML de respuesta
   - Valida XML contra XSD (`comunidades-autonomas.xsd`)
   - Parsea XML con JAXB
   - Convierte a objetos de dominio (Records)

2. **Processor**:
   - Procesa cada comunidad autónoma
   - Valida datos
   - Logs de depuración

3. **Writer**:
   - Guarda en base de datos PostgreSQL
   - Transacciones por chunks de 100 registros
   - Manejo de errores

## Configuración

### application.properties

```properties
# Spring Batch
spring.batch.job.enabled=false  # No ejecutar automáticamente al iniciar
spring.batch.initialize-schema=always  # Crear tablas de Spring Batch
```

### Chunk Size

El job procesa en chunks de 100 registros:

```java
.<ComunidadAutonoma, ComunidadAutonoma>chunk(100, transactionManager)
```

Esto significa que cada 100 registros se hace un commit a la base de datos.

## Solución de Problemas

### Error: "Job not found"

**Problema:** El job no está registrado correctamente.

**Solución:**
1. Verifica que el job esté definido como `@Bean`
2. Verifica que el nombre del job coincida
3. Reinicia la aplicación

### Error: "Connection refused" a la API

**Problema:** No se puede conectar a la API del Ministerio de Energía.

**Solución:**
1. Verifica tu conexión a Internet
2. Verifica que la URL de la API sea correcta
3. Verifica que la API esté disponible

### Error: "XML validation failed"

**Problema:** El XML no pasa la validación contra el XSD.

**Solución:**
1. Verifica que el esquema XSD esté en `src/main/resources/schemas/`
2. Revisa los logs para ver el error específico
3. Verifica que el XML de la API coincida con el esquema

### Job se ejecuta pero no guarda datos

**Problema:** Puede haber un error en el writer o en la transacción.

**Solución:**
1. Revisa los logs para ver errores de base de datos
2. Verifica que las credenciales de PostgreSQL sean correctas
3. Verifica que las tablas existan en la base de datos
4. Revisa los logs de Spring Batch para ver el estado del job

## Próximos Pasos

Para completar la carga de datos, necesitas crear jobs similares para:

1. **Provincias** - Similar a comunidades autónomas
2. **Municipios** - Similar a provincias
3. **Productos Petrolíferos** - Similar a comunidades autónomas
4. **Estaciones Terrestres** - Más complejo, incluye precios
5. **Estaciones Marítimas** - Similar a terrestres

Cada job sigue el mismo patrón:
- Reader: Consumir API → Validar XML → Parsear → Convertir a dominio
- Processor: Validar y procesar
- Writer: Guardar en base de datos

## Ejemplo de Uso Completo

```bash
# 1. Iniciar la aplicación
mvn spring-boot:run

# 2. En otra terminal, lanzar el job
curl -X POST http://localhost:8080/api/batch/jobs/cargar-comunidades-autonomas

# 3. Verificar en base de datos
psql -U profesor -d estaciones_db -c "SELECT * FROM comunidad_autonoma;"
```

¡Listo! El batch está preparado y funcionando. 🚀


