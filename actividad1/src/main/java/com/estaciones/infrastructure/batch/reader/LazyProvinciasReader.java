package com.estaciones.infrastructure.batch.reader;

import com.estaciones.domain.model.Provincia;
import com.estaciones.infrastructure.xml.client.EnergiaApiClient;
import com.estaciones.infrastructure.xml.mapper.JaxbToDomainMapper;
import com.estaciones.infrastructure.xml.model.ArrayOfProvincia;
import com.estaciones.infrastructure.xml.validator.XmlSchemaValidator;
import jakarta.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * Reader lazy para provincias.
 * Solo carga los datos cuando se ejecuta el job, no al iniciar la aplicación.
 */
public class LazyProvinciasReader implements ItemReader<Provincia> {
    
    private static final Logger log = LoggerFactory.getLogger(LazyProvinciasReader.class);
    
    private final EnergiaApiClient apiClient;
    private final XmlSchemaValidator validator;
    
    private ItemReader<Provincia> delegate;
    private boolean initialized = false;
    
    public LazyProvinciasReader(
            EnergiaApiClient apiClient,
            @Qualifier("provinciasValidator") XmlSchemaValidator validator) {
        this.apiClient = apiClient;
        this.validator = validator;
    }
    
    @Override
    public Provincia read() throws Exception {
        // Inicializar solo cuando se llama por primera vez (cuando se ejecuta el job)
        if (!initialized) {
            initialize();
            initialized = true;
        }
        
        return delegate != null ? delegate.read() : null;
    }
    
    private void initialize() {
        try {
            log.info("=== INICIANDO READER PROVINCIAS (LAZY) ===");
            String xml = apiClient.getProvincias();
            log.info("XML recibido (primeros 500 caracteres): {}", 
                xml != null && xml.length() > 500 ? xml.substring(0, 500) : xml);
            
            // Validar y parsear XML
            ArrayOfProvincia arrayOf = validator.validateAndUnmarshal(xml, ArrayOfProvincia.class);
            List<Provincia> provincias = JaxbToDomainMapper.toDomainListProvincias(arrayOf.getProvincia());
            
            log.info("=== READER PROVINCIAS ===");
            log.info("Total provincias parseadas: {}", provincias.size());
            for (Provincia provincia : provincias) {
                log.info("  - ID: {}, Nombre: {}, CCAA: {}", 
                    provincia.idProvincia(), provincia.nombreProvincia(), provincia.idCcaa());
            }
            log.info("=== FIN READER PROVINCIAS ===");
            
            if (provincias.isEmpty()) {
                log.warn("⚠️ No se parsearon provincias!");
            }
            
            delegate = new ListItemReader<>(provincias);
        } catch (JAXBException e) {
            log.error("Error parseando XML de provincias", e);
            delegate = new ListItemReader<>(List.of());
        } catch (Exception e) {
            log.error("Error obteniendo provincias", e);
            delegate = new ListItemReader<>(List.of());
        }
    }
}

