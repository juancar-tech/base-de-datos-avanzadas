package com.estaciones.infrastructure.xml.config;

import com.estaciones.infrastructure.xml.validator.XmlSchemaValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.xml.sax.SAXException;

import java.io.IOException;

@Configuration
public class XmlValidatorConfig {
    
    @Bean
    public XmlSchemaValidator comunidadesAutonomasValidator(ResourceLoader resourceLoader) 
            throws SAXException, IOException {
        return new XmlSchemaValidator("comunidades-autonomas.xsd", resourceLoader);
    }
    
    @Bean
    public XmlSchemaValidator provinciasValidator(ResourceLoader resourceLoader) 
            throws SAXException, IOException {
        return new XmlSchemaValidator("provincias.xsd", resourceLoader);
    }
    
    @Bean
    public XmlSchemaValidator municipiosValidator(ResourceLoader resourceLoader) 
            throws SAXException, IOException {
        return new XmlSchemaValidator("municipios.xsd", resourceLoader);
    }
    
    @Bean
    public XmlSchemaValidator productosPetroliferosValidator(ResourceLoader resourceLoader) 
            throws SAXException, IOException {
        return new XmlSchemaValidator("productos-petroliferos.xsd", resourceLoader);
    }
    
    @Bean
    public XmlSchemaValidator terrestresValidator(ResourceLoader resourceLoader) 
            throws SAXException, IOException {
        return new XmlSchemaValidator("estaciones-terrestres.xsd", resourceLoader);
    }
    
    @Bean
    public XmlSchemaValidator maritimosValidator(ResourceLoader resourceLoader) 
            throws SAXException, IOException {
        return new XmlSchemaValidator("postes-maritimos.xsd", resourceLoader);
    }
}

