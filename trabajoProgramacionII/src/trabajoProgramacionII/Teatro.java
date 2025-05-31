package trabajoProgramacionII;

import java.util.ArrayList;

public class Teatro extends Sede {

    public Teatro(String nombre, String direccion, int capacidad) {
        super(nombre, direccion, capacidad);
    }

    @Override
    public double calcularPrecioEntrada(Sector sector, double precioBase) {
        return sector.calcularPrecio(precioBase);
    }

    public void agregarSector(Sector sector) {
        sectores.add(sector);
    }

    @Override
    public ArrayList<Sector> obtenerSectores() {
        return new ArrayList<>(sectores);
    }

    @Override
    public String obtenerTipo() {
        return "Teatro";
    }

} 

