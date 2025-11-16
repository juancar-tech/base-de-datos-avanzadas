# Importar API en SOAPUI

## Problema Común

Si intentas importar desde `http://localhost:8080/swagger-ui.html`, obtendrás un error porque esa URL devuelve HTML, no el formato OpenAPI que SOAPUI necesita.

## Solución: Usar la URL Correcta

SOAPUI necesita importar desde la URL del **OpenAPI JSON**, no desde Swagger UI.

### URL Correcta para SOAPUI

```
http://localhost:8080/api-docs
```

O específicamente (formato estándar OpenAPI 3.0):

```
http://localhost:8080/v3/api-docs
```

## Pasos para Importar en SOAPUI

### 1. Asegúrate de que la Aplicación Esté Corriendo

```bash
mvn spring-boot:run
```

Verifica que la aplicación esté corriendo accediendo a:
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/api-docs

### 2. Importar en SOAPUI

#### Opción A: Desde OpenAPI/REST

1. Abre SOAPUI
2. **File** → **Import Project** → **REST** → **From URI**
3. En la URL, ingresa:
   ```
   http://localhost:8080/api-docs
   ```
4. Click en **OK**
5. SOAPUI descargará y parseará el OpenAPI JSON

#### Opción B: Desde OpenAPI Specification

1. Abre SOAPUI
2. **File** → **Import Project** → **OpenAPI**
3. Selecciona **From URL**
4. Ingresa la URL:
   ```
   http://localhost:8080/api-docs
   ```
5. Click en **OK**

#### Opción C: Descargar y Importar Manualmente

1. Abre en tu navegador:
   ```
   http://localhost:8080/api-docs
   ```
2. Guarda el contenido JSON en un archivo (ej: `openapi.json`)
3. En SOAPUI: **File** → **Import Project** → **OpenAPI** → **From File**
4. Selecciona el archivo `openapi.json`

### 3. Verificar la Importación

Después de importar, deberías ver:

- **REST Project** con el nombre de tu API
- **Endpoints** disponibles:
  - `GET /api/empresas/mas-estaciones-terrestres`
  - `GET /api/estaciones/cercanas`
- **Request/Response** examples generados automáticamente

## URLs Disponibles

| Propósito | URL |
|-----------|-----|
| **Swagger UI** (interfaz web) | http://localhost:8080/swagger-ui.html |
| **OpenAPI JSON** (para SOAPUI) | http://localhost:8080/api-docs |
| **OpenAPI YAML** | http://localhost:8080/api-docs.yaml |

## Solución de Problemas

### Error: "Cannot connect to server"

**Problema:** La aplicación no está corriendo o el puerto es diferente.

**Solución:**
1. Verifica que la aplicación esté corriendo: `mvn spring-boot:run`
2. Verifica el puerto en `application.properties` (por defecto: 8080)
3. Prueba acceder a http://localhost:8080/api-docs en tu navegador

### Error: "Invalid OpenAPI specification"

**Problema:** La URL no devuelve JSON válido.

**Solución:**
1. Verifica que estés usando `/api-docs` y no `/swagger-ui.html`
2. Abre http://localhost:8080/api-docs en tu navegador y verifica que devuelva JSON
3. Si hay errores en la aplicación, revísalos en los logs

### Error: "The element type 'meta' must be terminated..."

**Problema:** Estás intentando importar desde Swagger UI (HTML) en lugar de OpenAPI JSON.

**Solución:**
- ❌ **NO uses:** `http://localhost:8080/swagger-ui.html`
- ✅ **USA:** `http://localhost:8080/api-docs`

## Verificar que OpenAPI Está Disponible

Antes de importar en SOAPUI, verifica que el OpenAPI JSON esté disponible:

```bash
# Con cURL
curl http://localhost:8080/api-docs

# O abre en tu navegador
# http://localhost:8080/api-docs
```

Deberías ver un JSON con la estructura OpenAPI 3.0.

## Notas Adicionales

- SOAPUI soporta OpenAPI 2.0 (Swagger) y OpenAPI 3.0
- SpringDoc genera OpenAPI 3.0 por defecto
- Si SOAPUI tiene problemas con OpenAPI 3.0, puedes configurar SpringDoc para generar 2.0, pero no es necesario en versiones recientes de SOAPUI


