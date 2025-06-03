package trabajoProgramacionII;


class PlateaBaja extends Sector {

    public PlateaBaja(int numero, int cantidadAsientos, int asientosPorFila, int porcentaje) {
        super(numero, cantidadAsientos, asientosPorFila, porcentaje); 
    }

    @Override
    public double calcularPrecio(double precioBase) {
        return precioBase * 1.50;
    }

    @Override
    public String obtenerTipo() {
        return "Baja";
    }
}