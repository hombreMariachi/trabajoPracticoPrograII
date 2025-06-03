package trabajoProgramacionII;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Ticketek implements ITicketek {

    private Map<String, Usuario> usuarios;
    private Map<String, Espectaculo> espectaculos;
    private Map<String, Sede> sedes;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");

    public Ticketek() {
        this.usuarios = new HashMap<>();
        this.espectaculos = new HashMap<>();
        this.sedes = new HashMap<>();
    }

    @Override
    public void registrarSede(String nombre, String direccion, int capacidadMaxima) {
        if (sedes.containsKey(nombre)) {
            throw new RuntimeException("Ya existe una sede con ese nombre.");
        }
        Estadio estadio = new Estadio(nombre, nombre, direccion, capacidadMaxima, 0);
        sedes.put(nombre, estadio);
    }

    @Override
    public void registrarSede(String nombre, String direccion, int capacidadMaxima, int asientosPorFila, 
                             String[] sectores, int[] capacidad, int[] porcentajeAdicional) {
        if (sedes.containsKey(nombre)) {
            throw new RuntimeException("Ya existe una sede con ese nombre.");
        }

        Teatro teatro = new Teatro(nombre, nombre, direccion, capacidadMaxima, 0, asientosPorFila);

        for (int i = 0; i < sectores.length; i++) {
            String tipo = sectores[i];
            int capacidadSector = capacidad[i];
            int porcentaje = porcentajeAdicional[i];

            Sector sector = crearSector(tipo, i + 1, capacidadSector, asientosPorFila, porcentaje);
            teatro.agregarSector(sector);
        }

        sedes.put(nombre, teatro);
    }

    @Override
    public void registrarSede(String nombre, String direccion, int capacidadMaxima, int asientosPorFila, 
                             int cantidadPuestos, double precioConsumicion, String[] sectores, 
                             int[] capacidad, int[] porcentajeAdicional) {
        if (sedes.containsKey(nombre)) {
            throw new RuntimeException("Ya existe una sede con ese nombre.");
        }

        MiniEstadio mini = new MiniEstadio(nombre, nombre, direccion, capacidadMaxima, 
                                          precioConsumicion, cantidadPuestos, asientosPorFila);

        for (int i = 0; i < sectores.length; i++) {
            String tipo = sectores[i];
            int capacidadSector = capacidad[i];
            int porcentaje = porcentajeAdicional[i];

            Sector sector = crearSector(tipo, i + 1, capacidadSector, asientosPorFila, porcentaje);
            mini.agregarSector(sector);
        }

        sedes.put(nombre, mini);
    }

    private Sector crearSector(String tipo, int numero, int capacidad, int asientosPorFila, int porcentaje) {
        String tipoLimpio = tipo.toLowerCase().trim();
        
        // Extraer solo la parte importante del tipo
        if (tipoLimpio.contains("vip")) {
            return new PlateaVip(numero, capacidad, asientosPorFila, porcentaje);
        } else if (tipoLimpio.contains("comun") || tipoLimpio.contains("común")) {
            return new PlateaComun(numero, capacidad, asientosPorFila, porcentaje);
        } else if (tipoLimpio.contains("baja")) {
            return new PlateaBaja(numero, capacidad, asientosPorFila, porcentaje);
        } else if (tipoLimpio.contains("alta")) {
            return new PlateaAlta(numero, capacidad, asientosPorFila, porcentaje);
        } else {
            throw new IllegalArgumentException("Tipo de sector no reconocido: " + tipo);
        }
    }

    @Override
    public void registrarUsuario(String email, String nombre, String apellido, String contrasenia) {
        if (usuarios.containsKey(email)) {
            throw new RuntimeException("El usuario con el email " + email + " ya existe.");
        }
        Usuario nuevoUsuario = new Usuario(nombre, apellido, email, contrasenia);
        usuarios.put(email, nuevoUsuario);
    }

    @Override
    public void registrarEspectaculo(String nombre) {
        if (espectaculos.containsKey(nombre)) {
            throw new RuntimeException("El espectáculo ya existe.");
        }
        Espectaculo nuevoEspectaculo = new Espectaculo(nombre, nombre);
        espectaculos.put(nombre, nuevoEspectaculo);
    }

    @Override
    public void agregarFuncion(String nombreEspectaculo, String fecha, String sede, double precioBase) {
        if (!espectaculos.containsKey(nombreEspectaculo)) {
            throw new RuntimeException("El espectáculo no existe.");
        }
        if (!sedes.containsKey(sede)) {
            throw new RuntimeException("La sede no existe.");
        }

        LocalDate fechaLocal = LocalDate.parse(fecha, formatter);
        
        // Verificar que no haya otra función en la misma fecha
        for (Espectaculo esp : espectaculos.values()) {
            for (Funcion func : esp.listarFunciones()) {
                if (func.getFecha().equals(fechaLocal)) {
                    throw new RuntimeException("Ya existe una función en esa fecha.");
                }
            }
        }

        Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
        Sede sedeObj = sedes.get(sede);

        Funcion nuevaFuncion = new Funcion(sedeObj, fechaLocal, precioBase, espectaculo);
        espectaculo.agregarFuncion(nuevaFuncion);
    }

    @Override
    public List<IEntrada> venderEntrada(String nombreEspectaculo, String fecha, String email, 
                                       String contrasenia, int cantidadEntradas) {
        if (!autenticarUsuario(email, contrasenia)) {
            throw new RuntimeException("Usuario o contraseña incorrectos.");
        }

        Usuario usuario = usuarios.get(email);
        Espectaculo esp = espectaculos.get(nombreEspectaculo);
        LocalDate fechaLocal = LocalDate.parse(fecha, formatter);
        
        Funcion funcion = esp.obtenerFuncion(fechaLocal);
        
        // Verificar que sea un estadio
        if (!(funcion.getSede() instanceof Estadio)) {
            throw new RuntimeException("Esta función requiere asientos numerados.");
        }

        List<IEntrada> entradas = new ArrayList<>();
        for (int i = 0; i < cantidadEntradas; i++) {
            Entrada entrada = new Entrada(funcion, null, nombreEspectaculo, usuario);
            usuario.agregarEntrada(entrada);
            entradas.add(entrada);
        }

        return entradas;
    }

    @Override
    public List<IEntrada> venderEntrada(String nombreEspectaculo, String fecha, String email, 
                                       String contrasenia, String sector, int[] asientos) {
        if (!autenticarUsuario(email, contrasenia)) {
            throw new RuntimeException("Usuario o contraseña incorrectos.");
        }

        Usuario usuario = usuarios.get(email);
        Espectaculo esp = espectaculos.get(nombreEspectaculo);
        LocalDate fechaLocal = LocalDate.parse(fecha, formatter);
        
        Funcion funcion = esp.obtenerFuncion(fechaLocal);
        Sede sede = funcion.getSede();
        
        // Verificar que NO sea un estadio
        if (sede instanceof Estadio) {
            throw new RuntimeException("Esta función no tiene asientos numerados.");
        }
        
        Sector sectorObj = buscarSector(sede, sector);

        if (sectorObj == null) {
            throw new RuntimeException("El sector no existe en esta sede.");
        }

        List<IEntrada> entradas = new ArrayList<>();
        for (int asiento : asientos) {
            sectorObj.ocuparAsiento(asiento);
            Entrada entrada = new Entrada(funcion, sectorObj, nombreEspectaculo, usuario);
            entrada.setNumeroAsiento(asiento);
            usuario.agregarEntrada(entrada);
            entradas.add(entrada);
        }

        return entradas;
    }

    private Sector buscarSector(Sede sede, String tipoSector) {
        String tipoLimpio = tipoSector.toLowerCase().trim();
        
        for (Sector s : sede.getSectores()) {
            String tipoSectorActual = s.obtenerTipo().toLowerCase();
            
            // Buscar por coincidencia parcial
            if (tipoLimpio.contains(tipoSectorActual) || tipoSectorActual.contains("vip") && tipoLimpio.contains("vip") ||
                tipoSectorActual.contains("comun") && (tipoLimpio.contains("comun") || tipoLimpio.contains("común")) ||
                tipoSectorActual.contains("baja") && tipoLimpio.contains("baja") ||
                tipoSectorActual.contains("alta") && tipoLimpio.contains("alta")) {
                return s;
            }
        }
        return null;
    }

    @Override
    public String listarFunciones(String nombreEspectaculo) {
        if (!espectaculos.containsKey(nombreEspectaculo)) {
            throw new RuntimeException("El espectáculo no existe.");
        }
        
        Espectaculo esp = espectaculos.get(nombreEspectaculo);
        StringBuilder sb = new StringBuilder();
        
        List<Funcion> funciones = esp.listarFunciones();
        funciones.sort((f1, f2) -> f1.getFecha().compareTo(f2.getFecha()));
        
        for (Funcion funcion : funciones) {
            sb.append(" - (").append(funcion.getFecha().format(formatter))
              .append(") ").append(funcion.getSede().getNombre());
            
            if (funcion.getSede() instanceof Estadio) {
                // Para estadios: mostrar entradas vendidas / capacidad
                int entradasVendidas = contarEntradasVendidasFuncion(nombreEspectaculo, funcion.getFecha());
                sb.append(" - ").append(entradasVendidas).append("/").append(funcion.getSede().getCapacidad());
            } else {
                // Para teatros y miniestadios: mostrar por sector
                sb.append(" - ");
                boolean primero = true;
                List<Sector> sectoresOrdenados = new ArrayList<>(funcion.getSede().getSectores());
                sectoresOrdenados.sort((s1, s2) -> s1.getNumero() - s2.getNumero());
                
                for (Sector sector : sectoresOrdenados) {
                    if (!primero) sb.append(" | ");
                    sb.append(sector.obtenerTipo()).append(": ")
                      .append(sector.asientosOcupados.size()).append("/")
                      .append(sector.obtenerCantidadAsientos());
                    primero = false;
                }
            }
            sb.append("\n");
        }
        
        return sb.toString();
    }

    private int contarEntradasVendidasFuncion(String espectaculo, LocalDate fecha) {
        int contador = 0;
        for (Usuario usuario : usuarios.values()) {
            for (Entrada entrada : usuario.getEntradasCompradas().values()) {
                if (entrada.obtenerCodigoEspectaculo().equals(espectaculo) &&
                    entrada.getFuncion().getFecha().equals(fecha)) {
                    contador++;
                }
            }
        }
        return contador;
    }

    @Override
    public List<IEntrada> listarEntradasEspectaculo(String nombreEspectaculo) {
        List<IEntrada> entradas = new ArrayList<>();
        for (Usuario usuario : usuarios.values()) {
            for (Entrada entrada : usuario.getEntradasCompradas().values()) {
                if (entrada.obtenerCodigoEspectaculo().equals(nombreEspectaculo)) {
                    entradas.add(entrada);
                }
            }
        }
        return entradas;
    }

    @Override
    public List<IEntrada> listarEntradasFuturas(String email, String contrasenia) {
        if (!autenticarUsuario(email, contrasenia)) {
            throw new RuntimeException("Usuario o contraseña incorrectos.");
        }
        return usuarios.get(email).listarEntradasFuturas();
    }

    @Override
    public List<IEntrada> listarTodasLasEntradasDelUsuario(String email, String contrasenia) {
        if (!autenticarUsuario(email, contrasenia)) {
            throw new RuntimeException("Usuario o contraseña incorrectos.");
        }
        return usuarios.get(email).listarEntradas();
    }

    @Override
    public boolean anularEntrada(IEntrada entrada, String contrasenia) {
        if (entrada == null) {
            throw new RuntimeException("La entrada no puede ser nula.");
        }
        
        Entrada entradaImpl = (Entrada) entrada;
        Usuario usuario = entradaImpl.getUsuario();
        
        if (!usuario.validarContraseña(contrasenia)) {
            throw new RuntimeException("Contraseña incorrecta.");
        }
        
        if (!usuario.existeEntrada(entradaImpl.obtenerCodigo())) {
            throw new RuntimeException("La entrada no existe o ya fue anulada.");
        }
        
        // Si la fecha ya pasó, devolver false
        if (entradaImpl.getFuncion().getFecha().isBefore(LocalDate.now())) {
            return false;
        }
        
        return usuario.eliminarEntrada(entradaImpl.obtenerCodigo());
    }

    @Override
    public IEntrada cambiarEntrada(IEntrada entrada, String contrasenia, String fecha, String sector, int asiento) {
        Entrada entradaImpl = (Entrada) entrada;
        Usuario usuario = entradaImpl.getUsuario();
        
        if (!usuario.validarContraseña(contrasenia)) {
            throw new RuntimeException("Contraseña incorrecta.");
        }
        
        // Verificar que la entrada original no esté en el pasado
        if (entradaImpl.getFuncion().getFecha().isBefore(LocalDate.now())) {
            throw new RuntimeException("No se puede cambiar una entrada del pasado.");
        }
        
        // Anular entrada original
        usuario.eliminarEntrada(entradaImpl.obtenerCodigo());
        
        // Crear nueva entrada con sector y asiento específicos
        LocalDate fechaLocal = LocalDate.parse(fecha, formatter);
        Espectaculo esp = entradaImpl.getFuncion().getEspectaculo();
        Funcion nuevaFuncion = esp.obtenerFuncion(fechaLocal);
        
        Sector sectorObj = buscarSector(nuevaFuncion.getSede(), sector);
        
        if (sectorObj == null) {
            throw new RuntimeException("El sector no existe en esa sede.");
        }
        
        sectorObj.ocuparAsiento(asiento);
        Entrada nuevaEntrada = new Entrada(nuevaFuncion, sectorObj, esp.getNombre(), usuario);
        nuevaEntrada.setNumeroAsiento(asiento);
        usuario.agregarEntrada(nuevaEntrada);
        
        return nuevaEntrada;
    }

    @Override
    public IEntrada cambiarEntrada(IEntrada entrada, String contrasenia, String fecha) {
        Entrada entradaImpl = (Entrada) entrada;
        Usuario usuario = entradaImpl.getUsuario();
        
        if (!usuario.validarContraseña(contrasenia)) {
            throw new RuntimeException("Contraseña incorrecta.");
        }
        
        // Verificar que la entrada original no esté en el pasado
        if (entradaImpl.getFuncion().getFecha().isBefore(LocalDate.now())) {
            throw new RuntimeException("No se puede cambiar una entrada del pasado.");
        }
        
        // Anular entrada original
        usuario.eliminarEntrada(entradaImpl.obtenerCodigo());
        
        // Crear nueva entrada (para estadios)
        LocalDate fechaLocal = LocalDate.parse(fecha, formatter);
        Espectaculo esp = entradaImpl.getFuncion().getEspectaculo();
        Funcion nuevaFuncion = esp.obtenerFuncion(fechaLocal);
        
        Entrada nuevaEntrada = new Entrada(nuevaFuncion, null, esp.getNombre(), usuario);
        usuario.agregarEntrada(nuevaEntrada);
        
        return nuevaEntrada;
    }

    @Override
    public double costoEntrada(String nombreEspectaculo, String fecha) {
        Espectaculo esp = espectaculos.get(nombreEspectaculo);
        LocalDate fechaLocal = LocalDate.parse(fecha, formatter);
        Funcion funcion = esp.obtenerFuncion(fechaLocal);
        
        return funcion.getPrecioBase();
    }

    @Override
    public double costoEntrada(String nombreEspectaculo, String fecha, String sector) {
        Espectaculo esp = espectaculos.get(nombreEspectaculo);
        LocalDate fechaLocal = LocalDate.parse(fecha, formatter);
        Funcion funcion = esp.obtenerFuncion(fechaLocal);
        
        Sector sectorObj = buscarSector(funcion.getSede(), sector);
        if (sectorObj != null) {
            double precioSector = sectorObj.calcularPrecio(funcion.getPrecioBase());
            if (funcion.getSede() instanceof MiniEstadio) {
                MiniEstadio mini = (MiniEstadio) funcion.getSede();
                return precioSector + mini.getConsumicionLibre();
            }
            return precioSector;
        }
        
        return 0;
    }

    @Override
    public double totalRecaudado(String nombreEspectaculo) {
        double total = 0;
        for (Usuario usuario : usuarios.values()) {
            for (Entrada entrada : usuario.getEntradasCompradas().values()) {
                if (entrada.obtenerCodigoEspectaculo().equals(nombreEspectaculo)) {
                    total += entrada.precio();
                }
            }
        }
        return total;
    }

    @Override
    public double totalRecaudadoPorSede(String nombreEspectaculo, String nombreSede) {
        double total = 0;
        for (Usuario usuario : usuarios.values()) {
            for (Entrada entrada : usuario.getEntradasCompradas().values()) {
                if (entrada.obtenerCodigoEspectaculo().equals(nombreEspectaculo) &&
                    entrada.getFuncion().getSede().getNombre().equals(nombreSede)) {
                    total += entrada.precio();
                }
            }
        }
        return total;
    }

    private boolean autenticarUsuario(String email, String contrasenia) {
        Usuario usuario = usuarios.get(email);
        return usuario != null && usuario.validarContraseña(contrasenia);
    }
}