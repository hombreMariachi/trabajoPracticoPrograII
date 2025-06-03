package trabajoProgramacionII;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Entrada implements IEntrada {

    private String codigoEntrada;
    private String codigoEspectaculo;
    private Sector sector;
    private Funcion funcion;
    private Usuario usuario;
    private int numeroAsiento;
    private static int contador = 1;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");

    public Entrada(Funcion funcion, Sector sector, String codigoEspectaculo, Usuario usuario) {
        this.funcion = funcion;
        this.sector = sector;
        this.codigoEspectaculo = codigoEspectaculo;
        this.usuario = usuario;
        this.codigoEntrada = String.valueOf(contador++);
    }

    @Override
    public double precio() {
        if (sector == null) { // Estadio
            return funcion.getPrecioBase();
        } else { // Teatro o MiniEstadio
            double precioSector = sector.calcularPrecio(funcion.getPrecioBase());
            if (funcion.getSede() instanceof MiniEstadio) {
                MiniEstadio mini = (MiniEstadio) funcion.getSede();
                return precioSector + mini.getConsumicionLibre();
            }
            return precioSector;
        }
    }

    @Override
    public String ubicacion() {
        if (sector == null) {
            return "CAMPO";
        } else {
            int fila = calcularFila();
            return sector.obtenerTipo() + " f:" + fila + " a:" + numeroAsiento;
        }
    }

    private int calcularFila() {
        if (funcion.getSede() instanceof Teatro && ((Teatro)funcion.getSede()).getAsientosPorFila() > 0) {
            return (numeroAsiento - 1) / ((Teatro)funcion.getSede()).getAsientosPorFila() + 1;
        } else if (sector != null && funcion.getSede() instanceof MiniEstadio) {
            return (numeroAsiento - 1) / ((MiniEstadio)funcion.getSede()).getAsientosPorFila() + 1;
        }
        return 1; // Default
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append(codigoEntrada)
          .append(" - ")
          .append(codigoEspectaculo)
          .append(" - ")
          .append(funcion.getFecha().format(formatter));
        
        // Agregar P si la fecha ya pasó
        if (funcion.getFecha().isBefore(LocalDate.now())) {
            sb.append(" P");
        }
        
        sb.append(" - ")
          .append(funcion.getSede().getNombre())
          .append(" - ")
          .append(ubicacion());
        
        return sb.toString();
    }

    // Getters y setters
    public String obtenerCodigo() { return codigoEntrada; }
    public String obtenerCodigoEspectaculo() { return codigoEspectaculo; }
    public LocalDate obtenerFecha() { return funcion.getFecha(); }
    public Sector getSector() { return sector; }
    public Funcion getFuncion() { return funcion; }
    public Usuario getUsuario() { return usuario; }
    public void setNumeroAsiento(int numeroAsiento) { this.numeroAsiento = numeroAsiento; }
    public int getNumeroAsiento() { return numeroAsiento; }
}

