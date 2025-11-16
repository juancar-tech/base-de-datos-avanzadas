package com.estaciones.infrastructure.xml.model;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfEESSPrecio", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes", propOrder = {
    "eesprecio"
})
public class ArrayOfEESSPrecio {
    
    @XmlElement(name = "EESSPrecio", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private List<EESSPrecio> eesprecio;
    
    public List<EESSPrecio> getEesprecio() {
        if (eesprecio == null) {
            eesprecio = new ArrayList<>();
        }
        return this.eesprecio;
    }
}


