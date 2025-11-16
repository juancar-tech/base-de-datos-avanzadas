package com.estaciones.infrastructure.batch.reader;

import com.estaciones.domain.model.*;
import com.estaciones.domain.repository.*;
import com.estaciones.infrastructure.xml.client.EnergiaApiClient;
import com.estaciones.infrastructure.xml.mapper.JaxbToDomainMapper;
import com.estaciones.infrastructure.xml.model.*;
import com.estaciones.infrastructure.xml.validator.XmlSchemaValidator;
import jakarta.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Reader lazy para estaciones terrestres.
 * Solo carga los datos cuando se ejecuta el job, no al iniciar la aplicación.
 */
public class LazyEstacionesTerrestresReader implements ItemReader<EstacionServicioTerrestre> {
    
    private static final Logger log = LoggerFactory.getLogger(LazyEstacionesTerrestresReader.class);
    
    private final EnergiaApiClient apiClient;
    private final XmlSchemaValidator validator;
    private final EmpresaRepository empresaRepository;
    private final MunicipioRepository municipioRepository;
    private final ProvinciaRepository provinciaRepository;
    private final ComunidadAutonomaRepository ccaaRepository;
    
    private ItemReader<EstacionServicioTerrestre> delegate;
    private boolean initialized = false;
    
    public LazyEstacionesTerrestresReader(
            EnergiaApiClient apiClient,
            @Qualifier("terrestresValidator") XmlSchemaValidator validator,
            EmpresaRepository empresaRepository,
            MunicipioRepository municipioRepository,
            ProvinciaRepository provinciaRepository,
            ComunidadAutonomaRepository ccaaRepository) {
        this.apiClient = apiClient;
        this.validator = validator;
        this.empresaRepository = empresaRepository;
        this.municipioRepository = municipioRepository;
        this.provinciaRepository = provinciaRepository;
        this.ccaaRepository = ccaaRepository;
    }
    
    @Override
    public EstacionServicioTerrestre read() throws Exception {
        // Inicializar solo cuando se llama por primera vez (cuando se ejecuta el job)
        if (!initialized) {
            initialize();
            initialized = true;
        }
        
        return delegate != null ? delegate.read() : null;
    }
    
    private void initialize() {
        try {
            log.info("=== INICIANDO READER ESTACIONES TERRESTRES (LAZY) ===");
            String xml = apiClient.getEstacionesTerrestres();
            log.info("XML recibido (primeros 500 caracteres): {}", 
                xml != null && xml.length() > 500 ? xml.substring(0, 500) : xml);
            
            // Validar y parsear XML
            PreciosEESSTerrestres preciosEESS = validator.validateAndUnmarshal(xml, PreciosEESSTerrestres.class);
            ArrayOfEESSPrecio arrayOf = preciosEESS.getListaEESSPrecio();
            
            if (arrayOf == null || arrayOf.getEesprecio() == null) {
                log.warn("⚠️ No se encontraron estaciones terrestres en el XML");
                delegate = new ListItemReader<>(List.of());
                return;
            }
            
            List<EESSPrecio> eessPrecios = arrayOf.getEesprecio();
            List<EstacionServicioTerrestre> estaciones = new ArrayList<>();
            
            log.info("Total estaciones terrestres en XML: {}", eessPrecios.size());
            
            for (EESSPrecio eessPrecio : eessPrecios) {
                try {
                    // Buscar entidades relacionadas
                    Empresa empresa = null;
                    if (eessPrecio.getRotulo() != null && !eessPrecio.getRotulo().isEmpty()) {
                        empresa = empresaRepository.findByNombreRotulo(eessPrecio.getRotulo())
                            .orElse(null);
                        // Si no existe, crear una nueva empresa
                        if (empresa == null) {
                            empresa = new Empresa(null, eessPrecio.getRotulo(), null);
                            empresa = empresaRepository.save(empresa);
                        }
                    } else {
                        // Si no hay rótulo, usar empresa "Desconocida"
                        empresa = empresaRepository.findByNombreRotulo("Desconocida")
                            .orElse(null);
                        if (empresa == null) {
                            empresa = new Empresa(null, "Desconocida", null);
                            empresa = empresaRepository.save(empresa);
                        }
                    }
                    
                    Municipio municipio = null;
                    if (eessPrecio.getIdmunicipio() != null) {
                        municipio = municipioRepository.findById(eessPrecio.getIdmunicipio())
                            .orElse(null);
                    }
                    
                    Provincia provincia = null;
                    if (eessPrecio.getIdprovincia() != null) {
                        provincia = provinciaRepository.findById(eessPrecio.getIdprovincia())
                            .orElse(null);
                    }
                    
                    ComunidadAutonoma ccaa = null;
                    if (eessPrecio.getIdccaa() != null) {
                        ccaa = ccaaRepository.findById(eessPrecio.getIdccaa())
                            .orElse(null);
                    }
                    
                    EstacionServicioTerrestre estacion = JaxbToDomainMapper.toDomain(
                        eessPrecio, empresa, municipio, provincia, ccaa);
                    
                    if (estacion != null) {
                        estaciones.add(estacion);
                    }
                } catch (Exception e) {
                    log.error("Error procesando estación terrestre: {}", eessPrecio.getIdeess(), e);
                }
            }
            
            log.info("=== READER ESTACIONES TERRESTRES ===");
            log.info("Total estaciones parseadas: {}", estaciones.size());
            log.info("=== FIN READER ESTACIONES TERRESTRES ===");
            
            delegate = new ListItemReader<>(estaciones);
        } catch (JAXBException e) {
            log.error("Error parseando XML de estaciones terrestres", e);
            delegate = new ListItemReader<>(List.of());
        } catch (Exception e) {
            log.error("Error obteniendo estaciones terrestres", e);
            delegate = new ListItemReader<>(List.of());
        }
    }
}

