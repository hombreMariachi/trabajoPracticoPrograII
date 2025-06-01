package trabajoProgramacionII;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;

public class Usuario {

    private String nombre;
    private String apellido;
    private String email;
    private String contraseña;
    private Map<String, Entrada> entradasCompradas;
    private LocalDate fechaActual;

    public Usuario(String nombre, String apellido, String email, String contraseña) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.contraseña = contraseña;
        this.entradasCompradas = new HashMap<>();
        this.fechaActual = LocalDate.now();
    }
    
    
    public void mostrarEntradasUsuario() {
        if (entradasCompradas.isEmpty()) {
            System.out.println("El usuario no tiene entradas registradas.");
        } else {
            System.out.println("Entradas del usuario " + nombre + " " + apellido + ":");
            for (Entrada entrada : entradasCompradas.values()) {
                System.out.println(entrada);
            }
        }
    }

    
    @Override
    public String toString() {							///AGREGAR AL TAD
        return "Usuario{" +
               "nombre='" + nombre + '\'' +
               ", apellido='" + apellido + '\'' +
               ", email='" + email + '\'' +
               ", contraseña='" + contraseña + '\'' +
               ", entradasCompradas=" + entradasCompradas.toString() +
               ", fechaActual=" + fechaActual +
               '}';
    }
    
    
    
    public void agregarEntrada(Entrada entrada) {
        entradasCompradas.put(entrada.obtenerCodigo(), entrada);
    }

    public boolean eliminarEntrada(String codigoEntrada) {
        return entradasCompradas.remove(codigoEntrada) != null;
    }

    public boolean existeEntrada(String codigoEntrada) {
        return entradasCompradas.containsKey(codigoEntrada);
    }

    public Entrada buscarEntradaPorCodigo(String codigoEntrada) {
        return entradasCompradas.get(codigoEntrada);
    }

    public boolean validarContraseña(String contraseñaIngresada) {
        return this.contraseña.equals(contraseñaIngresada);
    }


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public Map<String, Entrada> getEntradasCompradas() {
		return entradasCompradas;
	}

	public void setEntradasCompradas(Map<String, Entrada> entradasCompradas) {
		this.entradasCompradas = entradasCompradas;
	}

	public LocalDate getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(LocalDate fechaActual) {
		this.fechaActual = fechaActual;
	}
    
    

} 
