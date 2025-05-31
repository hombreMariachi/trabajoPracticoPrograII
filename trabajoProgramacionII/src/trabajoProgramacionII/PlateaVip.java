package trabajoProgramacionII;

class PlateaVip extends Sector {

    public PlateaVip(int numero, int cantidadAsientos) {
        super(numero, cantidadAsientos);
    }

    @Override
    public double calcularPrecio(double precioBase) {
        return precioBase * 1.70;
    }

    @Override
    public String obtenerTipo() {
        return "VIP";
    }
} 