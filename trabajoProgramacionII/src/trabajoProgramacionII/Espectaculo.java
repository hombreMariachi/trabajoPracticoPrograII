package trabajoProgramacionII;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Espectaculo {


    private String codigo;
    private String nombre;
    private Map<String, Funcion> funciones; // clave: codigoSede + fecha

    public Espectaculo(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.funciones = new HashMap<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public Map<String, Funcion> getFunciones() {
        return funciones;
    }

    private String obtenerCantidadFuncionesTexto() {					//auxiliar para agregar al TAD
        if (funciones == null || funciones.isEmpty()) {
            return "Funciones: 0";
        } else {
            return "Funciones: " + funciones.size();
        }
    }

    @Override
    public String toString() {
        return "Espectaculo: " + nombre + " | Codigo: " + codigo + " | " + obtenerCantidadFuncionesTexto();
    }
    
    
    public void agregarFuncion(Funcion funcion) {
        String clave = funcion.getSede().getCodigo() + "-" + funcion.getFecha().toString();
        
        if (funciones.containsKey(clave)) {
            throw new IllegalArgumentException("La función ya existe para esa sede y fecha.");
        }
        
        funcion.setClave(clave); //  Se guarda la clave en la función
        funciones.put(clave, funcion);
    }
    
    
    
    public void mostrarFunciones() {
        if (funciones.isEmpty()) {
            System.out.println("Este espectáculo no tiene funciones registradas.");
        } else {
            System.out.println("Funciones del espectáculo '" + nombre + "':");
            for (Funcion funcion : funciones.values()) {
                System.out.println("Clave: " + funcion.getClave() +
                                   " | Fecha: " + funcion.getFecha() + 
                                   " | Sede: " + funcion.getSede().getNombre() + 
                                   " | Precio base: $" + funcion.getPrecioBase());
            }
        }
    }
    
    
    public Funcion obtenerFuncion(String codigoSede, LocalDate fecha) {
        String clave = codigoSede + "-" + fecha.toString();			//porque esa es la clave de funcion
        if (!funciones.containsKey(clave)) {
            throw new IllegalArgumentException("No existe una función en esa sede y fecha.");
        }
        return funciones.get(clave);
    }
    
    


    
    
    
    
    
    
    
    
    
    
    
    
    

    public boolean existeFuncion(String codigoSede, LocalDate fecha) {
        return funciones.containsKey(generarClaveFuncion(codigoSede, fecha));
    }

    public ArrayList<Funcion> listarFunciones() {
        return new ArrayList<>(funciones.values());
    }

    public boolean existeEspectaculoParaSede(String codigoSede, LocalDate fecha) {
        return existeFuncion(codigoSede, fecha);
    }

    public double consultarValorEntrada(String codigoSede, LocalDate fecha, String tipoSector, int numeroSector) {
        String claveFuncion = generarClaveFuncion(codigoSede, fecha);
        if (funciones.containsKey(claveFuncion)) {
            Funcion funcion = funciones.get(claveFuncion);
            Sector sector = funcion.getSede().obtenerSector(tipoSector, numeroSector);
            return funcion.getSede().calcularPrecioEntrada(sector, funcion.getPrecioBase());
        }
        return 0;
    }

    private String generarClaveFuncion(String codigoSede, LocalDate fecha) {
        return codigoSede + "-" + fecha.toString();
    }

    public Funcion getFuncion(String codigoSede, LocalDate fecha) {
        return funciones.get(generarClaveFuncion(codigoSede, fecha));
    }


} 