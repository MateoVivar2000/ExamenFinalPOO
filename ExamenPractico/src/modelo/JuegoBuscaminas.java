//TERMINADO 03/10/2025
package modelo;

import java.io.*;

// Esta clase se encarga de todo lo del juego: el tablero, el estado del juego y guardar/cargar la partida
public class JuegoBuscaminas implements Serializable {

    private static final long serialVersionUID = 1L; // Para que Java no se queje al guardar/cargar objetos

    private Tablero tablero;           // Aquí guardo todas las casillas del juego
    private EstadoJuego estado;        // Guarda si el juego sigue, si gané o perdí
    private static final String ARCHIVO_GUARDADO = "buscaminas_guardado.bin"; // Nombre del archivo de guardado

    // Define los posibles estados del juego
    public enum EstadoJuego { EN_CURSO, VICTORIA, DERROTA }

    // Constructor: crea un tablero nuevo y marca que el juego está en curso
    public JuegoBuscaminas() {
        this.tablero = new Tablero(); // Creo todas las casillas del tablero
        this.estado = EstadoJuego.EN_CURSO; // Juego empezando
    }

    // Descubre una casilla. Devuelve true si sigo jugando, false si pise una mina
    public boolean descubrirCasilla(int fila, int columna) throws Exception {
        boolean continua = tablero.descubrir(fila, columna); // Le digo al tablero que descubra la casilla

        if (!continua) {
            this.estado = EstadoJuego.DERROTA; // Si era mina, el juego termina.
            return false;
        }

        if (tablero.esVictoria()) {
            this.estado = EstadoJuego.VICTORIA; // Si descubre todas las seguras, gané
        }

        return true; // Si no pasó nada grave, sigo jugando
    }

    // Marca o desmarca una casilla con bandera (flag)
    public void marcarCasilla(int fila, int columna) {
        tablero.getCasilla(fila, columna).marcar(); // Cambio el estado de marcado de la casilla
    }

    // Devuelve el tablero completo para poder mostrarlo
    public Tablero getTablero() { return tablero; }

    // Devuelve el estado actual del juego para saber si sigo jugando, gané o perdí
    public EstadoJuego getEstado() { return estado; }

    // Devuelve true si el juego sigue en curso
    public boolean estaActivo() { return estado == EstadoJuego.EN_CURSO; }

    // Guarda la partida actual en un archivo
    public void guardarJuego() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_GUARDADO))) {
            oos.writeObject(this); // Guardo todo el juego tal cual está
            System.out.println("Partida guardada en: " + ARCHIVO_GUARDADO);
        } catch (IOException e) {
            System.err.println("Error al guardar la partida: " + e.getMessage());
        }
    }

    // Carga la partida guardada, o crea una nueva si no existe
    public static JuegoBuscaminas cargarJuego() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO_GUARDADO))) {
            JuegoBuscaminas j = (JuegoBuscaminas) ois.readObject(); // Leo el archivo y recupero el juego
            System.out.println("Partida cargada desde: " + ARCHIVO_GUARDADO);
            return j;
        } catch (FileNotFoundException e) {
            System.out.println("No encontré partida guardada, inicio un juego nuevo.");
            return new JuegoBuscaminas(); // Si no hay archivo, creo un juego nuevo
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("No se pudo cargar la partida, inicio un juego nuevo: " + e.getMessage());
            return new JuegoBuscaminas(); // Si hay error, creo un juego nuevo
        }
    }
}