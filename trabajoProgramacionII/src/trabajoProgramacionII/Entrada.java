package trabajoProgramacionII;

import java.time.LocalDate;

public class Entrada {

    private String codigoEntrada;
    private String codigoEspectaculo;
    private Sector sector;
    private Funcion funcion;

    public Entrada(Funcion funcion, Sector sector, String codigoEspectaculo) {
        this.funcion = funcion;
        this.sector = sector;
        this.codigoEspectaculo = codigoEspectaculo;
        this.codigoEntrada = generarCodigoEntrada();
    }

    private String generarCodigoEntrada() {
        return codigoEspectaculo + "-" + funcion.getFecha() + "-" + sector.obtenerTipo() + sector.getNumero();
    }

    public double obtenerPrecio() {
        return funcion.getSede().calcularPrecioEntrada(sector, funcion.getPrecioBase());
    }

    public String obtenerUbicacion() {
        return sector.obtenerTipo() + ", sector " + sector.getNumero();
    }

    public String obtenerCodigo() {
        return codigoEntrada;
    }

    public String obtenerCodigoEspectaculo() {
        return codigoEspectaculo;
    }

    public LocalDate obtenerFecha() {
        return funcion.getFecha();
    }

	public String getCodigoEntrada() {
		return codigoEntrada;
	}

	public void setCodigoEntrada(String codigoEntrada) {
		this.codigoEntrada = codigoEntrada;
	}

	public String getCodigoEspectaculo() {
		return codigoEspectaculo;
	}

	public void setCodigoEspectaculo(String codigoEspectaculo) {
		this.codigoEspectaculo = codigoEspectaculo;
	}

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

	public Funcion getFuncion() {
		return funcion;
	}

	public void setFuncion(Funcion funcion) {
		this.funcion = funcion;
	}
    
    
    
} 