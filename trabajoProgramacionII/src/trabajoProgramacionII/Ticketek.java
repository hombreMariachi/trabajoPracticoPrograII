package trabajoProgramacionII;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;

public class Ticketek {

	private Map<String, Usuario> usuarios;
	private Map<String, Espectaculo> espectaculos;
	private Map<String, Sede> sedes;

	public Ticketek() {
		this.usuarios = new HashMap<>();
		this.espectaculos = new HashMap<>();
		this.sedes = new HashMap<>();
	}

	private boolean sedeExiste(String codigoSede) { // Clase auxiliar. Agregar al TAD
		return sedes.containsKey(codigoSede);
	}

	private void registrarSedeInterna(String codigoSede, Sede sede) { // Clase auxuliar. Agregar al TAD.
		sedes.put(codigoSede, sede);
	}

	public void registrarSede(String codigoSede, int tipoDeSede, String nombre, String direccion, double precioBase,
			int capacidadMaxima) {
		if (!sedeExiste(codigoSede)) {
			Sede nuevaSede = switch (tipoDeSede) {
				case 1 -> new Estadio(codigoSede, nombre, direccion, capacidadMaxima, precioBase);
				case 2 -> new Teatro(codigoSede, nombre, direccion, capacidadMaxima, precioBase);
				case 3 -> new MiniEstadio(codigoSede, nombre, direccion, capacidadMaxima, precioBase);
				default -> throw new IllegalArgumentException("Tipo de sede no válido");
			};
			
			registrarSedeInterna(codigoSede, nuevaSede);
		
		}
	}

	public Sede obtenerSede(String codigoSede) { // -----------------------Metodo auxiliar para obtener sede.Agregar al
													// tad
		if (sedes.containsKey(codigoSede)) {
			return sedes.get(codigoSede);
		} else {
			throw new IllegalArgumentException("Sede no encontrada con el código: " + codigoSede);
		}
	}

	private boolean usuarioExiste(String email) { // ------------------------Funcion auxiliar. Agregar al TAD.
		return usuarios.containsKey(email);
	}

	private void agregarUsuario(String email, Usuario usuario) { // ---------------------------------- Funcion auxiliar.
																	// Agregar al TAD.
		usuarios.put(email, usuario);
	}

	public void registrarUsuario(String nombre, String apellido, String email, String contraseña) {
		if (usuarios.containsKey(email)) {
			throw new IllegalArgumentException("El usuario con el email " + email + " ya existe.");
		} else {
			Usuario nuevoUsuario = new Usuario(nombre, apellido, email, contraseña);
			usuarios.put(email, nuevoUsuario);
		}
	}

	public Usuario obtenerUsuario(String email) { // ---------------------------------AGREGAR AL TAD
		if (usuarios.containsKey(email)) {
			return usuarios.get(email);
		} else {
			throw new IllegalArgumentException("El usuario con email '" + email + "' no existe.");
		}
	}

	public void registrarEspectaculo(String nombreEspectaculo, String codigoEspectaculo) {
		if (!existeEspectaculo(codigoEspectaculo)) {
			Espectaculo nuevoEspectaculo = new Espectaculo(codigoEspectaculo, nombreEspectaculo);
			espectaculos.put(codigoEspectaculo, nuevoEspectaculo);
		} else {
			throw new IllegalArgumentException("El espectáculo ya existe con el código '" + codigoEspectaculo + "'.");
		}
	}

	public Espectaculo obtenerEspectaculo(String codigoEspectaculo) { // ---------------------------------AGREGAR AL TAD
		if (espectaculos.containsKey(codigoEspectaculo)) {
			return espectaculos.get(codigoEspectaculo);
		}
		throw new IllegalArgumentException("El espectáculo con código '" + codigoEspectaculo + "' no existe.");
	}
	

	public void registrarFuncion(String codigoEspectaculo, String codigoSede, LocalDate fecha) { /// ---------------------------------AGREGAR
																									/// AL TAD
		if (!existeEspectaculo(codigoEspectaculo)) {
			throw new IllegalArgumentException("El espectáculo no existe.");
		}
		if (!sedeExiste(codigoSede)) {
			throw new IllegalArgumentException("La sede no existe.");
		}

		Espectaculo espectaculo = obtenerEspectaculo(codigoEspectaculo);
		Sede sede = obtenerSede(codigoSede);
		double precioBase = sede.getPrecioBase();

		Funcion nuevaFuncion = new Funcion(sede, fecha, precioBase, espectaculo);
		espectaculo.agregarFuncion(nuevaFuncion);
	}
	
	

	public void venderEntradaGeneral(Usuario usuario, Funcion funcion, int cantidadEntradas) {
	    if (usuario == null) {
	        throw new IllegalArgumentException("El usuario no puede ser nulo.");
	    }

	    if (funcion == null) {
	        throw new IllegalArgumentException("La función no puede ser nula.");
	    }

	    if (cantidadEntradas <= 0) {
	        throw new IllegalArgumentException("La cantidad de entradas debe ser mayor a cero.");
	    }

	    Sede sede = funcion.getSede();

	    if (sede.getCapacidad() < cantidadEntradas) {
	        throw new IllegalStateException("No hay suficiente capacidad disponible en la sede.");
	    }

	    for (int i = 0; i < cantidadEntradas; i++) {
	        // Reducir capacidad y crear entrada por cada una solicitada
	        sede.reducirCapacidad();
	        Entrada entrada = new Entrada(funcion, null, funcion.getEspectaculo().getCodigo());
	        usuario.agregarEntrada(entrada);
	    }
	}
	
	
	
	public Funcion obtenerFuncion(String codigoEspectaculo, String codigoSede, LocalDate fecha) {	//agregar al TAD
	    Espectaculo espectaculo = obtenerEspectaculo(codigoEspectaculo);
	    return espectaculo.obtenerFuncion(codigoSede, fecha);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//------------------------------------------HASTA ACA LLEGUE----------------------------------------------------

	private ArrayList<Funcion> listarFuncionesDeEspectaculo(Espectaculo espectaculo) { // AUXILIAR: AGREGAR AL TAD
		return espectaculo.listarFunciones();
	}
	

	private String obtenerNombreSedeDeFuncion(Funcion funcion) { // AUXILIA: AGREGAR AL TAD
		return funcion.getSede().getNombre();
	}

	public ArrayList<String> listarSedesPorEspectaculo(String codigoEspectaculo) {
		ArrayList<String> sedesLista = new ArrayList<>();
		if (existeEspectaculo(codigoEspectaculo)) {
			Espectaculo espectaculo = obtenerEspectaculo(codigoEspectaculo);
			for (Funcion funcion : listarFuncionesDeEspectaculo(espectaculo)) {
				sedesLista.add(obtenerNombreSedeDeFuncion(funcion));
			}
		}
		return sedesLista;
	}

	private boolean existeUsuario(String email) { // AUXILIAR: AGREGAR AL TAD
		return usuarios.containsKey(email);
	}

	public ArrayList<Entrada> listarEntradasUsuario(String email) {
		if (existeUsuario(email)) {
			return obtenerUsuario(email).listarEntradas();
		} else {
			throw new IllegalArgumentException("El usuario con email " + email + " no está registrado.");
		}
	}

	public ArrayList<Entrada> listarEntradasFuturas(String email) {
		if (existeUsuario(email)) {
			return obtenerUsuario(email).listarEntradasFuturas();
		} else {
			throw new IllegalArgumentException("El usuario con email " + email + " no está registrado.");
		}
	}

	public ArrayList<Entrada> listarEntradasPasadas(String email) {
		if (existeUsuario(email)) {
			return obtenerUsuario(email).listarEntradasPasadas();
		} else {
			throw new IllegalArgumentException("El usuario con email " + email + " no está registrado.");
		}
	}
	
	

	public boolean anularEntrada(String codigoEntrada, String email, String contraseña) {
		if (autenticarUsuario(email, contraseña)) {
			Usuario usuario = obtenerUsuario(email);
			return usuario.eliminarEntrada(codigoEntrada);
		} else {
			return false;
		}
	}

	private Usuario obtenerUsuarioConEntrada(String codigoEntrada) { // AUXILIAR: Agregar al TAD
		for (Usuario usuario : usuarios.values()) {
			if (usuario.existeEntrada(codigoEntrada)) {
				return usuario;
			}
		}
		throw new IllegalArgumentException("No se encontró un usuario con la entrada: " + codigoEntrada); // AUXILIAR:
																											// Agregar
																											// al TAD
	}

	private Entrada buscarEntradaPorCodigo(String codigoEntrada) {
		for (Usuario usuario : usuarios.values()) {
			if (usuario.existeEntrada(codigoEntrada)) {
				return usuario.buscarEntradaPorCodigo(codigoEntrada);
			}
		}
		throw new IllegalArgumentException("No se encontró ninguna entrada con el código: " + codigoEntrada);
	}

	public boolean cambiarEntradaAOtraSede(String codigoEntrada, String nuevoCodigoSede, LocalDate nuevaFecha) {

		Entrada entradaOriginal = buscarEntradaPorCodigo(codigoEntrada);
		if (entradaOriginal == null)
			return false;

		Usuario usuario = obtenerUsuarioConEntrada(codigoEntrada);
		if (usuario == null)
			return false;

		String codigoEspectaculo = entradaOriginal.obtenerCodigoEspectaculo();

		if (!existeEspectaculo(codigoEspectaculo))
			return false;

		Espectaculo espectaculo = obtenerEspectaculo(codigoEspectaculo);

		if (!espectaculo.existeFuncion(nuevoCodigoSede, nuevaFecha))
			return false;

		Funcion nuevaFuncion = espectaculo.getFuncion(nuevoCodigoSede, nuevaFecha);

		Sede sede = nuevaFuncion.getSede();

		String tipoSector = entradaOriginal.getSector().obtenerTipo();
		int numeroSector = entradaOriginal.getSector().obtenerNumero();

		Sector sector = sede.obtenerSector(tipoSector, numeroSector);

		if (!sector.tieneAsientoDisponible())
			return false;

		// Remover la entrada original
		usuario.eliminarEntrada(codigoEntrada);

		// Crear y agregar nueva entrada
		sector.reducirAsientos();
		Entrada nuevaEntrada = new Entrada(nuevaFuncion, sector, codigoEspectaculo);
		usuario.agregarEntrada(nuevaEntrada);

		return true;
	}

	public double calcularCostoEntrada(String codigoEntrada, String email, String contraseña) {
		if (!autenticarUsuario(email, contraseña)) {
			throw new IllegalArgumentException("Usuario o contraseña incorrectos.");
		}

		Usuario usuario = obtenerUsuario(email);
		if (!usuario.existeEntrada(codigoEntrada)) {
			throw new IllegalArgumentException("El usuario no tiene una entrada con el código indicado.");
		}

		Entrada entrada = usuario.buscarEntradaPorCodigo(codigoEntrada);
		return entrada.obtenerPrecio();
	}

	private Funcion buscarFuncionPorSede(Espectaculo espectaculo, Sede sede) { // AUXILIAR: Agregar al TAD
		for (Funcion f : espectaculo.listarFunciones()) {
			if (f.getSede().equals(sede)) {
				return f;
			}
		}
		return null;
	}

	public double consultarValorEntrada(String codigoEspectaculo, String codigoSede, String tipoSector,
			int numeroSector) {
		if (!espectaculos.containsKey(codigoEspectaculo)) {
			throw new IllegalArgumentException("El espectáculo no existe.");
		}

		if (!sedes.containsKey(codigoSede)) {
			throw new IllegalArgumentException("La sede no existe.");
		}

		Espectaculo espectaculo = espectaculos.get(codigoEspectaculo);
		Sede sede = sedes.get(codigoSede);

		Funcion funcion = buscarFuncionPorSede(espectaculo, sede);
		if (funcion == null) {
			throw new IllegalArgumentException("No existe una función para esa sede en el espectáculo.");
		}

		Sector sector = sede.obtenerSector(tipoSector, numeroSector);
		if (sector == null) {
			throw new IllegalArgumentException("El sector indicado no existe en la sede.");
		}

		return sede.calcularPrecioEntrada(sector, funcion.getPrecioBase());
	}

	private double calcularRecaudacionPorUsuario(Usuario usuario, String codigoEspectaculo) { // AUXILIAR: Agregar al
																								// TAD
		double subtotal = 0;
		for (Entrada entrada : usuario.listarEntradas()) {
			if (entrada.obtenerCodigoEspectaculo().equals(codigoEspectaculo)) {
				subtotal += entrada.obtenerPrecio();
			}
		}
		return subtotal;
	}

	public double totalRecaudadoPorEspectaculo(String codigoEspectaculo) {
		if (!existeEspectaculo(codigoEspectaculo)) {
			throw new IllegalArgumentException("El espectáculo con código '" + codigoEspectaculo + "' no existe.");
		}

		double totalRecaudado = 0;
		for (Usuario usuario : usuarios.values()) {
			totalRecaudado += calcularRecaudacionPorUsuario(usuario, codigoEspectaculo);
		}

		return totalRecaudado;
	}

	public boolean autenticarUsuario(String email, String contraseña) {
		if (existeUsuario(email)) {
			Usuario usuario = obtenerUsuario(email);
			return usuario.validarContraseña(contraseña);
		} else {
			return false;
		}
	}

	public ArrayList<Funcion> listarFuncionesDeEspectaculo(String codigoEspectaculo) {
		if (!existeEspectaculo(codigoEspectaculo)) {
			throw new IllegalArgumentException("No existe un espectáculo con el código: " + codigoEspectaculo);
		}

		Espectaculo espectaculo = obtenerEspectaculo(codigoEspectaculo);
		return espectaculo.listarFunciones();
	}

	public boolean existeEspectaculo(String codigoEspectaculo) {
		return espectaculos.containsKey(codigoEspectaculo);
	}
}
