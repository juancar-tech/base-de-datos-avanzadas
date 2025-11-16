package com.estaciones.infrastructure.xml.model;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Municipio", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes", propOrder = {
    "idmunicipio",
    "idprovincia",
    "idccaa",
    "municipio",
    "provincia",
    "ccaa"
})
@XmlRootElement(name = "Municipio", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
public class MunicipioJaxb {
    
    @XmlElement(name = "IDMunicipio", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String idmunicipio;
    
    @XmlElement(name = "IDProvincia", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String idprovincia;
    
    @XmlElement(name = "IDCCAA", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String idccaa;
    
    @XmlElement(name = "Municipio", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String municipio;
    
    @XmlElement(name = "Provincia", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String provincia;
    
    @XmlElement(name = "CCAA", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String ccaa;
    
    public String getIdmunicipio() {
        return idmunicipio;
    }
    
    public void setIdmunicipio(String idmunicipio) {
        this.idmunicipio = idmunicipio;
    }
    
    public String getIdprovincia() {
        return idprovincia;
    }
    
    public void setIdprovincia(String idprovincia) {
        this.idprovincia = idprovincia;
    }
    
    public String getIdccaa() {
        return idccaa;
    }
    
    public void setIdccaa(String idccaa) {
        this.idccaa = idccaa;
    }
    
    public String getMunicipio() {
        return municipio;
    }
    
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }
    
    public String getProvincia() {
        return provincia;
    }
    
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
    
    public String getCcaa() {
        return ccaa;
    }
    
    public void setCcaa(String ccaa) {
        this.ccaa = ccaa;
    }
}


