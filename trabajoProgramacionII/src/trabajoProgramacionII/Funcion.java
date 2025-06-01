package trabajoProgramacionII;

import java.time.LocalDate;

public class Funcion {

	private Sede sede;
	private LocalDate fecha;
	private double precioBase;
	private Espectaculo espectaculo;
	private String clave;

	public Funcion(Sede sede, LocalDate fecha, double precioBase, Espectaculo espectaculo) {
		this.sede = sede;
		this.fecha = fecha;
		this.precioBase = precioBase;
		this.espectaculo = espectaculo;
	}

	// Getters
	public Sede getSede() {
		return sede;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public double getPrecioBase() {
		return precioBase;
	}

	public Espectaculo getEspectaculo() {
		return espectaculo;
	}

	public String getClave() {
		return clave;
	}

	// Setters
	public void setSede(Sede sede) {
		this.sede = sede;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public void setPrecioBase(double precioBase) {
		this.precioBase = precioBase;
	}

	public void setEspectaculo(Espectaculo espectaculo) {
		this.espectaculo = espectaculo;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	// Lógica
	public boolean estaAgotada() {
		return sede.estaAgotada(); // Método de la clase Sede
	}

	@Override
	public String toString() {
		return "Clave: " + clave + " | Función en: " + sede.getNombre() + " | Fecha: " + fecha + " | Precio base: $"
				+ precioBase;
	}
}