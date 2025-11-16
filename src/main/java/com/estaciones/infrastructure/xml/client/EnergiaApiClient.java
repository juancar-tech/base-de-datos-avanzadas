package com.estaciones.infrastructure.xml.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EnergiaApiClient {
    
    private final RestTemplate restTemplate;
    private final String baseUrl;
    
    public EnergiaApiClient(
            RestTemplate restTemplate,
            @Value("${api.energia.base-url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }
    
    private String getXmlResponse(String endpoint) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(java.util.Collections.singletonList(MediaType.APPLICATION_XML));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        ResponseEntity<String> response = restTemplate.exchange(
            baseUrl + endpoint,
            HttpMethod.GET,
            entity,
            String.class
        );
        
        return response.getBody();
    }
    
    public String getComunidadesAutonomas() {
        return getXmlResponse("/Listados/ComunidadesAutonomas/");
    }
    
    public String getProvincias() {
        return getXmlResponse("/Listados/Provincias/");
    }
    
    public String getMunicipios() {
        return getXmlResponse("/Listados/Municipios/");
    }
    
    public String getProductosPetroliferos() {
        return getXmlResponse("/Listados/ProductosPetroliferos/");
    }
    
    public String getEstacionesTerrestres() {
        return getXmlResponse("/EstacionesTerrestres/");
    }
    
    public String getPostesMaritimos() {
        return getXmlResponse("/PostesMaritimos/");
    }
}


