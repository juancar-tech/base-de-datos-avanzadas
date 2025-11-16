# Estructura del Proyecto - Sistema de Gestión de Estaciones de Servicio

## Arquitectura Hexagonal

El proyecto sigue una arquitectura hexagonal (puertos y adaptadores) que separa la lógica de negocio de la infraestructura:

```
src/main/java/com/estaciones/
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
│   │   └── ProductoPetroliferoRepository.java
│   └── exception/
│       └── DomainException.java
│
├── infrastructure/                  # Capa de Infraestructura
│   ├── persistence/
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
│   │       │   ├── EmpresaRepositoryJpaAdapter.java
│   │       │   └── ... (otros repositorios)
│   │       └── mapper/              # Mappers: Domain ↔ JPA Entity
│   │           ├── EmpresaMapper.java
│   │           ├── ComunidadAutonomaMapper.java
│   │           └── ... (otros mappers)
│   ├── xml/
│   │   ├── client/                  # Clientes REST para consumir APIs
│   │   │   └── EnergiaApiClient.java
│   │   ├── validator/               # Validación XML contra XSD
│   │   │   └── XmlSchemaValidator.java
│   │   ├── config/
│   │   │   ├── RestTemplateConfig.java
│   │   │   └── XmlValidatorConfig.java
│   │   └── exception/
│   │       └── XmlValidationException.java
│   ├── batch/
│   │   ├── config/
│   │   │   └── BatchConfig.java
│   │   └── job/
│   │       └── CargarComunidadesAutonomasJob.java
│   └── persistence/config/
│       └── PersistenceConfig.java
│
├── application/                     # Capa de Aplicación
│   ├── service/                     # Servicios de aplicación
│   │   ├── EmpresaService.java
│   │   └── EstacionService.java
│   └── dto/                          # DTOs para API REST
│       ├── ApiResponse.java
│       ├── EmpresaDTO.java
│       └── EstacionCercanaDTO.java
│
└── adapter/
    └── rest/                         # Controladores REST
        ├── EmpresaController.java
        └── EstacionController.java
```

## Flujo de Datos

1. **API Externa** → `EnergiaApiClient` → XML sin validar
2. **Validación XML** → `XmlSchemaValidator` → XML validado contra XSD
3. **Parseo JAXB** → Clases JAXB generadas → Objetos Java
4. **Mapeo a Dominio** → Objetos de dominio (Records)
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

- Validación XML obligatoria antes de procesar
- Transacciones por chunks de 100 registros
- Reintento automático en caso de fallos transitorios

## API REST

Endpoints disponibles:

- `GET /api/empresas/mas-estaciones-terrestres` - Empresa con más estaciones terrestres
- `GET /api/estaciones/cercanas?lat=X&lon=Y&radio=Z&combustible=ID` - Estaciones cercanas

Documentación Swagger: `http://localhost:8080/swagger-ui.html`


