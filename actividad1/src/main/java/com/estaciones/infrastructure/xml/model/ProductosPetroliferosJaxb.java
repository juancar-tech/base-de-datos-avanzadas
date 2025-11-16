package com.estaciones.infrastructure.xml.model;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProductosPetroliferos", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes", propOrder = {
    "idproducto",
    "nombreproducto",
    "nombreproductoabreviatura"
})
@XmlRootElement(name = "ProductosPetroliferos", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
public class ProductosPetroliferosJaxb {
    
    @XmlElement(name = "IDProducto", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String idproducto;
    
    @XmlElement(name = "NombreProducto", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String nombreproducto;
    
    @XmlElement(name = "NombreProductoAbreviatura", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String nombreproductoabreviatura;
    
    public String getIdproducto() {
        return idproducto;
    }
    
    public void setIdproducto(String idproducto) {
        this.idproducto = idproducto;
    }
    
    public String getNombreproducto() {
        return nombreproducto;
    }
    
    public void setNombreproducto(String nombreproducto) {
        this.nombreproducto = nombreproducto;
    }
    
    public String getNombreproductoabreviatura() {
        return nombreproductoabreviatura;
    }
    
    public void setNombreproductoabreviatura(String nombreproductoabreviatura) {
        this.nombreproductoabreviatura = nombreproductoabreviatura;
    }
}


