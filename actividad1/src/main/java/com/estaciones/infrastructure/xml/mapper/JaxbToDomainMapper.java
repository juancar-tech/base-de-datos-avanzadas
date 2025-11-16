package com.estaciones.infrastructure.xml.mapper;

import com.estaciones.domain.model.*;
import com.estaciones.infrastructure.xml.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JaxbToDomainMapper {
    
    // Mapa: nombre XML normalizado (sin _x0020_) → ID producto en BD
    private static final Map<String, String> MAP_TAG_A_IDPRODUCTO = Map.ofEntries(
        Map.entry("Precio_Adblue", "26"),
        Map.entry("Precio_Amoniaco", "30"),
        Map.entry("Precio_Biodiesel", "8"),
        Map.entry("Precio_Bioetanol", "16"),
        Map.entry("Precio_Biogas_Natural_Comprimido", "31"),
        Map.entry("Precio_Biogas_Natural_Licuado", "32"),
        Map.entry("Precio_Diésel_Renovable", "27"),
        Map.entry("Precio_Gas_Natural_Comprimido", "18"),
        Map.entry("Precio_Gas_Natural_Licuado", "19"),
        Map.entry("Precio_Gases_licuados_del_petróleo", "17"),
        Map.entry("Precio_Gasoleo_A", "4"),
        Map.entry("Precio_Gasoleo_B", "6"),
        Map.entry("Precio_Gasoleo_Premium", "5"),
        Map.entry("Precio_Gasolina_95_E10", "23"),
        Map.entry("Precio_Gasolina_95_E25", "24"),
        Map.entry("Precio_Gasolina_95_E5", "1"),
        Map.entry("Precio_Gasolina_95_E5_Premium", "20"),
        Map.entry("Precio_Gasolina_95_E85", "25"),
        Map.entry("Precio_Gasolina_98_E10", "21"),
        Map.entry("Precio_Gasolina_98_E5", "3"),
        Map.entry("Precio_Gasolina_Renovable", "28"),
        Map.entry("Precio_Hidrogeno", "22"),
        Map.entry("Precio_Metanol", "29"),
        // Para estaciones marítimas
        Map.entry("Precio_Gasoleo_A_habitual", "4")
    );
    
    /**
     * Normaliza el nombre del campo XML eliminando _x0020_ (espacios codificados)
     * Ejemplo: "Precio_x0020_Gasoleo_x0020_A" → "Precio_Gasoleo_A"
     */
    private static String normalizarNombreCampo(String nombreXml) {
        if (nombreXml == null) {
            return null;
        }
        return nombreXml.replace("_x0020_", "_");
    }
    
    /**
     * Obtiene el ID del producto desde el nombre del campo XML
     */
    private static String obtenerIdProducto(String nombreCampoXml) {
        String nombreNormalizado = normalizarNombreCampo(nombreCampoXml);
        return MAP_TAG_A_IDPRODUCTO.get(nombreNormalizado);
    }
    
    /**
     * Crea un ProductoPetrolifero con el ID correcto basado en el nombre del campo XML
     * Nota: El nombre y abreviatura son temporales, se deberían buscar desde la BD
     */
    private static ProductoPetrolifero crearProductoDesdeCampoXml(String nombreCampoXml) {
        String idProducto = obtenerIdProducto(nombreCampoXml);
        if (idProducto == null) {
            return null;
        }
        // Crear producto con ID correcto, nombre y abreviatura temporales
        // Estos se deberían buscar desde la BD, pero por ahora usamos valores por defecto
        return new ProductoPetrolifero(idProducto, null, null);
    }
    
    public static ComunidadAutonoma toDomain(ComunidadAutonomaJaxb jaxb) {
        if (jaxb == null) {
            return null;
        }
        return new ComunidadAutonoma(
            jaxb.getIdccaa(),
            jaxb.getCcaa()
        );
    }
    
    public static Provincia toDomain(ProvinciaJaxb jaxb) {
        if (jaxb == null) {
            return null;
        }
        return new Provincia(
            jaxb.getIdpovincia(),
            jaxb.getIdccaa(),
            jaxb.getProvincia()
        );
    }
    
    public static Municipio toDomain(MunicipioJaxb jaxb) {
        if (jaxb == null) {
            return null;
        }
        return new Municipio(
            jaxb.getIdmunicipio(),
            jaxb.getIdprovincia(),
            jaxb.getIdccaa(),
            jaxb.getMunicipio()
        );
    }
    
    public static ProductoPetrolifero toDomain(ProductosPetroliferosJaxb jaxb) {
        if (jaxb == null) {
            return null;
        }
        return new ProductoPetrolifero(
            jaxb.getIdproducto(),
            jaxb.getNombreproducto(),
            jaxb.getNombreproductoabreviatura()
        );
    }
    
    public static EstacionServicioTerrestre toDomain(EESSPrecio jaxb, Empresa empresa, 
                                                      Municipio municipio, Provincia provincia,
                                                      ComunidadAutonoma ccaa) {
        if (jaxb == null) {
            return null;
        }
        
        // Convertir precios
        List<PrecioTerrestre> precios = new ArrayList<>();
        
        // Precio Gasóleo A
        if (jaxb.getPrecioGasoleoA() != null && !jaxb.getPrecioGasoleoA().isEmpty()) {
            try {
                BigDecimal precio = new BigDecimal(jaxb.getPrecioGasoleoA().replace(",", "."));
                ProductoPetrolifero producto = crearProductoDesdeCampoXml("Precio_x0020_Gasoleo_x0020_A");
                if (producto != null) {
                    precios.add(new PrecioTerrestre(null, jaxb.getIdeess(), producto, precio, LocalDateTime.now()));
                }
            } catch (NumberFormatException e) {
                // Ignorar precios inválidos
            }
        }
        
        // Precio Gasolina 95 E5
        if (jaxb.getPrecioGasolina95E5() != null && !jaxb.getPrecioGasolina95E5().isEmpty()) {
            try {
                BigDecimal precio = new BigDecimal(jaxb.getPrecioGasolina95E5().replace(",", "."));
                ProductoPetrolifero producto = crearProductoDesdeCampoXml("Precio_x0020_Gasolina_x0020_95_x0020_E5");
                if (producto != null) {
                    precios.add(new PrecioTerrestre(null, jaxb.getIdeess(), producto, precio, LocalDateTime.now()));
                }
            } catch (NumberFormatException e) {
                // Ignorar precios inválidos
            }
        }
        
        // Precio Gasolina 98 E5
        if (jaxb.getPrecioGasolina98E5() != null && !jaxb.getPrecioGasolina98E5().isEmpty()) {
            try {
                BigDecimal precio = new BigDecimal(jaxb.getPrecioGasolina98E5().replace(",", "."));
                ProductoPetrolifero producto = crearProductoDesdeCampoXml("Precio_x0020_Gasolina_x0020_98_x0020_E5");
                if (producto != null) {
                precios.add(new PrecioTerrestre(null, jaxb.getIdeess(), producto, precio, LocalDateTime.now()));
                }
            } catch (NumberFormatException e) {
                // Ignorar precios inválidos
            }
        }
        
        LocalDateTime fechaActualizacion = null;
        try {
            if (jaxb.getIdeess() != null) {
                fechaActualizacion = LocalDateTime.now();
            }
        } catch (Exception e) {
            // Usar fecha actual si no se puede parsear
            fechaActualizacion = LocalDateTime.now();
        }
        
        return new EstacionServicioTerrestre(
            jaxb.getIdeess(),
            empresa,
            municipio,
            provincia,
            ccaa,
            jaxb.getCp(),
            jaxb.getDireccion(),
            jaxb.getLocalidad(),
            jaxb.getMargen(),
            parseBigDecimal(jaxb.getLatitud()),
            parseBigDecimal(jaxb.getLongitud()),
            jaxb.getHorario(),
            jaxb.getTipoVenta(),
            jaxb.getRemision(),
            fechaActualizacion,
            precios
        );
    }
    
    public static EstacionServicioMaritima toDomain(PosteMaritimoPrecio jaxb, Empresa empresa, 
                                                 Municipio municipio, Provincia provincia,
                                                 ComunidadAutonoma ccaa) {
        if (jaxb == null) {
            return null;
        }
        
        // Convertir precios
        List<PrecioMaritimo> precios = new ArrayList<>();
        
        // Precio Gasóleo A habitual
        if (jaxb.getPrecioGasoleoAHabitual() != null && !jaxb.getPrecioGasoleoAHabitual().isEmpty()) {
            try {
                BigDecimal precio = new BigDecimal(jaxb.getPrecioGasoleoAHabitual().replace(",", "."));
                ProductoPetrolifero producto = crearProductoDesdeCampoXml("Precio_x0020_Gasoleo_x0020_A_x0020_habitual");
                if (producto != null) {
                precios.add(new PrecioMaritimo(null, jaxb.getIdpostemaritimo(), producto, precio, LocalDateTime.now()));
                }
            } catch (NumberFormatException e) {
                // Ignorar precios inválidos
            }
        }
        
        // Precio Gasolina 95 E5
        if (jaxb.getPrecioGasolina95E5() != null && !jaxb.getPrecioGasolina95E5().isEmpty()) {
            try {
                BigDecimal precio = new BigDecimal(jaxb.getPrecioGasolina95E5().replace(",", "."));
                ProductoPetrolifero producto = crearProductoDesdeCampoXml("Precio_x0020_Gasolina_x0020_95_x0020_E5");
                if (producto != null) {
                precios.add(new PrecioMaritimo(null, jaxb.getIdpostemaritimo(), producto, precio, LocalDateTime.now()));
                }
            } catch (NumberFormatException e) {
                // Ignorar precios inválidos
            }
        }
        
        LocalDateTime fechaActualizacion = LocalDateTime.now();
        
        return new EstacionServicioMaritima(
            jaxb.getIdpostemaritimo(),
            empresa,
            municipio,
            provincia,
            ccaa,
            jaxb.getCp(),
            jaxb.getDireccion(),
            jaxb.getLocalidad(),
            jaxb.getPuerto(),
            parseBigDecimal(jaxb.getLatitud()),
            parseBigDecimal(jaxb.getLongitud()),
            jaxb.getHorario(),
            jaxb.getTipoVenta(),
            jaxb.getRemision(),
            fechaActualizacion,
            precios
        );
    }
    
    private static BigDecimal parseBigDecimal(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return new BigDecimal(value.replace(",", "."));
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    public static List<ComunidadAutonoma> toDomainList(List<ComunidadAutonomaJaxb> jaxbList) {
        if (jaxbList == null) {
            return List.of();
        }
        return jaxbList.stream()
            .map(JaxbToDomainMapper::toDomain)
            .collect(Collectors.toList());
    }
    
    public static List<Provincia> toDomainListProvincias(List<ProvinciaJaxb> jaxbList) {
        if (jaxbList == null) {
            return List.of();
        }
        return jaxbList.stream()
            .map(JaxbToDomainMapper::toDomain)
            .collect(Collectors.toList());
    }
    
    public static List<Municipio> toDomainListMunicipios(List<MunicipioJaxb> jaxbList) {
        if (jaxbList == null) {
            return List.of();
        }
        return jaxbList.stream()
            .map(JaxbToDomainMapper::toDomain)
            .collect(Collectors.toList());
    }
    
    public static List<ProductoPetrolifero> toDomainListProductos(List<ProductosPetroliferosJaxb> jaxbList) {
        if (jaxbList == null) {
            return List.of();
        }
        return jaxbList.stream()
            .map(JaxbToDomainMapper::toDomain)
            .collect(Collectors.toList());
    }
}

