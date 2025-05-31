package trabajoProgramacionII;

import java.util.ArrayList;

public class Estadio extends Sede {

    private double precioCampo;

    public Estadio(String nombre, String direccion, int capacidad, double precioCampo) {
        super(nombre, direccion, capacidad);
        this.precioCampo = precioCampo;
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
} 