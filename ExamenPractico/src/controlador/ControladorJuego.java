//EN PROCESO
package controlador;

import modelo.JuegoBuscaminas;
import vista.VistaConsola;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ControladorJuego {

    // Atributos principales del controlador
    private JuegoBuscaminas juego;   // Lógica del juego
    private final VistaConsola vista; // Interfaz de usuario
    private final Scanner scanner;    // Para leer la entrada del usuario

    // Constructor
    public ControladorJuego(JuegoBuscaminas juego, VistaConsola vista) {
        this.juego = juego;
        this.vista = vista;
        this.scanner = new Scanner(System.in); // Inicializa Scanner
    }

    // Método principal que inicia el juego
    public void iniciarJuego() {
        boolean jugarOtraVez = true; // Controla si el usuario quiere jugar otra partida

        while (jugarOtraVez) {
            vista.mostrarMensaje("¡EXAMEN PRACTICO JUEGO BUSCA MINAS!");

            // Bucle del turno de cada juego
            while (juego.estaActivo()) {
                vista.mostrarTablero(juego.getTablero());       // Muestra el tablero
                String entrada = vista.pedirCoordenada();       // Pide coordenadas al usuario


                try {
                    int[] coords = procesarCoordenada(entrada); // Convierte entrada a [fila, columna]
                    int fila = coords[0];
                    int columna = coords[1];

                    // Validar si la casilla ya está descubierta
                    if (juego.getTablero().getCasilla(fila, columna).estaDescubierta()) {
                        vista.mostrarMensaje("Esta casilla ya está descubierta. Escoge otra.");
                        continue; // vuelve a pedir coordenada
                    }

                    // Descubre la casilla y revisa el estado del juego
                    boolean sigue = juego.descubrirCasilla(fila, columna);

                    if (!sigue) { // Pisó una mina
                        vista.mostrarMensaje("¡BOOM! Pisaste una mina. Juego Terminado.");
                        vista.mostrarTablero(juego.getTablero());
                        break;
                    }

                    if (juego.getEstado() == JuegoBuscaminas.EstadoJuego.VICTORIA) { // Ganó
                        vista.mostrarMensaje("¡FELICIDADES! Has ganado.");
                        vista.mostrarTablero(juego.getTablero());
                        break;
                    }

                } catch (InputMismatchException e) {
                    vista.mostrarMensaje("Error: Formato inválido. Ejemplo: A5");
                } catch (ArrayIndexOutOfBoundsException e) {
                    vista.mostrarMensaje("Error: Coordenadas fuera del tablero (A-J, 1-10)");
                } catch (Exception e) {
                    vista.mostrarMensaje("Error: " + e.getMessage());
                }
            }

            // Preguntar si quiere jugar otra vez
            System.out.print("¿QUIERES JUGAR OTRA VEZ? (S/N): ");
            String respuesta = scanner.nextLine().trim().toUpperCase();
            if (respuesta.equals("S")) {
                juego = new JuegoBuscaminas(); // Reinicia juego
            } else {
                vista.mostrarMensaje("GRACIAS POR JUGAR");
                jugarOtraVez = false; // Sale del bucle exterior
            }
        }
    }

    // Convierte entrada tipo "A5" a coordenadas [fila, columna]
    private int[] procesarCoordenada(String input) throws InputMismatchException, ArrayIndexOutOfBoundsException {
        if (input == null || input.length() < 2) {
            throw new InputMismatchException(); // Entrada muy corta
        }

        char letra = Character.toUpperCase(input.charAt(0)); // Convierte a mayúscula
        int fila = letra - 'A'; // A-J → 0-9
        int columna;

        try {
            columna = Integer.parseInt(input.substring(1)) - 1; // 1-10 → 0-9
        } catch (NumberFormatException e) {
            throw new InputMismatchException(); // No es un número válido
        }

        if (fila < 0 || fila >= 10 || columna < 0 || columna >= 10) {
            throw new ArrayIndexOutOfBoundsException(); // Fuera del tablero
        }

        return new int[]{fila, columna};
    }
}
