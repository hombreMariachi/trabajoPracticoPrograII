package trabajoProgramacionII;

import java.util.ArrayList;

public class MiniEstadio extends Sede {

    private int cantPuestosComida;
    private int cantPuestosMerchandising;
    private double consumicionLibre;

    public MiniEstadio(String nombre, String direccion, int capacidad, double consumicionLibre) {
        super(nombre, direccion, capacidad);
        this.consumicionLibre = consumicionLibre;
        this.cantPuestosComida = 0;
        this.cantPuestosMerchandising = 0;
    }

    @Override
    public double calcularPrecioEntrada(Sector sector, double precioBase) {
        return sector.calcularPrecio(precioBase) + consumicionLibre;
    }

    @Override
    public ArrayList<Sector> obtenerSectores() {
        return new ArrayList<>(sectores);
    }

    @Override
    public String obtenerTipo() {
        return "Miniestadio";
    }

    public void setCantPuestosComida(int cantPuestosComida) {
        this.cantPuestosComida = cantPuestosComida;
    }

    public void setCantPuestosMerchandising(int cantPuestosMerchandising) {
        this.cantPuestosMerchandising = cantPuestosMerchandising;
    }

    public int getCantPuestosComida() {
        return cantPuestosComida;
    }

    public int getCantPuestosMerchandising() {
        return cantPuestosMerchandising;
    }

    public double getConsumicionLibre() {
        return consumicionLibre;
    }
} 
