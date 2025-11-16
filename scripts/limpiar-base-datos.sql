- =====================================================
-- Script para limpiar completamente la base de datos
-- PostgreSQL 16
-- =====================================================
-- 
-- USO:
--   psql -U profesor -d estaciones_db -f scripts/limpiar-base-datos.sql
-- 
-- O desde psql:
--   \c estaciones_db
--   \i scripts/limpiar-base-datos.sql
-- 
-- ADVERTENCIA: Este script elimina TODOS los datos y tablas.
-- =====================================================

-- Desactivar temporalmente las verificaciones de foreign keys
SET session_replication_role = 'replica';

-- =====================================================
-- ELIMINAR TABLAS DE PRECIOS (con foreign keys)
-- =====================================================

DROP TABLE IF EXISTS precio_terrestre CASCADE;
DROP TABLE IF EXISTS precio_maritimo CASCADE;

-- =====================================================
-- ELIMINAR TABLAS DE ESTACIONES (con foreign keys)
-- =====================================================

DROP TABLE IF EXISTS estacion_servicio_terrestre CASCADE;
DROP TABLE IF EXISTS estacion_servicio_maritima CASCADE;

-- =====================================================
-- ELIMINAR TABLAS DE REFERENCIA
-- =====================================================

DROP TABLE IF EXISTS municipio CASCADE;
DROP TABLE IF EXISTS provincia CASCADE;
DROP TABLE IF EXISTS comunidad_autonoma CASCADE;
DROP TABLE IF EXISTS empresa CASCADE;
DROP TABLE IF EXISTS producto_petrolifero CASCADE;

-- =====================================================
-- ELIMINAR TABLAS DE SPRING BATCH
-- =====================================================

DROP TABLE IF EXISTS batch_step_execution_context CASCADE;
DROP TABLE IF EXISTS batch_job_execution_context CASCADE;
DROP TABLE IF EXISTS batch_step_execution CASCADE;
DROP TABLE IF EXISTS batch_job_execution_params CASCADE;
DROP TABLE IF EXISTS batch_job_execution CASCADE;
DROP TABLE IF EXISTS batch_job_instance CASCADE;
DROP SEQUENCE batch_job_execution_seq CASCADE;
DROP SEQUENCE batch_job_seq CASCADE;
DROP SEQUENCE batch_step_execution_seq CASCADE;

-- =====================================================
-- ELIMINAR VISTAS (si existen)
-- =====================================================

DROP VIEW IF EXISTS v_estaciones_terrestres_completa CASCADE;

-- =====================================================
-- ELIMINAR FUNCIONES (si existen)
-- =====================================================

DROP FUNCTION IF EXISTS calcular_distancia_km(DECIMAL, DECIMAL, DECIMAL, DECIMAL) CASCADE;

-- =====================================================
-- ELIMINAR ÍNDICES (si quedan algunos huérfanos)
-- =====================================================

-- Los índices se eliminan automáticamente con las tablas,
-- pero por si acaso quedan algunos:

-- No es necesario eliminar índices manualmente, se eliminan con las tablas

-- =====================================================
-- REACTIVAR VERIFICACIONES DE FOREIGN KEYS
-- =====================================================

SET session_replication_role = 'origin';

-- =====================================================
-- VERIFICAR QUE SE HAYAN ELIMINADO TODAS LAS TABLAS
-- =====================================================

-- Mostrar las tablas restantes (debería estar vacío)
SELECT 
    table_name 
FROM 
    information_schema.tables 
WHERE 
    table_schema = 'public' 
    AND table_type = 'BASE TABLE'
ORDER BY 
    table_name;

-- =====================================================
-- FIN DEL SCRIPT
-- =====================================================


