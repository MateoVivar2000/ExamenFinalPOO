//SIN DELEGACION1

package main; // paquete distinto del nombre de la clase

import controlador.ControladorJuego;
import modelo.JuegoBuscaminas;
import vista.VistaConsola;

public class Main {
    public static void main(String[] args) {
        // Crear un juego nuevo
        JuegoBuscaminas juego = new JuegoBuscaminas();

        VistaConsola vista = new VistaConsola();
        ControladorJuego controlador = new ControladorJuego(juego, vista);

        controlador.iniciarJuego();
    }
}
