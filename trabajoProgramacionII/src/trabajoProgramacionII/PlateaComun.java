package trabajoProgramacionII;

class PlateaComun extends Sector {

    public PlateaComun(int numero, int cantidadAsientos) {
        super(numero, cantidadAsientos);
    }

    @Override
    public double calcularPrecio(double precioBase) {
        return precioBase * 1.40;
    }

    @Override
    public String obtenerTipo() {
        return "COMÚN";
    }
}
