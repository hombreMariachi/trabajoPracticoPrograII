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

    public ArrayList<Entrada> listarEntradas() {
        ArrayList<Entrada> lista = new ArrayList<>(entradasCompradas.values());
        lista.sort((e1, e2) -> e1.obtenerFecha().compareTo(e2.obtenerFecha()));
        return lista;
    }

    public ArrayList<Entrada> listarEntradasFuturas() {
        ArrayList<Entrada> futuras = new ArrayList<>();
        for (Entrada entrada : entradasCompradas.values()) {
            if (!entrada.obtenerFecha().isBefore(fechaActual)) {
                futuras.add(entrada);
            }
        }
        futuras.sort((e1, e2) -> e1.obtenerFecha().compareTo(e2.obtenerFecha()));
        return futuras;
    }

    public ArrayList<Entrada> listarEntradasPasadas() {
        ArrayList<Entrada> pasadas = new ArrayList<>();
        for (Entrada entrada : entradasCompradas.values()) {
            if (entrada.obtenerFecha().isBefore(fechaActual)) {
                pasadas.add(entrada);
            }
        }
        pasadas.sort((e1, e2) -> e1.obtenerFecha().compareTo(e2.obtenerFecha()));
        return pasadas;
    }
} 
