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

    public void agregarFuncion(String codigoSede, LocalDate fecha, double precioBase) {
        String claveFuncion = generarClaveFuncion(codigoSede, fecha);
        if (!funciones.containsKey(claveFuncion)) {
            Sede sede = null; // Esto deberá resolverse desde Ticketek o mediante inyección
            Funcion nuevaFuncion = new Funcion(sede, fecha, precioBase, this);
            funciones.put(claveFuncion, nuevaFuncion);
        }
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

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }
} 