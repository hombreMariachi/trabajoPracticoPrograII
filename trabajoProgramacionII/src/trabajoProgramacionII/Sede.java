package trabajoProgramacionII;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public abstract class Sede {

	protected String nombre;
	protected String direccion;
	protected int capacidad;
	protected Set<Sector> sectores;
	protected int precioBase; // AGREGADO , NO TENIA PRECIO BASE LA SEDE PERO ES NECESARIA. HAY QUE VER COMO
								// SE INSTANCIA EL PRECIOBASE

	public Sede(String nombre, String direccion, int capacidad) {
		this.nombre = nombre;
		this.direccion = direccion;
		this.capacidad = capacidad;
		this.sectores = new HashSet<>();
	}

	public abstract String obtenerTipo();

	public abstract double calcularPrecioEntrada(Sector sector, double precioBase);

	public abstract ArrayList<Sector> obtenerSectores(); // ESTO LO DEBEN DEFINIR LAS CLASES HIJAS EN CASO DE TENERLOS.

	public boolean tieneSector(String tipo, int numero) {	//si es una instancia de estadio devuelve false.
		if (this instanceof Estadio) {
			return false;
		} else {
			return true;
		}
	}

	public Sector obtenerSector(String tipo, int numero) {
		return sectores.stream().filter(s -> s.obtenerTipo().equalsIgnoreCase(tipo) && s.getNumero() == numero)
				.findFirst().orElse(null);
	}

	public void reducirCapacidad() {
		if (capacidad > 0) {
			capacidad--;
		}
	}

	public void agregarSector(Sector sector) {
		sectores.add(sector);
	}

	public String getNombre() {
		return nombre;
	}

	public int getPrecioBase() {
		return precioBase;
	}

	public void setPrecioBase(int precioBase) {
		this.precioBase = precioBase;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public Set<Sector> getSectores() {
		return sectores;
	}

	public void setSectores(Set<Sector> sectores) {
		this.sectores = sectores;
	}

}
