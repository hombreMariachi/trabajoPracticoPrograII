package trabajoProgramacionII;

class PlateaComun extends Sector {

    public PlateaComun(int numero, int cantidadAsientos, int asientosPorFila, int porcentaje) {
        super(numero, cantidadAsientos, asientosPorFila, porcentaje); 
    }

    @Override
    public double calcularPrecio(double precioBase) {
        return precioBase * 1.40;
    }

    @Override
    public String obtenerTipo() {
        return "Comun";
    }
}