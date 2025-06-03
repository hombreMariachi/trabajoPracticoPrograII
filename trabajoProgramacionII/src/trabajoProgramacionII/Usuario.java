package trabajoProgramacionII;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public List<IEntrada> listarEntradas() {
        return new ArrayList<IEntrada>(entradasCompradas.values());
    }

    public List<IEntrada> listarEntradasFuturas() {
        List<IEntrada> entradasFuturas = new ArrayList<>();
        for (Entrada entrada : entradasCompradas.values()) {
            if (entrada.obtenerFecha().isAfter(fechaActual)) {
                entradasFuturas.add(entrada);
            }
        }
        return entradasFuturas;
    }

    public List<IEntrada> listarEntradasPasadas() {
        List<IEntrada> entradasPasadas = new ArrayList<>();
        for (Entrada entrada : entradasCompradas.values()) {
            if (entrada.obtenerFecha().isBefore(fechaActual)) {
                entradasPasadas.add(entrada);
            }
        }
        return entradasPasadas;
    }

    // Getters
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getEmail() { return email; }
    public String getContraseña() { return contraseña; }
    public Map<String, Entrada> getEntradasCompradas() { return entradasCompradas; }
    public LocalDate getFechaActual() { return fechaActual; }

    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public void setEmail(String email) { this.email = email; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }
    public void setEntradasCompradas(Map<String, Entrada> entradasCompradas) { this.entradasCompradas = entradasCompradas; }
    public void setFechaActual(LocalDate fechaActual) { this.fechaActual = fechaActual; }
}


