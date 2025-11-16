package com.estaciones.infrastructure.batch.reader;

import com.estaciones.domain.model.ProductoPetrolifero;
import com.estaciones.infrastructure.xml.client.EnergiaApiClient;
import com.estaciones.infrastructure.xml.mapper.JaxbToDomainMapper;
import com.estaciones.infrastructure.xml.model.ArrayOfProductosPetroliferos;
import com.estaciones.infrastructure.xml.validator.XmlSchemaValidator;
import jakarta.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * Reader lazy para productos petrolíferos.
 * Solo carga los datos cuando se ejecuta el job, no al iniciar la aplicación.
 */
public class LazyProductosPetroliferosReader implements ItemReader<ProductoPetrolifero> {
    
    private static final Logger log = LoggerFactory.getLogger(LazyProductosPetroliferosReader.class);
    
    private final EnergiaApiClient apiClient;
    private final XmlSchemaValidator validator;
    
    private ItemReader<ProductoPetrolifero> delegate;
    private boolean initialized = false;
    
    public LazyProductosPetroliferosReader(
            EnergiaApiClient apiClient,
            @Qualifier("productosPetroliferosValidator") XmlSchemaValidator validator) {
        this.apiClient = apiClient;
        this.validator = validator;
    }
    
    @Override
    public ProductoPetrolifero read() throws Exception {
        // Inicializar solo cuando se llama por primera vez (cuando se ejecuta el job)
        if (!initialized) {
            initialize();
            initialized = true;
        }
        
        return delegate != null ? delegate.read() : null;
    }
    
    private void initialize() {
        try {
            log.info("=== INICIANDO READER PRODUCTOS PETROLÍFEROS (LAZY) ===");
            String xml = apiClient.getProductosPetroliferos();
            log.info("XML recibido (primeros 500 caracteres): {}", 
                xml != null && xml.length() > 500 ? xml.substring(0, 500) : xml);
            
            // Validar y parsear XML
            ArrayOfProductosPetroliferos arrayOf = validator.validateAndUnmarshal(xml, ArrayOfProductosPetroliferos.class);
            List<ProductoPetrolifero> productos = JaxbToDomainMapper.toDomainListProductos(arrayOf.getProductosPetroliferos());
            
            log.info("=== READER PRODUCTOS PETROLÍFEROS ===");
            log.info("Total productos parseados: {}", productos.size());
            for (ProductoPetrolifero producto : productos) {
                log.info("  - ID: {}, Nombre: {}, Abreviatura: {}", 
                    producto.idProducto(), producto.nombreProducto(), producto.nombreAbreviatura());
            }
            log.info("=== FIN READER PRODUCTOS PETROLÍFEROS ===");
            
            if (productos.isEmpty()) {
                log.warn("⚠️ No se parsearon productos petrolíferos!");
            }
            
            delegate = new ListItemReader<>(productos);
        } catch (JAXBException e) {
            log.error("Error parseando XML de productos petrolíferos", e);
            delegate = new ListItemReader<>(List.of());
        } catch (Exception e) {
            log.error("Error obteniendo productos petrolíferos", e);
            delegate = new ListItemReader<>(List.of());
        }
    }
}

