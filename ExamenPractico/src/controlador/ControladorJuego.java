
//ROSITA
package controlador;

import modelo.Tablero;
import vista.VistaConsola;
import java.util.InputMismatchException; // Requerido para manejo de excepciones

/**
 * Gestiona el flujo de la aplicación, controlando la interacción entre Modelo y Vista.
 */
public class ControladorJuego {

    private Tablero modelo;
    private final VistaConsola vista;
    private boolean juegoActivo;

    public ControladorJuego(Tablero modelo, VistaConsola vista) {
        this.modelo = modelo;
        this.vista = vista;
        this.juegoActivo = true;
    }

    /**
     * Inicia el bucle principal del juego.
     */
    public void iniciarJuego() {
        vista.mostrarMensaje("¡Bienvenido al Buscaminas POO!");
        
        while (juegoActivo) {
            vista.mostrarTablero(modelo);
            
            String entrada = vista.pedirCoordenada();
            
            if (entrada.equals("S")) {
                // Lógica de guardar estado (Persistencia) - Requisito del examen
                vista.mostrarMensaje("Guardando y saliendo del juego...");
                // Aquí iría la llamada al método de serialización del Modelo
                juegoActivo = false;
                continue;
            }
            
            try {
                // 1. Procesa y valida la entrada de coordenadas
                int[] coords = procesarCoordenada(entrada);
                int fila = coords[0];
                int columna = coords[1];
                
                // 2. Llama a la lógica del Modelo y maneja el resultado/excepciones
                boolean continua = modelo.descubrir(fila, columna);
                
                if (!continua) {
                    // Derrota (mina seleccionada)
                    vista.mostrarMensaje("¡BOOM! Has seleccionado una mina. Juego Terminado.");
                    juegoActivo = false;
                    // Aquí se puede mostrar el tablero completo revelado
                    vista.mostrarTablero(modelo);
                } else if (modelo.esVictoria()) {
                    // Victoria
                    vista.mostrarMensaje("¡FELICIDADES! Has descubierto todas las casillas seguras.");
                    juegoActivo = false;
                }
                
            } catch (InputMismatchException e) {
                vista.mostrarMensaje("Error: Formato de entrada inválido. Use el formato LetraNúmero (Ej: A5).");
            } catch (ArrayIndexOutOfBoundsException e) {
                 // Manejo de índices fuera de límites
                vista.mostrarMensaje("Error: Coordenadas fuera de los límites del tablero (A-H, 1-10).");
            } catch (Exception e) {
                // Captura CasillaYaDescubiertaException y otras excepciones personalizadas
                vista.mostrarMensaje(e.getMessage());
            }
        }
        vista.mostrarMensaje("Gracias por jugar.");
    }

    /**
     * Convierte la entrada del usuario (Ej: A5) en índices de matriz (fila, columna).
     * @param input Entrada del usuario.
     * @return Array de enteros {fila, columna}.
     * @throws InputMismatchException Si el formato es incorrecto.
     * @throws ArrayIndexOutOfBoundsException Si las coordenadas están fuera del tablero.
     */
    private int[] procesarCoordenada(String input) throws InputMismatchException, ArrayIndexOutOfBoundsException {
        if (input == null || input.length() < 2) {
            throw new InputMismatchException();
        }

        // Obtener la letra (A-H) y convertirla en índice (0-7)
        char letra = input.charAt(0);
        int fila = letra - 'A';

        // Obtener la columna (1-10) y convertirla en índice (0-9)
        int columna;
        try {
            columna = Integer.parseInt(input.substring(1)) - 1;
        } catch (NumberFormatException e) {
            throw new InputMismatchException();
        }
        
        // Verificar límites para lanzar ArrayIndexOutOfBoundsException si es necesario
        if (fila < 0 || fila >= Tablero.FILAS || columna < 0 || columna >= Tablero.COLUMNAS) {
            throw new ArrayIndexOutOfBoundsException();
        }

        return new int[]{fila, columna};
    }
}