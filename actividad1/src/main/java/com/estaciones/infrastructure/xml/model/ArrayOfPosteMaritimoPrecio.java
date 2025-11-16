package com.estaciones.infrastructure.xml.model;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfPosteMaritimoPrecio", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes", propOrder = {
    "posteMaritimoPrecio"
})
public class ArrayOfPosteMaritimoPrecio {
    
    @XmlElement(name = "PosteMaritimoPrecio", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private List<PosteMaritimoPrecio> posteMaritimoPrecio;
    
    public List<PosteMaritimoPrecio> getPosteMaritimoPrecio() {
        if (posteMaritimoPrecio == null) {
            posteMaritimoPrecio = new ArrayList<>();
        }
        return this.posteMaritimoPrecio;
    }
}


