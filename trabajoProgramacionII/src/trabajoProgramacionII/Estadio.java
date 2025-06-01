package trabajoProgramacionII;

import java.util.ArrayList;

public class Estadio extends Sede {

    private double precioCampo;

    public Estadio(String codigo, String nombre, String direccion, int capacidad, double precioBase) {
        super(codigo, nombre, direccion, capacidad, precioBase);
    }
    
    
    @Override
    public double calcularPrecioEntrada(Sector sector, double precioBase) {
        return precioCampo;
    }

    @Override
    public ArrayList<Sector> obtenerSectores() {
        return new ArrayList<>();
    }

    @Override
    public String obtenerTipo() {
        return "Estadio";
    }
    
    @Override
    public String toString() {						//TO STRING DE ESTADIO. AGREGAR AL TAD
        return "Estadio{" +
               "nombre='" + nombre + '\'' +
               ", direccion='" + direccion + '\'' +
               ", capacidad=" + capacidad +
               ", precioCampo=" + precioBase +
               '}';
    }
} 