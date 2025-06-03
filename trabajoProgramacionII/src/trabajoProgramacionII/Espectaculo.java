package trabajoProgramacionII;

import java.time.LocalDate;
import java.util.*;

public class Espectaculo {
    private String codigo;
    private String nombre;
    private Map<String, Funcion> funciones;

    public Espectaculo(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.funciones = new HashMap<>();
    }

    public void agregarFuncion(Funcion funcion) {
        String clave = funcion.getSede().getNombre() + "-" + funcion.getFecha().toString();
        funciones.put(clave, funcion);
    }

    public Funcion obtenerFuncion(LocalDate fecha) {
        // Buscar por fecha en todas las funciones
        for (Funcion funcion : funciones.values()) {
            if (funcion.getFecha().equals(fecha)) {
                return funcion;
            }
        }
        throw new RuntimeException("No existe función en esa fecha.");
    }

    public ArrayList<Funcion> listarFunciones() {
        return new ArrayList<>(funciones.values());
    }

    // Getters
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
}
