package trabajoProgramacionII;

import java.util.HashMap;
import java.util.Map;

public abstract class Sector {
    protected int numero;
    protected int cantidadAsientos;
    private int asientosPorFila;
    private int porcentaje;
    protected Map<Integer, Boolean> asientosOcupados;

    public Sector(int numero, int cantidadAsientos, int asientosPorFila, int porcentaje) {
        this.numero = numero;
        this.cantidadAsientos = cantidadAsientos;
        this.asientosPorFila = asientosPorFila;
        this.porcentaje = porcentaje;
        this.asientosOcupados = new HashMap<>();
    }

    public abstract double calcularPrecio(double precioBase);
    public abstract String obtenerTipo();

    public void ocuparAsiento(int numeroAsiento) {
        asientosOcupados.put(numeroAsiento, true);
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

    public int getNumero() { return numero; }
    public int obtenerCantidadAsientos() { return cantidadAsientos; }
    public int getAsientosPorFila() { return asientosPorFila; }
}
