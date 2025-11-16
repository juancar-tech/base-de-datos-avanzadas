# Estructura del Proyecto - Sistema de Gestión de Estaciones de Servicio

## Arquitectura Hexagonal

El proyecto sigue una arquitectura hexagonal (puertos y adaptadores) que separa la lógica de negocio de la infraestructura:

```
src/main/java/com/estaciones/
├── EstacionesApplication.java       # Clase principal Spring Boot

├── domain/                          # Capa de Dominio (sin dependencias externas)
│   ├── model/                       # Objetos de dominio (Records Java 21)
│   │   ├── ComunidadAutonoma.java
│   │   ├── Provincia.java
│   │   ├── Municipio.java
│   │   ├── Empresa.java
│   │   ├── ProductoPetrolifero.java
│   │   ├── EstacionServicioTerrestre.java
│   │   ├── PrecioTerrestre.java
│   │   ├── EstacionServicioMaritima.java
│   │   └── PrecioMaritimo.java
│   ├── repository/                  # Interfaces de repositorio (Puertos)
│   │   ├── EmpresaRepository.java
│   │   ├── ComunidadAutonomaRepository.java
│   │   ├── ProvinciaRepository.java
│   │   ├── MunicipioRepository.java
│   │   ├── EstacionTerrestreRepository.java
│   │   ├── EstacionMaritimaRepository.java
│   │   └── ProductoPetroliferoRepository.java
│   └── exception/
│       └── DomainException.java

├── infrastructure/                  # Capa de Infraestructura
│   ├── persistence/
│   │   ├── config/
│   │   │   ├── PersistenceConfig.java
│   │   │   └── DatabaseFunctionInitializer.java  # Inicializa funciones PL/pgSQL
│   │   └── jpa/
│   │       ├── entity/              # Entidades JPA (mapeo a BD)
│   │       │   ├── ComunidadAutonomaEntity.java
│   │       │   ├── ProvinciaEntity.java
│   │       │   ├── MunicipioEntity.java
│   │       │   ├── EmpresaEntity.java
│   │       │   ├── ProductoPetroliferoEntity.java
│   │       │   ├── EstacionServicioTerrestreEntity.java
│   │       │   ├── PrecioTerrestreEntity.java
│   │       │   ├── EstacionServicioMaritimaEntity.java
│   │       │   └── PrecioMaritimoEntity.java
│   │       ├── repository/          # Repositorios JPA internos y adaptadores
│   │       │   ├── JpaEmpresaRepository.java
│   │       │   ├── JpaComunidadAutonomaRepository.java
│   │       │   ├── JpaProvinciaRepository.java
│   │       │   ├── JpaMunicipioRepository.java
│   │       │   ├── JpaEstacionTerrestreRepository.java
│   │       │   ├── JpaEstacionMaritimaRepository.java
│   │       │   ├── JpaProductoPetroliferoRepository.java
│   │       │   ├── EmpresaRepositoryJpaAdapter.java
│   │       │   ├── ComunidadAutonomaRepositoryJpaAdapter.java
│   │       │   ├── ProvinciaRepositoryJpaAdapter.java
│   │       │   ├── MunicipioRepositoryJpaAdapter.java
│   │       │   ├── EstacionTerrestreRepositoryJpaAdapter.java
│   │       │   ├── EstacionMaritimaRepositoryJpaAdapter.java
│   │       │   └── ProductoPetroliferoRepositoryJpaAdapter.java
│   │       └── mapper/              # Mappers: Domain ↔ JPA Entity
│   │           ├── EmpresaMapper.java
│   │           ├── ComunidadAutonomaMapper.java
│   │           ├── ProvinciaMapper.java
│   │           ├── MunicipioMapper.java
│   │           ├── EstacionTerrestreMapper.java
│   │           ├── EstacionMaritimaMapper.java
│   │           └── ProductoPetroliferoMapper.java
│   ├── xml/
│   │   ├── client/                  # Clientes REST para consumir APIs
│   │   │   └── EnergiaApiClient.java
│   │   ├── validator/               # Validación XML contra XSD
│   │   │   └── XmlSchemaValidator.java
│   │   ├── mapper/                  # Mappers: JAXB → Domain
│   │   │   └── JaxbToDomainMapper.java
│   │   ├── model/                   # Modelos JAXB (generados desde XSD)
│   │   │   ├── ArrayOfComunidadAutonoma.java
│   │   │   ├── ArrayOfProvincia.java
│   │   │   ├── ArrayOfMunicipio.java
│   │   │   ├── ArrayOfProductosPetroliferos.java
│   │   │   ├── ArrayOfEESSPrecio.java
│   │   │   ├── ArrayOfPosteMaritimoPrecio.java
│   │   │   ├── ComunidadAutonomaJaxb.java
│   │   │   ├── ProvinciaJaxb.java
│   │   │   ├── MunicipioJaxb.java
│   │   │   ├── ProductosPetroliferosJaxb.java
│   │   │   ├── PreciosEESSTerrestres.java
│   │   │   ├── PreciosPostesMaritimos.java
│   │   │   ├── EESSPrecio.java
│   │   │   └── PosteMaritimoPrecio.java
│   │   ├── config/
│   │   │   ├── RestTemplateConfig.java
│   │   │   └── XmlValidatorConfig.java
│   │   └── exception/
│   │       └── XmlValidationException.java
│   └── batch/
│       ├── config/
│       │   └── BatchConfig.java
│       ├── job/                     # Configuraciones de Jobs Spring Batch
│       │   ├── CargarComunidadesAutonomasJobConfig.java
│       │   ├── CargarProvinciasJobConfig.java
│       │   ├── CargarMunicipiosJobConfig.java
│       │   ├── CargarProductosPetroliferosJobConfig.java
│       │   ├── CargarEstacionesTerrestresJobConfig.java
│       │   └── CargarEstacionesMaritimasJobConfig.java
│       └── reader/                  # Readers lazy para Spring Batch
│           ├── LazyComunidadesAutonomasReader.java
│           ├── LazyProvinciasReader.java
│           ├── LazyMunicipiosReader.java
│           ├── LazyProductosPetroliferosReader.java
│           ├── LazyEstacionesTerrestresReader.java
│           └── LazyEstacionesMaritimasReader.java

├── application/                     # Capa de Aplicación
│   ├── service/                     # Servicios de aplicación
│   │   ├── EmpresaService.java
│   │   ├── EstacionService.java
│   │   └── BatchService.java
│   └── dto/                          # DTOs para API REST
│       ├── ApiResponse.java
│       ├── EmpresaDTO.java
│       └── EstacionCercanaDTO.java

└── adapter/
    └── rest/                         # Controladores REST
        ├── EmpresaController.java
        ├── EstacionController.java
        └── BatchController.java
```

## Flujo de Datos

1. **API Externa** → `EnergiaApiClient` → XML sin validar
2. **Validación XML** → `XmlSchemaValidator` → XML validado contra XSD
3. **Parseo JAXB** → Modelos JAXB (`PreciosEESSTerrestres`, `PreciosPostesMaritimos`, etc.)
4. **Mapeo JAXB → Dominio** → `JaxbToDomainMapper` → Objetos de dominio (Records)
5. **Repositorio** → Adaptador JPA → Entidad JPA → PostgreSQL

## Persistencia Intercambiable

El proyecto permite cambiar la implementación de persistencia mediante perfiles:

- **Perfil JPA** (`application-jpa.properties`): Usa Spring Data JPA con Hibernate
- **Perfil JDBC** (`application-jdbc.properties`): Usa JDBC directo (pendiente de implementar)

## Validación XML

Todos los XML recibidos de las APIs externas se validan contra esquemas XSD antes de procesarse:

- Esquemas XSD en `src/main/resources/schemas/`
- Validación automática con `XmlSchemaValidator`
- Errores de validación capturados y logueados

## Spring Batch

Jobs ETL para cargar datos desde las APIs externas:

- **6 Jobs configurados**:
  - `CargarComunidadesAutonomasJobConfig`
  - `CargarProvinciasJobConfig`
  - `CargarMunicipiosJobConfig`
  - `CargarProductosPetroliferosJobConfig`
  - `CargarEstacionesTerrestresJobConfig`
  - `CargarEstacionesMaritimasJobConfig`

- **6 Readers lazy** (carga datos solo cuando se ejecuta el job):
  - `LazyComunidadesAutonomasReader`
  - `LazyProvinciasReader`
  - `LazyMunicipiosReader`
  - `LazyProductosPetroliferosReader`
  - `LazyEstacionesTerrestresReader`
  - `LazyEstacionesMaritimasReader`

- **Características**:
  - Validación XML obligatoria antes de procesar
  - Transacciones por chunks de 100 registros
  - Reintento automático en caso de fallos transitorios
  - Lectura lazy (no carga datos al iniciar la aplicación)

## API REST

Endpoints disponibles:

- `GET /api/empresas/mas-estaciones-terrestres` - Empresa con más estaciones terrestres
- `GET /api/estaciones/cercanas?lat=X&lon=Y&radio=Z&combustible=ID` - Estaciones cercanas
- `POST /api/batch/ejecutar/{jobName}` - Ejecutar un job de Spring Batch específico
- `GET /api/batch/estado/{jobExecutionId}` - Consultar estado de ejecución de un job

Documentación Swagger: `http://localhost:8080/swagger-ui.html`

## Funciones de Base de Datos

- **`calcular_distancia_km`**: Función PL/pgSQL que calcula la distancia entre dos puntos geográficos usando la fórmula de Haversine. Se crea automáticamente mediante `DatabaseFunctionInitializer` al iniciar la aplicación (evita problemas con el SQL executor de Spring Boot).

## Componentes Especiales

- **`DatabaseFunctionInitializer`**: Componente `CommandLineRunner` que inicializa funciones PL/pgSQL en la base de datos usando JDBC directamente, evitando problemas con el SQL executor de Spring Boot.
