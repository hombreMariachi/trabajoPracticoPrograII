package trabajoProgramacionII;

class PlateaBaja extends Sector {

    public PlateaBaja(int numero, int cantidadAsientos) {
        super(numero, cantidadAsientos);
    }

    @Override
    public double calcularPrecio(double precioBase) {
        return precioBase * 1.50;
    }

    @Override
    public String obtenerTipo() {
        return "BAJA";
    }
}