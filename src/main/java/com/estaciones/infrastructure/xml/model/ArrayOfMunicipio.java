package com.estaciones.infrastructure.xml.model;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfMunicipio", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes", propOrder = {
    "municipio"
})
@XmlRootElement(name = "ArrayOfMunicipio", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
public class ArrayOfMunicipio {
    
    @XmlElement(name = "Municipio", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private List<MunicipioJaxb> municipio;
    
    public List<MunicipioJaxb> getMunicipio() {
        if (municipio == null) {
            municipio = new ArrayList<>();
        }
        return this.municipio;
    }
}


