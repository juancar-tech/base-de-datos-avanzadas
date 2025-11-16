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
 * Reader lazy para estaciones marítimas.
 * Solo carga los datos cuando se ejecuta el job, no al iniciar la aplicación.
 */
public class LazyEstacionesMaritimasReader implements ItemReader<EstacionServicioMaritima> {
    
    private static final Logger log = LoggerFactory.getLogger(LazyEstacionesMaritimasReader.class);
    
    private final EnergiaApiClient apiClient;
    private final XmlSchemaValidator validator;
    private final EmpresaRepository empresaRepository;
    private final MunicipioRepository municipioRepository;
    private final ProvinciaRepository provinciaRepository;
    private final ComunidadAutonomaRepository ccaaRepository;
    
    private ItemReader<EstacionServicioMaritima> delegate;
    private boolean initialized = false;
    
    public LazyEstacionesMaritimasReader(
            EnergiaApiClient apiClient,
            @Qualifier("maritimosValidator") XmlSchemaValidator validator,
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
    public EstacionServicioMaritima read() throws Exception {
        // Inicializar solo cuando se llama por primera vez (cuando se ejecuta el job)
        if (!initialized) {
            initialize();
            initialized = true;
        }
        
        return delegate != null ? delegate.read() : null;
    }
    
    private void initialize() {
        try {
            log.info("=== INICIANDO READER ESTACIONES MARÍTIMAS (LAZY) ===");
            String xml = apiClient.getPostesMaritimos();
            log.info("XML recibido (primeros 500 caracteres): {}", 
                xml != null && xml.length() > 500 ? xml.substring(0, 500) : xml);
            
            // Validar y parsear XML
            PreciosPostesMaritimos preciosPostes = validator.validateAndUnmarshal(xml, PreciosPostesMaritimos.class);
            ArrayOfPosteMaritimoPrecio arrayOf = preciosPostes.getListaEESSPrecio();
            
            if (arrayOf == null || arrayOf.getPosteMaritimoPrecio() == null) {
                log.warn("No se encontraron estaciones marítimas en el XML");
                delegate = new ListItemReader<>(List.of());
                return;
            }
            
            List<PosteMaritimoPrecio> postePrecios = arrayOf.getPosteMaritimoPrecio();
            List<EstacionServicioMaritima> estaciones = new ArrayList<>();
            
            log.info("Total estaciones marítimas en XML: {}", postePrecios.size());
            
            for (PosteMaritimoPrecio postePrecio : postePrecios) {
                try {
                    // Buscar entidades relacionadas
                    Empresa empresa = null;
                    if (postePrecio.getRotulo() != null && !postePrecio.getRotulo().isEmpty()) {
                        empresa = empresaRepository.findByNombreRotulo(postePrecio.getRotulo())
                            .orElse(null);
                        // Si no existe, crear una nueva empresa
                        if (empresa == null) {
                            empresa = new Empresa(null, postePrecio.getRotulo(), null);
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
                    if (postePrecio.getIdmunicipio() != null) {
                        municipio = municipioRepository.findById(postePrecio.getIdmunicipio())
                            .orElse(null);
                    }
                    
                    Provincia provincia = null;
                    if (postePrecio.getIdprovincia() != null) {
                        provincia = provinciaRepository.findById(postePrecio.getIdprovincia())
                            .orElse(null);
                    }
                    
                    ComunidadAutonoma ccaa = null;
                    if (postePrecio.getIdccaa() != null) {
                        ccaa = ccaaRepository.findById(postePrecio.getIdccaa())
                            .orElse(null);
                    }
                    
                    EstacionServicioMaritima estacion = JaxbToDomainMapper.toDomain(
                        postePrecio, empresa, municipio, provincia, ccaa);
                    
                    if (estacion != null) {
                        estaciones.add(estacion);
                    }
                } catch (Exception e) {
                    log.error("Error procesando estación marítima: {}", postePrecio.getIdpostemaritimo(), e);
                }
            }
            
            log.info("=== READER ESTACIONES MARÍTIMAS ===");
            log.info("Total estaciones parseadas: {}", estaciones.size());
            log.info("=== FIN READER ESTACIONES MARÍTIMAS ===");
            
            delegate = new ListItemReader<>(estaciones);
        } catch (JAXBException e) {
            log.error("Error parseando XML de estaciones marítimas", e);
            delegate = new ListItemReader<>(List.of());
        } catch (Exception e) {
            log.error("Error obteniendo estaciones marítimas", e);
            delegate = new ListItemReader<>(List.of());
        }
    }
}

