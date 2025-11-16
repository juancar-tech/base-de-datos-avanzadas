# Guía: Subir Proyecto a GitHub

## Pasos para subir actividad1 a GitHub

### 1. Verificar estado actual
```bash
git status
```

### 2. Agregar todos los archivos
```bash
git add .
```

### 3. Hacer commit inicial
```bash
git commit -m "feat: Actividad 1 - Sistema de Gestión de Estaciones de Servicio

- Arquitectura hexagonal con Spring Boot
- Integración con API del Ministerio de Energía
- Persistencia con PostgreSQL y JPA
- Procesos batch para carga de datos
- API REST documentada
- Diagrama ER incluido"
```

### 4. Agregar el repositorio remoto
```bash
git remote add origin https://github.com/juancar-tech/base-de-datos-avanzadas.git
```

### 5. Verificar el remote
```bash
git remote -v
```

### 6. Cambiar nombre de rama a main (si es necesario)
```bash
git branch -M main
```

### 7. Subir al repositorio
```bash
git push -u origin main
```

## Comandos útiles para futuras actualizaciones

### Ver cambios
```bash
git status
```

### Agregar cambios específicos
```bash
git add archivo1.java archivo2.java
```

### Hacer commit
```bash
git commit -m "descripción del cambio"
```

### Subir cambios
```bash
git push
```

## Estructura recomendada para múltiples actividades

Si vas a tener múltiples actividades en el mismo repositorio, puedes organizarlas así:

```
base-de-datos-avanzadas/
├── actividad1/
│   ├── src/
│   ├── pom.xml
│   └── README.md
├── actividad2/
│   └── ...
└── README.md (principal del repositorio)
```

Para esto, puedes:
1. Crear una carpeta `actividad1` en el repositorio
2. Mover todos los archivos actuales a esa carpeta
3. O mantener la estructura actual y crear subcarpetas para futuras actividades

## Notas importantes

- El archivo `.gitignore` ya está configurado para excluir:
  - Archivos compilados (`target/`, `*.class`)
  - Configuraciones de IDE (`.idea/`, `.vscode/`)
  - Archivos locales de configuración
  - Logs

- **NO** subir archivos sensibles como:
  - Contraseñas de base de datos
  - API keys
  - Archivos `application-local.properties` con credenciales

