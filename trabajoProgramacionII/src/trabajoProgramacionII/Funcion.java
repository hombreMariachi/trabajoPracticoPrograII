package trabajoProgramacionII;
import java.time.LocalDate;

public class Funcion {

    private Sede sede;
    private LocalDate fecha;
    private double precioBase;
    private Espectaculo espectaculo;

    public Funcion(Sede sede, LocalDate fecha, double precioBase, Espectaculo espectaculo) {
        this.sede = sede;
        this.fecha = fecha;
        this.precioBase = precioBase;
        this.espectaculo = espectaculo;
    }

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

    public boolean estaAgotada() {
        // Se considera agotada si no hay sectores con asientos disponibles
        for (Sector sector : sede.obtenerSectores()) {
            if (sector.tieneAsientoDisponible()) {
                return false;
            }
        }
        return true;
    }
}