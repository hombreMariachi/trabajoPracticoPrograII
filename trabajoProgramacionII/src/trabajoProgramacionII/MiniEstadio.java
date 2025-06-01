package trabajoProgramacionII;

import java.util.ArrayList;

public class MiniEstadio extends Sede {

    private int cantPuestosComida;
    private int cantPuestosMerchandising;
    private double consumicionLibre;


    public MiniEstadio(String codigo, String nombre, String direccion, int capacidad, double precioBase) {
        super(codigo, nombre, direccion, capacidad, precioBase);
    }

    @Override
    public String obtenerTipo() {
        return "MiniEstadio";
    }

    @Override
    public double calcularPrecioEntrada(Sector sector, double precioBase) {
        return sector.calcularPrecio(precioBase);
    }

    @Override
    public ArrayList<Sector> obtenerSectores() {
        return new ArrayList<>(sectores);
    }
    
    
} 
