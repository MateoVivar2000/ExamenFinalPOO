package vista;

import modelo.Tablero;
import java.util.Scanner;

/**
 * Clase que gestiona la interacción con el usuario a través de la consola.
 * Responsable de mostrar el estado del juego y leer las entradas.
 */
public class VistaConsola {

    private final Scanner scanner;

    public VistaConsola() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Muestra el tablero actual al usuario, incluyendo coordenadas.
     */
    public void mostrarTablero(Tablero tablero) {
        // Muestra las coordenadas de las columnas (1 a 10)
        System.out.println("\n    1 2 3 4 5 6 7 8 9 10");
        System.out.println("  -------------------------");

        // Itera sobre las filas
        for (int i = 0; i < Tablero.FILAS; i++) {
            // Convierte el índice de fila (0-7) a su letra correspondiente (A-H)
            char letraFila = (char) ('A' + i);
            System.out.print(letraFila + " | ");

            // Itera sobre las columnas
            for (int j = 0; j < Tablero.COLUMNAS; j++) {
                // Utiliza el método toString() de la clase Casilla para obtener su representación
                System.out.print(tablero.getCasilla(i, j).toString() + " ");
            }
            System.out.println("|");
        }
        System.out.println("  -------------------------");
    }

    /**
     * Pide al usuario que ingrese una coordenada (Ej: A5, C10).
     * @return La coordenada ingresada como String.
     */
    public String pedirCoordenada() {
        System.out.print("Ingrese coordenada (Ej: A5) o 'S' para guardar/salir: ");
        return scanner.nextLine().trim().toUpperCase();
    }
    
    /**
     * Muestra un mensaje al usuario (errores, victorias, derrotas).
     */
    public void mostrarMensaje(String mensaje) {
        System.out.println(">>> " + mensaje);
    }
}