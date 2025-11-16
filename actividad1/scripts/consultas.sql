-- =====================================================
-- Consultas SQL - Sistema de Gestión de Estaciones de Servicio
-- PostgreSQL 16
-- =====================================================


-- =====================================================
-- CONSULTA 1: Nombre de la empresa con más estaciones de servicio terrestres
-- =====================================================

SELECT 
    e.nombre_rotulo AS empresa,
    COUNT(est.id_eess) AS total_estaciones
FROM empresa e
INNER JOIN estacion_servicio_terrestre est ON e.id_empresa = est.id_empresa
GROUP BY e.id_empresa, e.nombre_rotulo
ORDER BY total_estaciones DESC
LIMIT 1;

-- =====================================================
-- CONSULTA 2: Nombre de la empresa con más estaciones de servicio marítimas
-- =====================================================

SELECT 
    e.nombre_rotulo AS empresa,
    COUNT(est.id_poste_maritimo) AS total_estaciones
FROM empresa e
INNER JOIN estacion_servicio_maritima est ON e.id_empresa = est.id_empresa
GROUP BY e.id_empresa, e.nombre_rotulo
ORDER BY total_estaciones DESC
LIMIT 1;

-- =====================================================
-- CONSULTA 3: Estación con precio más bajo de "Gasolina 95 E5" en Madrid
-- =====================================================
-- Nota: "Gasolina 95 E5" tiene id_producto = '1'
-- Madrid tiene id_ccaa = '13' (Comunidad de Madrid)

SELECT 
    est.direccion AS localizacion,
    est.localidad,
    est.codigo_postal,
    e.nombre_rotulo AS empresa,
    est.margen,
    pt.precio AS precio_gasolina_95_e5,
    est.latitud,
    est.longitud
FROM estacion_servicio_terrestre est
INNER JOIN empresa e ON est.id_empresa = e.id_empresa
INNER JOIN precio_terrestre pt ON est.id_eess = pt.id_eess
INNER JOIN producto_petrolifero prod ON pt.id_producto = prod.id_producto
INNER JOIN comunidad_autonoma ccaa ON est.id_ccaa = ccaa.id_ccaa
WHERE prod.nombre_producto = 'Gasolina 95 E5'
  AND ccaa.nombre_ccaa = 'Madrid'
  AND pt.precio IS NOT NULL
ORDER BY pt.precio ASC
LIMIT 1;

-- =====================================================
-- CONSULTA 4: Estación con precio más bajo de "Gasóleo A" 
--            a menos de 10 km del centro de Albacete
-- =====================================================
-- Nota: "Gasóleo A habitual" tiene id_producto = '4'
-- Coordenadas del centro de Albacete: aproximadamente 38.9942, -1.8561
-- Coordenadas del centro de Torrejón de Velasco: aproximadamente 40.189185, -3.778521

-- Usando la fórmula de Haversine directamente y la función calcular_distancia_km

WITH distancias AS (
    SELECT 
        est.id_eess,
        est.direccion AS localizacion,
        est.localidad,
        est.codigo_postal,
        e.nombre_rotulo AS empresa,
        est.margen,
        pt.precio AS precio_gasoleo_a,
        est.latitud,
        est.longitud,
        calcular_distancia_km(
            est.latitud,
            est.longitud,
            38.9942,   -- latitud de referencia
            -1.8561    -- longitud de referencia
        ) AS distancia_km
    FROM estacion_servicio_terrestre est
    INNER JOIN empresa e ON est.id_empresa = e.id_empresa
    INNER JOIN precio_terrestre pt ON est.id_eess = pt.id_eess
    INNER JOIN producto_petrolifero prod ON pt.id_producto = prod.id_producto
    WHERE prod.nombre_producto = 'Gasóleo A habitual'
      AND est.latitud IS NOT NULL
      AND est.longitud IS NOT NULL
      AND pt.precio IS NOT NULL
      AND pt.precio > 0
)
SELECT 
    localizacion,
    localidad,
    codigo_postal,
    empresa,
    margen,
    precio_gasoleo_a,
    distancia_km,
    latitud,
    longitud
FROM distancias
WHERE distancia_km <= 10
ORDER BY precio_gasoleo_a asc, distancia_km ASC
LIMIT 1;



WITH distancias AS (
    SELECT 
        est.id_eess,
        est.direccion AS localizacion,
        est.localidad,
        est.codigo_postal,
        e.nombre_rotulo AS empresa,
        est.margen,
        pt.precio AS precio_gasoleo_a,
        est.latitud,
        est.longitud,
        -- Fórmula de Haversine para calcular distancia en km
        -- Coordenadas del centro de Albacete: 38.9942, -1.8561
        (6371 * 2 * atan2(
            sqrt(
                sin(radians((est.latitud - 38.9942) / 2)) * sin(radians((est.latitud - 38.9942) / 2)) + 
                cos(radians(38.9942)) * cos(radians(est.latitud)) * 
                sin(radians((est.longitud - (-1.8561)) / 2)) * sin(radians((est.longitud - (-1.8561)) / 2))
            ),
            sqrt(1 - (
                sin(radians((est.latitud - 38.9942) / 2)) * sin(radians((est.latitud - 38.9942) / 2)) + 
                cos(radians(38.9942)) * cos(radians(est.latitud)) * 
                sin(radians((est.longitud - (-1.8561)) / 2)) * sin(radians((est.longitud - (-1.8561)) / 2))
            ))
        )) AS distancia_km
    FROM estacion_servicio_terrestre est
    INNER JOIN empresa e ON est.id_empresa = e.id_empresa
    INNER JOIN precio_terrestre pt ON est.id_eess = pt.id_eess
    INNER JOIN producto_petrolifero prod ON pt.id_producto = prod.id_producto
    WHERE prod.nombre_producto = 'Gasóleo A habitual'
      AND est.latitud IS NOT NULL
      AND est.longitud IS NOT NULL
      AND pt.precio IS NOT NULL
      AND pt.precio > 0
)
SELECT 
    localizacion,
    localidad,
    codigo_postal,
    empresa,
    margen,
    precio_gasoleo_a,
    distancia_km,
    latitud,
    longitud
FROM distancias
WHERE distancia_km <= 10
ORDER BY precio_gasoleo_a ASC
LIMIT 1;

-- =====================================================
-- CONSULTA 5: Provincia de la estación marítima con "Gasolina 95 E5" más cara
-- =====================================================
-- Nota: "Gasolina 95 E5" tiene id_producto = '1'

SELECT 
    prov.nombre_provincia AS provincia,
    est.id_poste_maritimo,
    est.direccion AS localizacion,
    est.localidad,
    e.nombre_rotulo AS empresa,
    pm.precio AS precio_gasolina_95_e5
FROM estacion_servicio_maritima est
INNER JOIN empresa e ON est.id_empresa = e.id_empresa
INNER JOIN precio_maritimo pm ON est.id_poste_maritimo = pm.id_poste_maritimo
INNER JOIN producto_petrolifero prod ON pm.id_producto = prod.id_producto
INNER JOIN provincia prov ON est.id_provincia = prov.id_provincia
WHERE prod.nombre_producto = 'Gasolina 95 E5'
  AND pm.precio IS NOT NULL
ORDER BY pm.precio DESC
LIMIT 1;

-- =====================================================
-- FIN DEL SCRIPT
-- =====================================================

