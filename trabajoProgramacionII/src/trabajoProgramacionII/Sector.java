package trabajoProgramacionII;

import java.util.HashMap;
import java.util.Map;

public abstract class Sector {

    protected int numero;
    protected int cantidadAsientos;
    protected Map<Integer, Boolean> asientosOcupados;

    public Sector(int numero, int cantidadAsientos) {
        this.numero = numero;
        this.cantidadAsientos = cantidadAsientos;
        this.asientosOcupados = new HashMap<>();
    }

    public abstract double calcularPrecio(double precioBase);

    public abstract String obtenerTipo();

    public int obtenerNumero() {
        return numero;
    }

    public int obtenerCantidadAsientos() {
        return cantidadAsientos;
    }

    public boolean tieneAsientoDisponible() {
        return asientosOcupados.size() < cantidadAsientos;
    }

    public void reducirAsientos() {
        for (int i = 1; i <= cantidadAsientos; i++) {
            if (!asientosOcupados.containsKey(i)) {
                asientosOcupados.put(i, true);
                break;
            }
        }
    }
} 
