package com.estaciones.infrastructure.batch.reader;

import com.estaciones.domain.model.ComunidadAutonoma;
import com.estaciones.infrastructure.xml.client.EnergiaApiClient;
import com.estaciones.infrastructure.xml.mapper.JaxbToDomainMapper;
import com.estaciones.infrastructure.xml.model.ArrayOfComunidadAutonoma;
import com.estaciones.infrastructure.xml.validator.XmlSchemaValidator;
import jakarta.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * Reader lazy para comunidades autónomas.
 * Solo carga los datos cuando se ejecuta el job, no al iniciar la aplicación.
 */
public class LazyComunidadesAutonomasReader implements ItemReader<ComunidadAutonoma> {
    
    private static final Logger log = LoggerFactory.getLogger(LazyComunidadesAutonomasReader.class);
    
    private final EnergiaApiClient apiClient;
    private final XmlSchemaValidator validator;
    
    private ItemReader<ComunidadAutonoma> delegate;
    private boolean initialized = false;
    
    public LazyComunidadesAutonomasReader(
            EnergiaApiClient apiClient,
            @Qualifier("comunidadesAutonomasValidator") XmlSchemaValidator validator) {
        this.apiClient = apiClient;
        this.validator = validator;
    }
    
    @Override
    public ComunidadAutonoma read() throws Exception {
        // Inicializar solo cuando se llama por primera vez (cuando se ejecuta el job)
        if (!initialized) {
            initialize();
            initialized = true;
        }
        
        return delegate != null ? delegate.read() : null;
    }
    
    private void initialize() {
        try {
            log.info("=== INICIANDO READER COMUNIDADES AUTÓNOMAS (LAZY) ===");
            String xml = apiClient.getComunidadesAutonomas();
            log.info("XML recibido (primeros 500 caracteres): {}", 
                xml != null && xml.length() > 500 ? xml.substring(0, 500) : xml);
            
            // Validar y parsear XML
            ArrayOfComunidadAutonoma arrayOf = validator.validateAndUnmarshal(xml, ArrayOfComunidadAutonoma.class);
            List<ComunidadAutonoma> comunidades = JaxbToDomainMapper.toDomainList(arrayOf.getComunidadAutonoma());
            
            log.info("=== READER COMUNIDADES AUTÓNOMAS ===");
            log.info("Total comunidades parseadas: {}", comunidades.size());
            for (ComunidadAutonoma ccaa : comunidades) {
                log.info("  - ID: {}, Nombre: {}", ccaa.idCcaa(), ccaa.nombreCcaa());
            }
            log.info("=== FIN READER COMUNIDADES AUTÓNOMAS ===");
            
            if (comunidades.isEmpty()) {
                log.warn("⚠️ No se parsearon comunidades autónomas!");
            }
            
            delegate = new ListItemReader<>(comunidades);
        } catch (JAXBException e) {
            log.error("Error parseando XML de comunidades autónomas", e);
            delegate = new ListItemReader<>(List.of());
        } catch (Exception e) {
            log.error("Error obteniendo comunidades autónomas", e);
            delegate = new ListItemReader<>(List.of());
        }
    }
}

