package com.estaciones.infrastructure.xml.model;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ComunidadAutonoma", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes", propOrder = {
    "idccaa",
    "ccaa"
})
@XmlRootElement(name = "ComunidadAutonoma", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
public class ComunidadAutonomaJaxb {
    
    @XmlElement(name = "IDCCAA", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String idccaa;
    
    @XmlElement(name = "CCAA", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String ccaa;
    
    public String getIdccaa() {
        return idccaa;
    }
    
    public void setIdccaa(String idccaa) {
        this.idccaa = idccaa;
    }
    
    public String getCcaa() {
        return ccaa;
    }
    
    public void setCcaa(String ccaa) {
        this.ccaa = ccaa;
    }
}


