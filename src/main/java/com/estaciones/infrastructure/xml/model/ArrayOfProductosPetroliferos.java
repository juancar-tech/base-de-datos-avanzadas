package com.estaciones.infrastructure.xml.model;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfProductosPetroliferos", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes", propOrder = {
    "productosPetroliferos"
})
@XmlRootElement(name = "ArrayOfProductosPetroliferos", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
public class ArrayOfProductosPetroliferos {
    
    @XmlElement(name = "ProductosPetroliferos", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private List<ProductosPetroliferosJaxb> productosPetroliferos;
    
    public List<ProductosPetroliferosJaxb> getProductosPetroliferos() {
        if (productosPetroliferos == null) {
            productosPetroliferos = new ArrayList<>();
        }
        return this.productosPetroliferos;
    }
}


