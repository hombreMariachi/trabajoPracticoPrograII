package trabajoProgramacionII;

import java.util.List;

public interface ITicketek {
	
    void registrarSede(String nombre, String direccion, int capacidadMaxima);
    void registrarSede(String nombre, String direccion, int capacidadMaxima, int asientosPorFila, String[] sectores, int[] capacidad, int[] porcentajeAdicional);
    void registrarSede(String nombre, String direccion, int capacidadMaxima, int asientosPorFila, int cantidadPuestos, double precioConsumicion, String[] sectores, int[] capacidad, int[] porcentajeAdicional);
    void registrarUsuario(String email, String nombre, String apellido, String contrasenia);
    void registrarEspectaculo(String nombre);
    void agregarFuncion(String nombreEspectaculo, String fecha, String sede, double precioBase);
    List<IEntrada> venderEntrada(String nombreEspectaculo, String fecha, String email, String contrasenia, int cantidadEntradas);
    List<IEntrada> venderEntrada(String nombreEspectaculo, String fecha, String email, String contrasenia, String sector, int[] asientos);
    String listarFunciones(String nombreEspectaculo);
    List<IEntrada> listarEntradasEspectaculo(String nombreEspectaculo);
    List<IEntrada> listarEntradasFuturas(String email, String contrasenia);
    List<IEntrada> listarTodasLasEntradasDelUsuario(String email, String contrasenia);
    boolean anularEntrada(IEntrada entrada, String contrasenia);
    IEntrada cambiarEntrada(IEntrada entrada, String contrasenia, String fecha, String sector, int asiento);
    IEntrada cambiarEntrada(IEntrada entrada, String contrasenia, String fecha);
    double costoEntrada(String nombreEspectaculo, String fecha);
    double costoEntrada(String nombreEspectaculo, String fecha, String sector);
    double totalRecaudado(String nombreEspectaculo);
    double totalRecaudadoPorSede(String nombreEspectaculo, String nombreSede);
}