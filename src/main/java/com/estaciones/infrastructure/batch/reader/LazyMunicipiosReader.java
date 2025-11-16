package com.estaciones.infrastructure.batch.reader;

import com.estaciones.domain.model.Municipio;
import com.estaciones.infrastructure.xml.client.EnergiaApiClient;
import com.estaciones.infrastructure.xml.mapper.JaxbToDomainMapper;
import com.estaciones.infrastructure.xml.model.ArrayOfMunicipio;
import com.estaciones.infrastructure.xml.validator.XmlSchemaValidator;
import jakarta.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * Reader lazy para municipios.
 * Solo carga los datos cuando se ejecuta el job, no al iniciar la aplicación.
 */
public class LazyMunicipiosReader implements ItemReader<Municipio> {
    
    private static final Logger log = LoggerFactory.getLogger(LazyMunicipiosReader.class);
    
    private final EnergiaApiClient apiClient;
    private final XmlSchemaValidator validator;
    
    private ItemReader<Municipio> delegate;
    private boolean initialized = false;
    
    public LazyMunicipiosReader(
            EnergiaApiClient apiClient,
            @Qualifier("municipiosValidator") XmlSchemaValidator validator) {
        this.apiClient = apiClient;
        this.validator = validator;
    }
    
    @Override
    public Municipio read() throws Exception {
        // Inicializar solo cuando se llama por primera vez (cuando se ejecuta el job)
        if (!initialized) {
            initialize();
            initialized = true;
        }
        
        return delegate != null ? delegate.read() : null;
    }
    
    private void initialize() {
        try {
            log.info("=== INICIANDO READER MUNICIPIOS (LAZY) ===");
            String xml = apiClient.getMunicipios();
            log.info("XML recibido (primeros 500 caracteres): {}", 
                xml != null && xml.length() > 500 ? xml.substring(0, 500) : xml);
            
            // Validar y parsear XML
            ArrayOfMunicipio arrayOf = validator.validateAndUnmarshal(xml, ArrayOfMunicipio.class);
            List<Municipio> municipios = JaxbToDomainMapper.toDomainListMunicipios(arrayOf.getMunicipio());
            
            log.info("=== READER MUNICIPIOS ===");
            log.info("Total municipios parseados: {}", municipios.size());
            if (municipios.size() <= 10) {
                for (Municipio municipio : municipios) {
                    log.info("  - ID: {}, Nombre: {}, Provincia: {}, CCAA: {}", 
                        municipio.idMunicipio(), municipio.nombreMunicipio(), 
                        municipio.idProvincia(), municipio.idCcaa());
                }
            } else {
                log.info("  (Mostrando solo los primeros 10 de {})", municipios.size());
                for (int i = 0; i < 10; i++) {
                    Municipio municipio = municipios.get(i);
                    log.info("  - ID: {}, Nombre: {}, Provincia: {}, CCAA: {}", 
                        municipio.idMunicipio(), municipio.nombreMunicipio(), 
                        municipio.idProvincia(), municipio.idCcaa());
                }
            }
            log.info("=== FIN READER MUNICIPIOS ===");
            
            if (municipios.isEmpty()) {
                log.warn("⚠️ No se parsearon municipios!");
            }
            
            delegate = new ListItemReader<>(municipios);
        } catch (JAXBException e) {
            log.error("Error parseando XML de municipios", e);
            delegate = new ListItemReader<>(List.of());
        } catch (Exception e) {
            log.error("Error obteniendo municipios", e);
            delegate = new ListItemReader<>(List.of());
        }
    }
}

