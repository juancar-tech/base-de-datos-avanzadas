# Esquemas XSD

Este directorio contiene los esquemas XSD para validar los XML recibidos de las APIs del Ministerio de Energía.

## Archivos Requeridos

Colocar aquí los siguientes archivos XSD:

- `estaciones-terrestres.xsd` - Esquema para estaciones terrestres
- `postes-maritimos.xsd` - Esquema para postes marítimos
- `comunidades-autonomas.xsd` - Esquema para comunidades autónomas
- `provincias.xsd` - Esquema para provincias
- `municipios.xsd` - Esquema para municipios
- `productos-petroliferos.xsd` - Esquema para productos petrolíferos

## Generación de Clases JAXB

Una vez que los XSD estén disponibles, se pueden generar las clases JAXB usando:

```bash
xjc -d src/main/java -p com.estaciones.infrastructure.xml.model src/main/resources/schemas/*.xsd
```

O usando el plugin Maven `jaxb2-maven-plugin`.

## Namespace

Todos los XML del Ministerio de Energía usan el namespace:
```
http://schemas.datacontract.org/2004/07/ServiciosCarburantes
```


