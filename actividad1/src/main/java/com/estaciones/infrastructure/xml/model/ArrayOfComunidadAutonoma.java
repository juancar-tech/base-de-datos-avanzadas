package com.estaciones.infrastructure.xml.model;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfComunidadAutonoma", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes", propOrder = {
    "comunidadAutonoma"
})
@XmlRootElement(name = "ArrayOfComunidadAutonoma", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
public class ArrayOfComunidadAutonoma {
    
    @XmlElement(name = "ComunidadAutonoma", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private List<ComunidadAutonomaJaxb> comunidadAutonoma;
    
    public List<ComunidadAutonomaJaxb> getComunidadAutonoma() {
        if (comunidadAutonoma == null) {
            comunidadAutonoma = new ArrayList<>();
        }
        return this.comunidadAutonoma;
    }
}


