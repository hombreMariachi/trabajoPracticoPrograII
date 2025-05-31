package trabajoProgramacionII;

class PlateaAlta extends Sector {

    public PlateaAlta(int numero, int cantidadAsientos) {
        super(numero, cantidadAsientos);
    }

    @Override
    public double calcularPrecio(double precioBase) {
        return precioBase * 1.00;
    }

    @Override
    public String obtenerTipo() {
        return "ALTA";
    }
}