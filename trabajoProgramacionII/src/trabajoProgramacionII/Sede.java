package trabajoProgramacionII;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public abstract class Sede {
    protected String codigo;
    protected String nombre;
    protected String direccion;
    protected int capacidad;
    protected Set<Sector> sectores;
    protected double precioBase;

    public Sede(String codigo, String nombre, String direccion, int capacidad, double precioBase) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.direccion = direccion;
        this.capacidad = capacidad;
        this.precioBase = precioBase;
        this.sectores = new HashSet<>();
    }

    public abstract String obtenerTipo();
    public abstract double calcularPrecioEntrada(Sector sector, double precioBase);
    public abstract ArrayList<Sector> obtenerSectores();

    public boolean estaAgotada() {
        if (this instanceof Estadio) {
            return capacidad <= 0;
        } else {
            throw new UnsupportedOperationException("La verificación otro tipo de sede que no sea estadio aún no está implementada.");
        }
    }

    public void agregarSector(Sector sector) {
        sectores.add(sector);
    }

    // Getters y Setters
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
    public int getCapacidad() { return capacidad; }
    public double getPrecioBase() { return precioBase; }
    public Set<Sector> getSectores() { return sectores; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }
    public void setPrecioBase(double precioBase) { this.precioBase = precioBase; }
    public void setSectores(Set<Sector> sectores) { this.sectores = sectores; }
}