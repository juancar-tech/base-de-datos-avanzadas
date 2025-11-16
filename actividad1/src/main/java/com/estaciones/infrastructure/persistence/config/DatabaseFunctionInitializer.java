package com.estaciones.infrastructure.persistence.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Componente que inicializa funciones de base de datos que no pueden ser ejecutadas
 * correctamente por el SQL executor de Spring Boot (especialmente funciones PL/pgSQL).
 * 
 * Este componente se ejecuta después de que Spring Boot haya ejecutado los scripts SQL,
 * asegurando que las funciones estén disponibles.
 */
@Component
@Order(1) // Ejecutar después de la inicialización de la base de datos
public class DatabaseFunctionInitializer implements CommandLineRunner {
    
    private static final Logger log = LoggerFactory.getLogger(DatabaseFunctionInitializer.class);
    
    private final JdbcTemplate jdbcTemplate;
    
    public DatabaseFunctionInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public void run(String... args) {
        log.info("=== Inicializando funciones de base de datos ===");
        createHaversineFunction();
        log.info("=== Funciones de base de datos inicializadas ===");
    }
    
    /**
     * Crea la función calcular_distancia_km usando la fórmula de Haversine.
     * Esta función calcula la distancia en kilómetros entre dos puntos geográficos.
     */
    private void createHaversineFunction() {
        String functionSql = """
            CREATE OR REPLACE FUNCTION calcular_distancia_km(
                lat1 DECIMAL,
                lon1 DECIMAL,
                lat2 DECIMAL,
                lon2 DECIMAL
            ) RETURNS DECIMAL AS $func$
            DECLARE
                R DECIMAL := 6371; -- Radio de la Tierra en km
                dlat DECIMAL;
                dlon DECIMAL;
                a DECIMAL;
                c DECIMAL;
            BEGIN
                dlat := radians(lat2 - lat1);
                dlon := radians(lon2 - lon1);

                a := sin(dlat/2) * sin(dlat/2) + 
                     cos(radians(lat1)) * cos(radians(lat2)) * 
                     sin(dlon/2) * sin(dlon/2);

                c := 2 * atan2(sqrt(a), sqrt(1-a));

                RETURN R * c;
            END;
            $func$ LANGUAGE plpgsql IMMUTABLE;
            """;
        
        try {
            jdbcTemplate.execute(functionSql);
            log.info("✅ Función calcular_distancia_km creada correctamente");
            
            // Verificar que la función existe
            String checkFunction = """
                SELECT EXISTS (
                    SELECT 1 
                    FROM pg_proc 
                    WHERE proname = 'calcular_distancia_km'
                );
                """;
            
            Boolean exists = jdbcTemplate.queryForObject(checkFunction, Boolean.class);
            if (Boolean.TRUE.equals(exists)) {
                log.info("✅ Función calcular_distancia_km verificada en la base de datos");
            } else {
                log.warn("⚠️ La función calcular_distancia_km no se encontró después de crearla");
            }
        } catch (Exception e) {
            log.error("❌ Error al crear la función calcular_distancia_km: {}", e.getMessage(), e);
            // No lanzamos la excepción para que la aplicación pueda continuar
            // La función puede ya existir o haber sido creada manualmente
        }
    }
}

