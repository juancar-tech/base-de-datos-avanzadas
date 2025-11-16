package com.estaciones.infrastructure.xml.validator;

import com.estaciones.infrastructure.xml.exception.XmlValidationException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.IOException;
import java.io.StringReader;

public class XmlSchemaValidator {
    
    private final Schema schema;
    
    public XmlSchemaValidator(String xsdPath, ResourceLoader resourceLoader) throws SAXException, IOException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Resource xsdResource = resourceLoader.getResource("classpath:schemas/" + xsdPath);
        this.schema = schemaFactory.newSchema(xsdResource.getURL());
    }
    
    /**
     * Limpia el XML eliminando cualquier contenido antes de la declaración XML
     */
    private String cleanXml(String xml) {
        if (xml == null) {
            return null;
        }
        
        // Eliminar BOM (Byte Order Mark) si existe
        xml = xml.replace("\uFEFF", "");
        
        // Buscar la posición de la declaración XML
        int xmlStart = xml.indexOf("<?xml");
        if (xmlStart > 0) {
            // Eliminar todo lo que esté antes de la declaración XML
            xml = xml.substring(xmlStart);
        } else if (xmlStart == -1) {
            // Si no hay declaración XML, buscar el primer '<'
            int firstTag = xml.indexOf('<');
            if (firstTag > 0) {
                xml = xml.substring(firstTag);
            }
        }
        
        // Eliminar espacios en blanco al inicio y final
        return xml.trim();
    }
    
    @SuppressWarnings("unchecked")
    public <T> T validateAndUnmarshal(String xml, Class<T> clazz) throws JAXBException {
        try {
            // Limpiar el XML antes de validarlo
            String cleanedXml = cleanXml(xml);
            
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            
            // Configurar validación contra XSD
            unmarshaller.setSchema(schema);
            
            // Unmarshal con validación
            T result = (T) unmarshaller.unmarshal(new StringReader(cleanedXml));
            
            return result;
        } catch (JAXBException e) {
            // Verificar si la causa es una SAXException (error de validación)
            Throwable cause = e.getCause();
            if (cause instanceof SAXException) {
                throw new XmlValidationException("Error de validación XML: " + cause.getMessage(), cause);
            }
            throw new XmlValidationException("Error al parsear XML: " + e.getMessage(), e);
        }
    }
}
