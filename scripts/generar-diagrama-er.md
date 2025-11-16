# Generación del Diagrama ER

Este documento explica cómo generar el diagrama entidad/relación a partir del schema SQL.

## Opción 1: Mermaid (Recomendado)

El archivo `docs/diagrama-er.md` contiene el diagrama en formato Mermaid que se renderiza automáticamente en:
- GitHub
- GitLab
- Visual Studio Code (con extensión Mermaid)
- Cualquier visor de Markdown que soporte Mermaid

### Visualizar en VS Code
1. Instalar la extensión "Markdown Preview Mermaid Support"
2. Abrir `docs/diagrama-er.md`
3. Presionar `Ctrl+Shift+V` para previsualizar

### Visualizar online
- Copiar el código Mermaid a: https://mermaid.live/

### Exportar como Imagen para Word (Método Recomendado)

#### Método 1: mermaid.live (Más fácil)
1. Ir a https://mermaid.live/
2. Copiar el código Mermaid del bloque ````mermaid` en `docs/diagrama-er.md` (líneas 7-113)
3. Pegar el código en el editor de mermaid.live
4. El diagrama se renderizará automáticamente
5. Click en el botón **"Actions"** (arriba a la derecha)
6. Seleccionar **"Download PNG"** o **"Download SVG"**
   - PNG: mejor para Word (imagen rasterizada)
   - SVG: mejor calidad pero Word puede tener problemas
7. Insertar la imagen descargada en Word: Insertar → Imágenes → Este dispositivo

#### Método 2: Extensión VS Code "Markdown Preview Mermaid Support"
1. Instalar la extensión "Markdown Preview Mermaid Support" en VS Code
2. Abrir `docs/diagrama-er.md`
3. Presionar `Ctrl+Shift+V` para previsualizar
4. Click derecho en el diagrama renderizado
5. Seleccionar "Save Image As..." o "Copy Image"
6. Si no aparece esta opción, hacer screenshot con `Windows + Shift + S`

#### Método 3: Herramienta de línea de comandos (mermaid-cli)
```bash
# Instalar mermaid-cli globalmente
npm install -g @mermaid-js/mermaid-cli

# Generar PNG desde el archivo markdown
mmdc -i docs/diagrama-er.md -o docs/images/diagrama-er.png -b transparent

# O generar SVG (mejor calidad)
mmdc -i docs/diagrama-er.md -o docs/images/diagrama-er.svg -b transparent
```

#### Método 4: Screenshot (Rápido pero menos profesional)
1. Abrir `docs/diagrama-er.md` en GitHub o GitLab
2. Hacer screenshot del diagrama renderizado
3. Pegar directamente en Word (Word puede optimizar la imagen automáticamente)

## Opción 2: PlantUML

El archivo `docs/diagrama-er.puml` contiene el diagrama en formato PlantUML.

### Requisitos
- Java instalado
- PlantUML JAR o extensión de VS Code

### Visualizar con VS Code
1. Instalar extensión "PlantUML"
2. Abrir `docs/diagrama-er.puml`
3. Presionar `Alt+D` para previsualizar

### Visualizar online
- Subir el archivo a: http://www.plantuml.com/plantuml/uml/

### Generar imagen desde línea de comandos
```bash
java -jar plantuml.jar docs/diagrama-er.puml -o ../docs/images/
```

## Opción 3: Herramientas Online

### dbdiagram.io
1. Ir a https://dbdiagram.io/
2. Importar el archivo `src/main/resources/db/schema.sql`
3. La herramienta generará automáticamente el diagrama

### SQL DDL to ER Diagram
1. Ir a https://www.dbml.org/dbml-to-erd/
2. Convertir el schema SQL a DBML
3. Generar el diagrama ER

## Opción 4: Herramientas de Base de Datos

### pgAdmin (PostgreSQL)
1. Conectar a la base de datos
2. Click derecho en la base de datos → "Generate ERD"
3. Exportar como imagen

### DBeaver
1. Conectar a la base de datos PostgreSQL
2. Click derecho en la base de datos → "View Diagram"
3. Exportar como imagen PNG/SVG

## Estructura del Diagrama

El diagrama incluye:

### Entidades de Referencia Geográfica
- `COMUNIDAD_AUTONOMA`
- `PROVINCIA`
- `MUNICIPIO`

### Entidades de Negocio
- `EMPRESA`
- `PRODUCTO_PETROLIFERO`

### Entidades de Estaciones Terrestres
- `ESTACION_SERVICIO_TERRESTRE`
- `PRECIO_TERRESTRE`

### Entidades de Estaciones Marítimas
- `ESTACION_SERVICIO_MARITIMA`
- `PRECIO_MARITIMO`

### Relaciones
- Relaciones 1:N entre entidades geográficas
- Relaciones 1:N entre empresas y estaciones
- Relaciones N:M entre estaciones y productos (a través de tablas de precios)

