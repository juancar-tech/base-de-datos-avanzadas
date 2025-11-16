# Estructura del Repositorio

## Organización

Este repositorio contiene las actividades de la asignatura **Base de Datos Avanzadas**, organizadas de forma independiente.

```
base-de-datos-avanzadas/
├── README.md              # README principal del repositorio
├── .gitignore             # Archivos ignorados por git
├── .cursorrules           # Reglas para Cursor AI
│
├── actividad1/            # ✅ Sistema de Gestión de Estaciones de Servicio
│   ├── README.md          # Documentación de la actividad 1
│   ├── pom.xml            # Configuración Maven
│   ├── src/               # Código fuente
│   ├── docs/              # Diagramas y documentación adicional
│   └── scripts/           # Scripts SQL y utilidades
│
├── actividad2/            # ⏳ Pendiente
│   └── README.md
│
└── actividad3/            # ⏳ Pendiente
    └── README.md
```

## Características

- **Independencia**: Cada actividad es un proyecto independiente
- **Organización**: Estructura clara y separada
- **Documentación**: Cada actividad tiene su propio README
- **Versionado**: Todo está bajo control de versiones con Git

## Cómo Trabajar con Cada Actividad

### Actividad 1

```bash
# Navegar a la actividad
cd actividad1

# Compilar
mvn clean compile

# Ejecutar
mvn spring-boot:run
```

Ver [actividad1/README.md](actividad1/README.md) para más detalles.

### Actividad 2 y 3

Cuando se implementen, seguirán la misma estructura que actividad1.

## Notas

- Cada actividad puede tener sus propias dependencias y configuraciones
- Los proyectos son independientes entre sí
- Se recomienda mantener la misma estructura de carpetas en todas las actividades

