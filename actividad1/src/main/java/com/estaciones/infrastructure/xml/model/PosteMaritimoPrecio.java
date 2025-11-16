package com.estaciones.infrastructure.xml.model;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PosteMaritimoPrecio", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
public class PosteMaritimoPrecio {
    
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
    
    @XmlElement(name = "Municipio", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String municipio;
    
    @XmlElement(name = "Provincia", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String provincia;
    
    @XmlElement(name = "Puerto", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String puerto;
    
    @XmlElement(name = "Remisión", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String remision;
    
    @XmlElement(name = "Rótulo", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String rotulo;
    
    @XmlElement(name = "Tipo_x0020_Venta", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String tipoVenta;
    
    @XmlElement(name = "IDPosteMaritimo", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String idpostemaritimo;
    
    @XmlElement(name = "IDMunicipio", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String idmunicipio;
    
    @XmlElement(name = "IDProvincia", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String idprovincia;
    
    @XmlElement(name = "IDCCAA", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String idccaa;
    
    // Campos de precios
    @XmlElement(name = "Precio_x0020_Gasoleo_x0020_A_x0020_habitual", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String precioGasoleoAHabitual;
    
    @XmlElement(name = "Precio_x0020_Gasolina_x0020_95_x0020_E5", namespace = "http://schemas.datacontract.org/2004/07/ServiciosCarburantes")
    private String precioGasolina95E5;
    
    // Getters y setters
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
    
    public String getMunicipio() { return municipio; }
    public void setMunicipio(String municipio) { this.municipio = municipio; }
    
    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }
    
    public String getPuerto() { return puerto; }
    public void setPuerto(String puerto) { this.puerto = puerto; }
    
    public String getRemision() { return remision; }
    public void setRemision(String remision) { this.remision = remision; }
    
    public String getRotulo() { return rotulo; }
    public void setRotulo(String rotulo) { this.rotulo = rotulo; }
    
    public String getTipoVenta() { return tipoVenta; }
    public void setTipoVenta(String tipoVenta) { this.tipoVenta = tipoVenta; }
    
    public String getIdpostemaritimo() { return idpostemaritimo; }
    public void setIdpostemaritimo(String idpostemaritimo) { this.idpostemaritimo = idpostemaritimo; }
    
    public String getIdmunicipio() { return idmunicipio; }
    public void setIdmunicipio(String idmunicipio) { this.idmunicipio = idmunicipio; }
    
    public String getIdprovincia() { return idprovincia; }
    public void setIdprovincia(String idprovincia) { this.idprovincia = idprovincia; }
    
    public String getIdccaa() { return idccaa; }
    public void setIdccaa(String idccaa) { this.idccaa = idccaa; }
    
    public String getPrecioGasoleoAHabitual() { return precioGasoleoAHabitual; }
    public void setPrecioGasoleoAHabitual(String precioGasoleoAHabitual) { this.precioGasoleoAHabitual = precioGasoleoAHabitual; }
    
    public String getPrecioGasolina95E5() { return precioGasolina95E5; }
    public void setPrecioGasolina95E5(String precioGasolina95E5) { this.precioGasolina95E5 = precioGasolina95E5; }
}


