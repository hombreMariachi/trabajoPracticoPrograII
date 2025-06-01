package trabajoProgramacionII;

import java.time.LocalDate;

public class Main {

	public static void main(String[] args) {
		Ticketek sistema = new Ticketek();

		sistema.registrarSede("S001", 1, "Monumental", "Av. Figueroa Alcorta 7597", 20000, 1000); // Estadio
		Sede sedeRegistrada1 = sistema.obtenerSede("S001");
		// System.out.println(sedeRegistrada1);

		sistema.registrarUsuario("Juan", "Pérez", "juan@mail.com", "1234");
		Usuario usuario = sistema.obtenerUsuario("juan@mail.com");
		
		sistema.registrarUsuario("Juan2", "Pérez2", "juan@mail.com.ar", "1234");
		Usuario usuario2 = sistema.obtenerUsuario("juan@mail.com.ar");
		// System.out.println(usuario);

		sistema.registrarEspectaculo("La Renga", "la_renga");
		Espectaculo espectaculo1 = sistema.obtenerEspectaculo("la_renga");
		// System.out.println(espectaculo1);

		LocalDate fechaFuncion = LocalDate.of(2025, 6, 30);
		sistema.registrarFuncion("la_renga", "S001", fechaFuncion);
		System.out.println(espectaculo1);
		espectaculo1.mostrarFunciones();
		
		
		Funcion funcion = sistema.obtenerFuncion("la_renga", "S001", LocalDate.of(2025, 6, 30));
		sistema.venderEntradaGeneral(usuario, funcion, 2);
		usuario.mostrarEntradasUsuario();
	
		


	}

}
