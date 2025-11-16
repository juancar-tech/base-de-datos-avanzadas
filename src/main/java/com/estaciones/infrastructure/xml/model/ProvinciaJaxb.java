package com.estaciones.infrastructure.xml.model;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Provincia", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes", propOrder = {
    "idpovincia",
    "idccaa",
    "provincia",
    "ccaa"
})
@XmlRootElement(name = "Provincia", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
public class ProvinciaJaxb {
    
    @XmlElement(name = "IDPovincia", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String idpovincia;
    
    @XmlElement(name = "IDCCAA", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String idccaa;
    
    @XmlElement(name = "Provincia", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String provincia;
    
    @XmlElement(name = "CCAA", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String ccaa;
    
    public String getIdpovincia() {
        return idpovincia;
    }
    
    public void setIdpovincia(String idpovincia) {
        this.idpovincia = idpovincia;
    }
    
    public String getIdccaa() {
        return idccaa;
    }
    
    public void setIdccaa(String idccaa) {
        this.idccaa = idccaa;
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


