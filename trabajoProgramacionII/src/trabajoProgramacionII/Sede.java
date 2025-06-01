package trabajoProgramacionII;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public abstract class Sede {
	protected String codigo;		//agregado, codigo que uno genera desde el main
	protected String nombre;
	protected String direccion;
	protected int capacidad;
	protected Set<Sector> sectores;
	protected double precioBase; // AGREGADO , NO TENIA PRECIO BASE LA SEDE PERO ES NECESARIA.

	public Sede(String codigo, String nombre, String direccion, int capacidad, double precioBase) {
	    this.codigo = codigo;
	    this.nombre = nombre;
	    this.direccion = direccion;
	    this.capacidad = capacidad;
	    this.precioBase = precioBase;
	    this.sectores = new HashSet<>();
	}
	
	public boolean estaAgotada() {
	    if (this instanceof Estadio) {
	        return capacidad <= 0;
	    } else {
	        throw new UnsupportedOperationException("La verificación otro tipo de sede que no sea estadio aún no está implementada.");
	    }
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

	public String getCodigo() {
	    return codigo;
	}
	
	
	public String getNombre() {
		return nombre;
	}

	public double getPrecioBase() {
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
