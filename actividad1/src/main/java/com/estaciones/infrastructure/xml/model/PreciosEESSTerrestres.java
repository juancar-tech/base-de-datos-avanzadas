package com.estaciones.infrastructure.xml.model;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PreciosEESSTerrestres", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes", propOrder = {
    "fecha",
    "listaEESSPrecio",
    "nota",
    "resultadoConsulta"
})
@XmlRootElement(name = "PreciosEESSTerrestres", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
public class PreciosEESSTerrestres {
    
    @XmlElement(name = "Fecha", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String fecha;
    
    @XmlElement(name = "ListaEESSPrecio", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private ArrayOfEESSPrecio listaEESSPrecio;
    
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
    
    public ArrayOfEESSPrecio getListaEESSPrecio() {
        return listaEESSPrecio;
    }
    
    public void setListaEESSPrecio(ArrayOfEESSPrecio listaEESSPrecio) {
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


