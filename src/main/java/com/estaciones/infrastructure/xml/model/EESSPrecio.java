package com.estaciones.infrastructure.xml.model;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EESSPrecio", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
public class EESSPrecio {
    
    @XmlElement(name = "C.P.", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String cp;
    
    @XmlElement(name = "Dirección", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String direccion;
    
    @XmlElement(name = "Horario", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String horario;
    
    @XmlElement(name = "Latitud", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String latitud;
    
    @XmlElement(name = "Localidad", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String localidad;
    
    @XmlElement(name = "Longitud_x0020__x0028_WGS84_x0029_", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String longitud;
    
    @XmlElement(name = "Margen", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String margen;
    
    @XmlElement(name = "Municipio", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String municipio;
    
    @XmlElement(name = "Provincia", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String provincia;
    
    @XmlElement(name = "Remisión", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String remision;
    
    @XmlElement(name = "Rótulo", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String rotulo;
    
    @XmlElement(name = "Tipo_x0020_Venta", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String tipoVenta;
    
    @XmlElement(name = "IDEESS", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String ideess;
    
    @XmlElement(name = "IDMunicipio", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String idmunicipio;
    
    @XmlElement(name = "IDProvincia", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String idprovincia;
    
    @XmlElement(name = "IDCCAA", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String idccaa;
    
    // Campos de precios - solo los más comunes
    @XmlElement(name = "Precio_x0020_Gasoleo_x0020_A", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String precioGasoleoA;
    
    @XmlElement(name = "Precio_x0020_Gasolina_x0020_95_x0020_E5", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String precioGasolina95E5;
    
    @XmlElement(name = "Precio_x0020_Gasolina_x0020_98_x0020_E5", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String precioGasolina98E5;
    
    // Getters y setters para campos principales
    public String getCp() { return cp; }
    public void setCp(String cp) { this.cp = cp; }
    
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }
    
    public String getLatitud() { return latitud; }
    public void setLatitud(String latitud) { this.latitud = latitud; }
    
    public String getLocalidad() { return localidad; }
    public void setLocalidad(String localidad) { this.localidad = localidad; }
    
    public String getLongitud() { return longitud; }
    public void setLongitud(String longitud) { this.longitud = longitud; }
    
    public String getMargen() { return margen; }
    public void setMargen(String margen) { this.margen = margen; }
    
    public String getMunicipio() { return municipio; }
    public void setMunicipio(String municipio) { this.municipio = municipio; }
    
    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }
    
    public String getRemision() { return remision; }
    public void setRemision(String remision) { this.remision = remision; }
    
    public String getRotulo() { return rotulo; }
    public void setRotulo(String rotulo) { this.rotulo = rotulo; }
    
    public String getTipoVenta() { return tipoVenta; }
    public void setTipoVenta(String tipoVenta) { this.tipoVenta = tipoVenta; }
    
    public String getIdeess() { return ideess; }
    public void setIdeess(String ideess) { this.ideess = ideess; }
    
    public String getIdmunicipio() { return idmunicipio; }
    public void setIdmunicipio(String idmunicipio) { this.idmunicipio = idmunicipio; }
    
    public String getIdprovincia() { return idprovincia; }
    public void setIdprovincia(String idprovincia) { this.idprovincia = idprovincia; }
    
    public String getIdccaa() { return idccaa; }
    public void setIdccaa(String idccaa) { this.idccaa = idccaa; }
    
    public String getPrecioGasoleoA() { return precioGasoleoA; }
    public void setPrecioGasoleoA(String precioGasoleoA) { this.precioGasoleoA = precioGasoleoA; }
    
    public String getPrecioGasolina95E5() { return precioGasolina95E5; }
    public void setPrecioGasolina95E5(String precioGasolina95E5) { this.precioGasolina95E5 = precioGasolina95E5; }
    
    public String getPrecioGasolina98E5() { return precioGasolina98E5; }
    public void setPrecioGasolina98E5(String precioGasolina98E5) { this.precioGasolina98E5 = precioGasolina98E5; }
}


