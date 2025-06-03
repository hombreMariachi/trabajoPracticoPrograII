package trabajoProgramacionII;



	class PlateaAlta extends Sector {

	    public PlateaAlta(int numero, int cantidadAsientos, int asientosPorFila, int porcentaje) {
	        super(numero, cantidadAsientos, asientosPorFila, porcentaje); 
	    }

	    @Override
	    public double calcularPrecio(double precioBase) {
	        return precioBase * 1.00;
	    }

	    @Override
	    public String obtenerTipo() {
	        return "Alta";
	    }
	}