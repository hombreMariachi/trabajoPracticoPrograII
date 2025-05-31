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
    
    private boolean sedeExiste(String codigoSede) {				// Clase auxiliar. Agregar al TAD
        return sedes.containsKey(codigoSede);
    }
    
    private void registrarSedeInterna(String codigoSede, Sede sede) { //Clase auxuliar. Agregar al TAD.
        sedes.put(codigoSede, sede);
    }

    

    public void registrarSede(String codigoSede, int tipoDeSede, String nombre, String direccion, double precioBase, int capacidadMaxima) {
        if (!sedeExiste(codigoSede)) {
            Sede nuevaSede = switch (tipoDeSede) {
                case 1 -> new Estadio(nombre, direccion, capacidadMaxima, precioBase); 		// tipo 1: Estadio
                case 2 -> new Teatro(nombre, direccion, capacidadMaxima); 					// tipo 2: Teatro
                case 3 -> new MiniEstadio(nombre, direccion, capacidadMaxima, precioBase); 	// tipo 3: Miniestadio
                default -> throw new IllegalArgumentException("Tipo de sede no válido");
            };
            registrarSedeInterna(codigoSede, nuevaSede);
        }
    }
    
   
    
    
    private boolean usuarioExiste(String email) {					//Funcion auxiliar. Agregar al TAD.
        return usuarios.containsKey(email);
    }

    private void agregarUsuario(String email, Usuario usuario) {	//Funcion auxiliar. Agregar al TAD.
        usuarios.put(email, usuario);
    }

    
 
    public void registrarUsuario(String nombre, String apellido, String email, String contraseña) {
        if (!usuarioExiste(email)) {
            agregarUsuario(email, new Usuario(nombre, apellido, email, contraseña));
        }
    }
    
    
    private String generarCodigoEspectaculo(String nombre, LocalDate fecha) {			//Auxiliar. Agregar al TAD.
        return nombre + "-" + fecha.toString();
    }

    private Espectaculo crearEspectaculo(String codigo, String nombre) {			//Auxiliar. Agregar al TAD.
        return new Espectaculo(codigo, nombre);
    }

    private double obtenerPrecioBaseSede(int codigoSede) {						//Auxiliar. Agregar al TAD.
        return sedes.get(String.valueOf(codigoSede)).getPrecioBase();
    }

    private void agregarEspectaculo(String codigo, Espectaculo espectaculo) {			//Auxiliar. Agregar al TAD.
        espectaculos.put(codigo, espectaculo);
    }
    
    
    public void registrarEspectaculo(String nombreEspectaculo, int codigoSede, LocalDate fecha) {
        String codigo = generarCodigoEspectaculo(nombreEspectaculo, fecha);
        if (!existeEspectaculo(codigo)) {
            Espectaculo nuevo = crearEspectaculo(codigo, nombreEspectaculo);
            double precioBase = obtenerPrecioBaseSede(codigoSede);
            nuevo.agregarFuncion(String.valueOf(codigoSede), fecha, precioBase);
            agregarEspectaculo(codigo, nuevo);
        }
    }
    
    
    
    private boolean usuarioAutenticadoYEspectaculoExiste(String email, String contraseña, String codigoEspectaculo) {		//AUXILIAR AGREGAR AL TAD
        return autenticarUsuario(email, contraseña) && espectaculos.containsKey(codigoEspectaculo);
    }

    private Usuario obtenerUsuario(String email) {
        if (usuarios.containsKey(email)) {
            return usuarios.get(email);
        }
        throw new IllegalArgumentException("El usuario con ese email no está registrado.");
    }
    
    
    

    private Espectaculo obtenerEspectaculo(String codigoEspectaculo) {												//AUXILIAR AGREGAR AL TAD
        return espectaculos.get(codigoEspectaculo);
    }

    private Funcion obtenerFuncion(Espectaculo espectaculo, String codigoSede, LocalDate fecha) {				//AUXILIAR AGREGAR AL TAD
        return espectaculo.getFuncion(codigoSede, fecha);
    }
    
    

    private Sector obtenerSectorDisponible(Funcion funcion, String tipoSector, int numeroSector) {							//AUXILIAR AGREGAR AL TAD
        Sector sector = funcion.getSede().obtenerSector(tipoSector, numeroSector);
        if (sector == null) {
            throw new IllegalArgumentException("El sector especificado no existe: tipo " + tipoSector + ", número " + numeroSector);
        }

        if (!sector.tieneAsientoDisponible()) {
            throw new IllegalStateException("No hay asientos disponibles en el sector: tipo " + tipoSector + ", número " + numeroSector);
        }

        return sector;
    }
    
    

    public void venderEntrada(String email, String contraseña, String codigoEspectaculo, String codigoSede, LocalDate fecha, String tipoSector, int numeroSector, int cantidadEntradas) {
        if (usuarioAutenticadoYEspectaculoExiste(email, contraseña, codigoEspectaculo)) {
            Espectaculo espectaculo = obtenerEspectaculo(codigoEspectaculo);
            Funcion funcion = obtenerFuncion(espectaculo, codigoSede, fecha);
            Usuario usuario = obtenerUsuario(email);
            Sector sector = obtenerSectorDisponible(funcion, tipoSector, numeroSector);

            if (sector != null) {
                for (int i = 0; i < cantidadEntradas; i++) {
                    if (sector.tieneAsientoDisponible()) {
                        sector.reducirAsientos();
                        Entrada entrada = new Entrada(funcion, sector, codigoEspectaculo);
                        usuario.agregarEntrada(entrada);
                    }
                }
            }
        }
    }
    
    

    
    
    private ArrayList<Funcion> listarFuncionesDeEspectaculo(Espectaculo espectaculo) {		//AUXILIAR: AGREGAR AL TAD
        return espectaculo.listarFunciones();
    }

    private String obtenerNombreSedeDeFuncion(Funcion funcion) {						// AUXILIA: AGREGAR AL TAD
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
    
    
    
    private boolean existeUsuario(String email) {					//AUXILIAR: AGREGAR AL TAD
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
    
    


    private Usuario obtenerUsuarioConEntrada(String codigoEntrada) {									//AUXILIAR: Agregar al TAD
        for (Usuario usuario : usuarios.values()) {
            if (usuario.existeEntrada(codigoEntrada)) {
                return usuario;
            }
        }
        throw new IllegalArgumentException("No se encontró un usuario con la entrada: " + codigoEntrada);		//AUXILIAR: Agregar al TAD
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
        if (entradaOriginal == null) return false;

        Usuario usuario = obtenerUsuarioConEntrada(codigoEntrada);
        if (usuario == null) return false;

        String codigoEspectaculo = entradaOriginal.obtenerCodigoEspectaculo();

        if (!existeEspectaculo(codigoEspectaculo)) return false;

        Espectaculo espectaculo = obtenerEspectaculo(codigoEspectaculo);
        
        if (!espectaculo.existeFuncion(nuevoCodigoSede, nuevaFecha)) return false;

        Funcion nuevaFuncion = espectaculo.getFuncion(nuevoCodigoSede, nuevaFecha);
        
        Sede sede = nuevaFuncion.getSede();

        String tipoSector = entradaOriginal.getSector().obtenerTipo();
        int numeroSector = entradaOriginal.getSector().obtenerNumero();

        Sector sector = sede.obtenerSector(tipoSector, numeroSector);
        
        
        if (!sector.tieneAsientoDisponible()) return false;

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
    
    
    private Funcion buscarFuncionPorSede(Espectaculo espectaculo, Sede sede) {			//AUXILIAR: Agregar al TAD
        for (Funcion f : espectaculo.listarFunciones()) {
            if (f.getSede().equals(sede)) {
                return f;
            }
        }
        return null;
    }

    
    public double consultarValorEntrada(String codigoEspectaculo, String codigoSede, String tipoSector, int numeroSector) {
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
    
    

    private double calcularRecaudacionPorUsuario(Usuario usuario, String codigoEspectaculo) {			//AUXILIAR: Agregar al TAD
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
