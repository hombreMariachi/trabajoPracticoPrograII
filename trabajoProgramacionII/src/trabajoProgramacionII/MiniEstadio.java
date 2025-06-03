package trabajoProgramacionII;

import java.util.ArrayList;

public class MiniEstadio extends Sede {
    private int cantPuestosComida;
    private double consumicionLibre;
    private int asientosPorFila;

    public MiniEstadio(String codigo, String nombre, String direccion, int capacidad, 
                       double consumicionLibre, int cantPuestosComida, int asientosPorFila) {
        super(codigo, nombre, direccion, capacidad, 0);
        this.consumicionLibre = consumicionLibre;
        this.cantPuestosComida = cantPuestosComida;
        this.asientosPorFila = asientosPorFila;
    }

    @Override
    public String obtenerTipo() { 
        return "MiniEstadio"; 
    }

    @Override
    public double calcularPrecioEntrada(Sector sector, double precioBase) {
        return sector.calcularPrecio(precioBase) + consumicionLibre;
    }

    @Override
    public ArrayList<Sector> obtenerSectores() {
        return new ArrayList<>(sectores);
    }

    public double getConsumicionLibre() { 
        return consumicionLibre; 
    }
    
    public int getAsientosPorFila() {
        return asientosPorFila;
    }
}
