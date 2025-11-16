#!/bin/bash

# Script de verificación de prerrequisitos
# Sistema de Gestión de Estaciones de Servicio

echo "=========================================="
echo "Verificación de Prerrequisitos"
echo "=========================================="
echo ""

# Verificar Java
echo "1. Verificando Java..."
if command -v java &> /dev/null; then
    JAVA_VERSION=$(java -version 2>&1 | head -n 1)
    echo "   ✅ Java encontrado: $JAVA_VERSION"
    
    # Verificar versión Java 21
    JAVA_MAJOR=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | awk -F '.' '{print $1}')
    if [ "$JAVA_MAJOR" -ge 21 ]; then
        echo "   ✅ Versión Java 21 o superior"
    else
        echo "   ⚠️  Advertencia: Se requiere Java 21, versión actual: $JAVA_MAJOR"
    fi
else
    echo "   ❌ Java no encontrado. Por favor instala Java 21"
    exit 1
fi

echo ""

# Verificar Maven
echo "2. Verificando Maven..."
if command -v mvn &> /dev/null; then
    MVN_VERSION=$(mvn -version | head -n 1)
    echo "   ✅ Maven encontrado: $MVN_VERSION"
else
    echo "   ❌ Maven no encontrado. Por favor instala Maven 3.9+"
    exit 1
fi

echo ""

# Verificar PostgreSQL
echo "3. Verificando PostgreSQL..."
if command -v psql &> /dev/null; then
    PSQL_VERSION=$(psql --version)
    echo "   ✅ PostgreSQL encontrado: $PSQL_VERSION"
    
    # Verificar conexión
    if psql -U postgres -c "SELECT version();" &> /dev/null; then
        echo "   ✅ Conexión a PostgreSQL exitosa"
        
        # Verificar base de datos
        if psql -U postgres -lqt | cut -d \| -f 1 | grep -qw estaciones_db; then
            echo "   ✅ Base de datos 'estaciones_db' existe"
        else
            echo "   ⚠️  Base de datos 'estaciones_db' no existe"
            echo "      Ejecuta: CREATE DATABASE estaciones_db;"
        fi
    else
        echo "   ⚠️  No se pudo conectar a PostgreSQL"
        echo "      Verifica que PostgreSQL esté corriendo y las credenciales sean correctas"
    fi
else
    echo "   ⚠️  PostgreSQL no encontrado en PATH"
    echo "      Verifica que PostgreSQL esté instalado y corriendo"
fi

echo ""

# Verificar puertos
echo "4. Verificando puertos..."
if lsof -Pi :8080 -sTCP:LISTEN -t >/dev/null 2>&1; then
    echo "   ⚠️  Puerto 8080 está en uso"
else
    echo "   ✅ Puerto 8080 disponible"
fi

if lsof -Pi :5432 -sTCP:LISTEN -t >/dev/null 2>&1; then
    echo "   ✅ Puerto 5432 (PostgreSQL) está en uso"
else
    echo "   ⚠️  Puerto 5432 no está en uso (PostgreSQL puede no estar corriendo)"
fi

echo ""

# Verificar estructura del proyecto
echo "5. Verificando estructura del proyecto..."
if [ -f "pom.xml" ]; then
    echo "   ✅ pom.xml encontrado"
else
    echo "   ❌ pom.xml no encontrado"
    exit 1
fi

if [ -d "src/main/java" ]; then
    echo "   ✅ Directorio src/main/java encontrado"
else
    echo "   ❌ Directorio src/main/java no encontrado"
    exit 1
fi

if [ -d "src/main/resources" ]; then
    echo "   ✅ Directorio src/main/resources encontrado"
else
    echo "   ❌ Directorio src/main/resources no encontrado"
    exit 1
fi

echo ""

echo "=========================================="
echo "Verificación completada"
echo "=========================================="
echo ""
echo "Si todos los checks están ✅, puedes ejecutar:"
echo "  mvn spring-boot:run"
echo ""


