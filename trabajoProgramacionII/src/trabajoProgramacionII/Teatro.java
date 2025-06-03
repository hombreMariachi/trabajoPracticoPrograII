package trabajoProgramacionII;

import java.util.ArrayList;

public class Teatro extends Sede {
    
    private int asientosPorFila;

    public Teatro(String codigo, String nombre, String direccion, int capacidad, double precioBase, int asientosPorFila) {
        super(codigo, nombre, direccion, capacidad, precioBase);
        this.asientosPorFila = asientosPorFila;
    }

    @Override
    public String obtenerTipo() {
        return "Teatro";
    }

    @Override
    public double calcularPrecioEntrada(Sector sector, double precioBase) {
        return sector.calcularPrecio(precioBase);
    }

    @Override
    public ArrayList<Sector> obtenerSectores() {
        return new ArrayList<>(sectores);
    }
    
    public int getAsientosPorFila() {
        return asientosPorFila;
    }
}