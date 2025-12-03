// ROSITA

package controlador;

import modelo.Tablero;
import vista.VistaConsola;
import java.util.InputMismatchException; // Para detectar formator incorrecto en la coordenada

/**
 * Gestiona el flujo principal del juego Buscaminas.
 * Hace de intermediario entre el Modelo (Tablero) y la Vista (Consola).
 */
public class ControladorJuego {

    private Tablero modelo;        // Contiene la lógica del buscaminas
    private final VistaConsola vista; // Interfaz que muestra mensajes y pide datos
    private boolean juegoActivo;      // Controla el ciclo principal

    public ControladorJuego(Tablero modelo, VistaConsola vista) {
        this.modelo = modelo;
        this.vista = vista;
        this.juegoActivo = true; // Cuando sea false, el juego termina
    }

    /**
     * Inicia el bucle principal del juego.
     */
    public void iniciarJuego() {
        vista.mostrarMensaje("¡Bienvenido al Buscaminas POO!");
        
        while (juegoActivo) { // Bucle principal
            vista.mostrarTablero(modelo); // Muestra el tablero actual
            
            String entrada = vista.pedirCoordenada(); // Pide algo como "A5"
            
            if (entrada.equals("S")) { // Acción para guardar el juego
                vista.mostrarMensaje("Guardando y saliendo del juego...");
                // Aquí iría la serialización para persistencia (examen)
                juegoActivo = false;
                continue;
            }
            
            try {
                // 1. Convertir la entrada en coordenadas válidas
                int[] coords = procesarCoordenada(entrada);
                int fila = coords[0];
                int columna = coords[1];
                
                // 2. El modelo intenta descubrir esa casilla
                boolean continua = modelo.descubrir(fila, columna);
                
                if (!continua) {
                    // El jugador perdió al seleccionar una mina
                    vista.mostrarMensaje("¡BOOM! Has seleccionado una mina. Juego Terminado.");
                    juegoActivo = false;
                    
                    // Muestra el tablero revelado
                    vista.mostrarTablero(modelo);
                } else if (modelo.esVictoria()) {
                    // Todas las casillas seguras fueron descubiertas
                    vista.mostrarMensaje("¡FELICIDADES! Has descubierto todas las casillas seguras.");
                    juegoActivo = false;
                }
                
            } catch (InputMismatchException e) {
                // Detecta formato incorrecto: ejemplo "ZZ", "A?", ""
                vista.mostrarMensaje("Error: Formato de entrada inválido. Use el formato LetraNúmero (Ej: A5).");
            } catch (ArrayIndexOutOfBoundsException e) {
                // Cuando la fila o columna no están dentro del rango permitido
                vista.mostrarMensaje("Error: Coordenadas fuera de los límites del tablero (A-H, 1-10).");
            } catch (Exception e) {
                // Captura cualquier excepción personalizada como CasillaYaDescubiertaException
                vista.mostrarMensaje(e.getMessage());
            }
        }
        vista.mostrarMensaje("Gracias por jugar.");
    }

    /**
     * Transforma una entrada tipo "A5" en índices de matriz (fila y columna).
     * @throws InputMismatchException si el formato no es válido
     * @throws ArrayIndexOutOfBoundsException si la coordenada no existe en el tablero
     */
    private int[] procesarCoordenada(String input) throws InputMismatchException, ArrayIndexOutOfBoundsException {
        
        // La entrada debe tener al menos una letra y un número
        if (input == null || input.length() < 2) {
            throw new InputMismatchException();
        }

        // Letra A-H → fila 0-7
        char letra = input.charAt(0);
        int fila = letra - 'A';

        // Número 1-10 → columna 0-9
        int columna;
        try {
            columna = Integer.parseInt(input.substring(1)) - 1;
        } catch (NumberFormatException e) {
            throw new InputMismatchException(); // No puso un número válido
        }
        
        // Validación de límites del tablero
        if (fila < 0 || fila >= Tablero.FILAS || columna < 0 || columna >= Tablero.COLUMNAS) {
            throw new ArrayIndexOutOfBoundsException();
        }

        return new int[]{fila, columna};
    }
}
