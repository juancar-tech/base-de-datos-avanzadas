@echo off
REM Script de verificación de prerrequisitos (Windows)
REM Sistema de Gestión de Estaciones de Servicio

echo ==========================================
echo Verificación de Prerrequisitos
echo ==========================================
echo.

REM Verificar Java
echo 1. Verificando Java...
java -version >nul 2>&1
if %errorlevel% == 0 (
    echo    ✅ Java encontrado
    java -version
) else (
    echo    ❌ Java no encontrado. Por favor instala Java 21
    exit /b 1
)

echo.

REM Verificar Maven
echo 2. Verificando Maven...
mvn -version >nul 2>&1
if %errorlevel% == 0 (
    echo    ✅ Maven encontrado
    mvn -version | findstr /C:"Apache Maven"
) else (
    echo    ❌ Maven no encontrado. Por favor instala Maven 3.9+
    exit /b 1
)

echo.

REM Verificar PostgreSQL
echo 3. Verificando PostgreSQL...
psql --version >nul 2>&1
if %errorlevel% == 0 (
    echo    ✅ PostgreSQL encontrado
    psql --version
) else (
    echo    ⚠️  PostgreSQL no encontrado en PATH
    echo       Verifica que PostgreSQL esté instalado
)

echo.

REM Verificar estructura del proyecto
echo 4. Verificando estructura del proyecto...
if exist "pom.xml" (
    echo    ✅ pom.xml encontrado
) else (
    echo    ❌ pom.xml no encontrado
    exit /b 1
)

if exist "src\main\java" (
    echo    ✅ Directorio src\main\java encontrado
) else (
    echo    ❌ Directorio src\main\java no encontrado
    exit /b 1
)

if exist "src\main\resources" (
    echo    ✅ Directorio src\main\resources encontrado
) else (
    echo    ❌ Directorio src\main\resources no encontrado
    exit /b 1
)

echo.

echo ==========================================
echo Verificación completada
echo ==========================================
echo.
echo Si todos los checks están ✅, puedes ejecutar:
echo   mvn spring-boot:run
echo.

pause


