package trabajoProgramacionII;


import java.util.ArrayList;

public class Estadio extends Sede {

    public Estadio(String codigo, String nombre, String direccion, int capacidad, double precioBase) {
        super(codigo, nombre, direccion, capacidad, precioBase);
    }

    @Override
    public double calcularPrecioEntrada(Sector sector, double precioBase) {
        return precioBase;
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
    public String toString() {
        return "Estadio{" +
               "nombre='" + nombre + '\'' +
               ", direccion='" + direccion + '\'' +
               ", capacidad=" + capacidad +
               ", precioBase=" + precioBase +
               '}';
    }
}