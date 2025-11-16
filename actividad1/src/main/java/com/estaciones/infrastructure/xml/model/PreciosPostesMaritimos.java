package com.estaciones.infrastructure.xml.model;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PreciosPostesMaritimos", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes", propOrder = {
    "fecha",
    "listaEESSPrecio",
    "nota",
    "resultadoConsulta"
})
@XmlRootElement(name = "PreciosPostesMaritimos", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
public class PreciosPostesMaritimos {
    
    @XmlElement(name = "Fecha", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String fecha;
    
    @XmlElement(name = "ListaEESSPrecio", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private ArrayOfPosteMaritimoPrecio listaEESSPrecio;
    
    @XmlElement(name = "Nota", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String nota;
    
    @XmlElement(name = "ResultadoConsulta", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String resultadoConsulta;
    
    public String getFecha() {
        return fecha;
    }
    
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    public ArrayOfPosteMaritimoPrecio getListaEESSPrecio() {
        return listaEESSPrecio;
    }
    
    public void setListaEESSPrecio(ArrayOfPosteMaritimoPrecio listaEESSPrecio) {
        this.listaEESSPrecio = listaEESSPrecio;
    }
    
    public String getNota() {
        return nota;
    }
    
    public void setNota(String nota) {
        this.nota = nota;
    }
    
    public String getResultadoConsulta() {
        return resultadoConsulta;
    }
    
    public void setResultadoConsulta(String resultadoConsulta) {
        this.resultadoConsulta = resultadoConsulta;
    }
}


