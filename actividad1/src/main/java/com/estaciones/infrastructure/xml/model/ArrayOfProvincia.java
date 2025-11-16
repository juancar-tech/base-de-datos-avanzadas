package com.estaciones.infrastructure.xml.model;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfProvincia", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes", propOrder = {
    "provincia"
})
@XmlRootElement(name = "ArrayOfProvincia", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
public class ArrayOfProvincia {
    
    @XmlElement(name = "Provincia", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private List<ProvinciaJaxb> provincia;
    
    public List<ProvinciaJaxb> getProvincia() {
        if (provincia == null) {
            provincia = new ArrayList<>();
        }
        return this.provincia;
    }
}


