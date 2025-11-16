# Diagrama Entidad/Relación - Sistema de Estaciones de Servicio

Este documento contiene el diagrama entidad/relación del sistema de gestión de estaciones de servicio.

## Diagrama ER (Mermaid)

```mermaid
erDiagram
    COMUNIDAD_AUTONOMA {
        VARCHAR id_ccaa PK
        VARCHAR nombre_ccaa UK
    }
    
    PROVINCIA {
        VARCHAR id_provincia PK
        VARCHAR id_ccaa FK
        VARCHAR nombre_provincia
    }
    
    MUNICIPIO {
        VARCHAR id_municipio PK
        VARCHAR id_provincia FK
        VARCHAR id_ccaa FK
        VARCHAR nombre_municipio
    }
    
    EMPRESA {
        BIGSERIAL id_empresa PK
        VARCHAR nombre_rotulo UK
        TIMESTAMP created_at
    }
    
    PRODUCTO_PETROLIFERO {
        VARCHAR id_producto PK
        VARCHAR nombre_producto
        VARCHAR nombre_abreviatura
    }
    
    ESTACION_SERVICIO_TERRESTRE {
        VARCHAR id_eess PK
        BIGINT id_empresa FK
        VARCHAR id_municipio FK
        VARCHAR id_provincia FK
        VARCHAR id_ccaa FK
        VARCHAR codigo_postal
        VARCHAR direccion
        VARCHAR localidad
        VARCHAR margen
        DECIMAL latitud
        DECIMAL longitud
        TEXT horario
        VARCHAR tipo_venta
        VARCHAR remision
        TIMESTAMP fecha_actualizacion
        TIMESTAMP created_at
    }
    
    PRECIO_TERRESTRE {
        BIGSERIAL id_precio PK
        VARCHAR id_eess FK
        VARCHAR id_producto FK
        DECIMAL precio
        TIMESTAMP fecha_registro
    }
    
    ESTACION_SERVICIO_MARITIMA {
        VARCHAR id_poste_maritimo PK
        BIGINT id_empresa FK
        VARCHAR id_municipio FK
        VARCHAR id_provincia FK
        VARCHAR id_ccaa FK
        VARCHAR codigo_postal
        VARCHAR direccion
        VARCHAR localidad
        VARCHAR puerto
        DECIMAL latitud
        DECIMAL longitud
        TEXT horario
        VARCHAR tipo_venta
        VARCHAR remision
        TIMESTAMP fecha_actualizacion
        TIMESTAMP created_at
    }
    
    PRECIO_MARITIMO {
        BIGSERIAL id_precio PK
        VARCHAR id_poste_maritimo FK
        VARCHAR id_producto FK
        DECIMAL precio
        TIMESTAMP fecha_registro
    }
    
    %% Relaciones de Referencia Geográfica
    COMUNIDAD_AUTONOMA ||--o{ PROVINCIA : "tiene"
    PROVINCIA ||--o{ MUNICIPIO : "contiene"
    COMUNIDAD_AUTONOMA ||--o{ MUNICIPIO : "pertenece_a"
    
    %% Relaciones de Estaciones Terrestres
    EMPRESA ||--o{ ESTACION_SERVICIO_TERRESTRE : "opera"
    MUNICIPIO ||--o{ ESTACION_SERVICIO_TERRESTRE : "ubicada_en"
    PROVINCIA ||--o{ ESTACION_SERVICIO_TERRESTRE : "ubicada_en"
    COMUNIDAD_AUTONOMA ||--o{ ESTACION_SERVICIO_TERRESTRE : "ubicada_en"
    ESTACION_SERVICIO_TERRESTRE ||--o{ PRECIO_TERRESTRE : "tiene"
    PRODUCTO_PETROLIFERO ||--o{ PRECIO_TERRESTRE : "precio_de"
    
    %% Relaciones de Estaciones Marítimas
    EMPRESA ||--o{ ESTACION_SERVICIO_MARITIMA : "opera"
    MUNICIPIO ||--o{ ESTACION_SERVICIO_MARITIMA : "ubicada_en"
    PROVINCIA ||--o{ ESTACION_SERVICIO_MARITIMA : "ubicada_en"
    COMUNIDAD_AUTONOMA ||--o{ ESTACION_SERVICIO_MARITIMA : "ubicada_en"
    ESTACION_SERVICIO_MARITIMA ||--o{ PRECIO_MARITIMO : "tiene"
    PRODUCTO_PETROLIFERO ||--o{ PRECIO_MARITIMO : "precio_de"
```

## Descripción de Entidades

### Tablas de Referencia Geográfica

- **COMUNIDAD_AUTONOMA**: Comunidades autónomas de España
- **PROVINCIA**: Provincias de España (pertenecen a una comunidad autónoma)
- **MUNICIPIO**: Municipios de España (pertenecen a una provincia y comunidad autónoma)

### Tablas de Referencia de Negocio

- **EMPRESA**: Empresas operadoras de estaciones de servicio
- **PRODUCTO_PETROLIFERO**: Productos petrolíferos disponibles (Gasolina 95 E5, Gasóleo A, etc.)

### Tablas de Estaciones Terrestres

- **ESTACION_SERVICIO_TERRESTRE**: Estaciones de servicio terrestres con información de ubicación y coordenadas GPS
- **PRECIO_TERRESTRE**: Precios de productos en estaciones terrestres (relación muchos a muchos entre estaciones y productos)

### Tablas de Estaciones Marítimas

- **ESTACION_SERVICIO_MARITIMA**: Estaciones de servicio marítimas (postes marítimos) con información de puerto
- **PRECIO_MARITIMO**: Precios de productos en estaciones marítimas (relación muchos a muchos entre estaciones y productos)

## Cardinalidades

- **1:N**: Una comunidad autónoma tiene muchas provincias
- **1:N**: Una provincia contiene muchos municipios
- **1:N**: Una empresa opera muchas estaciones (tanto terrestres como marítimas)
- **1:N**: Una estación tiene muchos precios (uno por producto)
- **1:N**: Un producto tiene muchos precios (uno por estación)

## Notas Importantes

- Las claves primarias están marcadas como **PK**
- Las claves únicas están marcadas como **UK**
- Las claves foráneas están marcadas como **FK**
- Los tipos de datos están simplificados (VARCHAR, DECIMAL, BIGSERIAL, TIMESTAMP)
- Las relaciones de eliminación son principalmente `ON DELETE RESTRICT` para mantener la integridad referencial
- Los precios se eliminan en cascada cuando se elimina una estación (`ON DELETE CASCADE`)

