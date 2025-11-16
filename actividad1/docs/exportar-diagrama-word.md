# Guía Rápida: Exportar Diagrama ER a Word

## Método Más Rápido (Recomendado)

### Paso 1: Copiar el código Mermaid
Abre el archivo `docs/diagrama-er.md` y copia el código entre las líneas 7-113 (el bloque ````mermaid`).

### Paso 2: Ir a mermaid.live
1. Abre tu navegador y ve a: **https://mermaid.live/**
2. Pega el código Mermaid copiado en el editor
3. El diagrama se renderizará automáticamente

### Paso 3: Descargar la imagen
1. Click en el botón **"Actions"** (arriba a la derecha del editor)
2. Selecciona **"Download PNG"** (mejor para Word)
3. Guarda el archivo en tu computadora

### Paso 4: Insertar en Word
1. Abre tu documento Word
2. Ve a **Insertar** → **Imágenes** → **Este dispositivo**
3. Selecciona el archivo PNG descargado
4. Ajusta el tamaño si es necesario

## Método Alternativo: Desde VS Code

Si tienes la extensión "Markdown Preview Mermaid Support":

1. Abre `docs/diagrama-er.md` en VS Code
2. Presiona `Ctrl+Shift+V` para previsualizar
3. Click derecho sobre el diagrama
4. Si aparece "Save Image As...", úsalo
5. Si no, usa `Windows + Shift + S` para hacer screenshot

## Método Avanzado: Línea de Comandos

Si tienes Node.js instalado:

```bash
# Instalar herramienta
npm install -g @mermaid-js/mermaid-cli

# Generar imagen PNG
mmdc -i docs/diagrama-er.md -o diagrama-er.png -b transparent
```

## Consejos para Word

- **Formato recomendado**: PNG con fondo transparente
- **Tamaño**: El diagrama puede ser grande, ajusta el tamaño en Word manteniendo las proporciones
- **Calidad**: Si necesitas mejor calidad, usa SVG y conviértelo a PNG con alta resolución
- **Posicionamiento**: En Word, click derecho → "Ajustar texto" → "Delante del texto" para moverlo libremente

## Código Mermaid Completo

Si necesitas copiar el código directamente, está en `docs/diagrama-er.md` desde la línea 7 hasta la 113.

