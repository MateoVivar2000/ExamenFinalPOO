//SIN DELEGACION1

import controlador.ControladorJuego;
import modelo.Tablero;
import vista.VistaConsola;

/**
 * Clase principal que arranca la aplicación.
 * Crea las instancias del Modelo, la Vista y el Controlador.
 */
public class Main {

    public static void main(String[] args) {
        
        // 1. Crear el Modelo (La lógica del juego)
        Tablero tablero = new Tablero(); 
        
        // 2. Crear la Vista (La interfaz de consola)
        VistaConsola vista = new VistaConsola();
        
        // 3. Crear el Controlador (Une el Modelo y la Vista)
        ControladorJuego controlador = new ControladorJuego(tablero, vista);
        
        // 4. Iniciar el juego
        controlador.iniciarJuego();
    }
}