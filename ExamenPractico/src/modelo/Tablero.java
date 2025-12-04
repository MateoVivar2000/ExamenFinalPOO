//TERMINADO 3/12/25
package modelo;

import java.io.Serializable;
import java.util.Random;

public class Tablero implements Serializable {
    private static final long serialVersionUID = 1L;

    // Tamaño del tablero y cantidad total de minas
    public static final int FILAS = 10;
    public static final int COLUMNAS = 10;
    public static final int NUM_MINAS = 10;

    private Casilla[][] casillas;
    private int casillasSegurasRestantes;

    public Tablero() {
        casillas = new Casilla[FILAS][COLUMNAS];
        // Cantidad de casillas que no tienen mina y deben descubrirse para ganar
        casillasSegurasRestantes = FILAS * COLUMNAS - NUM_MINAS;

        // Se crean todas las casillas del tablero
        for (int i = 0; i < FILAS; i++)
            for (int j = 0; j < COLUMNAS; j++)
                casillas[i][j] = new Casilla(i, j);

        colocarMinasAleatoriamente();
        calcularMinasAdyacentes();
    }

    // Coloca minas en posiciones aleatorias sin repetir
    private void colocarMinasAleatoriamente() {
        Random random = new Random();
        int minasColocadas = 0;

        while (minasColocadas < NUM_MINAS) {
            int fila = random.nextInt(FILAS);
            int columna = random.nextInt(COLUMNAS);

            // Solo coloca la mina si la casilla no tiene una
            if (!casillas[fila][columna].tieneMina()) {
                casillas[fila][columna].colocarMina();
                minasColocadas++;
            }
        }
    }

    // Calcula cuántas minas hay alrededor de cada casilla
    private void calcularMinasAdyacentes() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {

                if (casillas[i][j].tieneMina()) continue;

                int conteo = 0;

                // Recorre las 8 posiciones vecinas
                for (int di = -1; di <= 1; di++) {
                    for (int dj = -1; dj <= 1; dj++) {

                        if (di == 0 && dj == 0) continue; // omitir la casilla actual

                        int ni = i + di;
                        int nj = j + dj;

                        // Verifica que el vecino esté dentro del tablero
                        if (ni >= 0 && ni < FILAS && nj >= 0 && nj < COLUMNAS) {
                            if (casillas[ni][nj].tieneMina()) conteo++;
                        }
                    }
                }

                casillas[i][j].setMinasAdyacentes(conteo);
            }
        }
    }

    /**
     * Intenta descubrir una casilla.
     * Si tiene mina, se termina el juego. Si no, continúa.
     */
    public boolean descubrir(int fila, int columna) throws Exception {
        Casilla c = casillas[fila][columna];

        if (c.estaDescubierta()) throw new Exception("Casilla ya descubierta.");
        if (c.estaMarcada()) throw new Exception("La casilla está marcada. Desmárcala para descubrirla.");

        c.descubrir();

        // Si se descubrió una mina, fin del juego
        if (c.tieneMina()) {
            return false;
        }

        casillasSegurasRestantes--;

        // Si no hay minas alrededor, se expanden las casillas vecinas
        if (c.getMinasAdyacentes() == 0) {
            expandirCasillasVacias(fila, columna);
        }

        return true;
    }

    // Descubre automáticamente las casillas vecinas cuando no hay minas cerca
    private void expandirCasillasVacias(int fila, int columna) {
        for (int di = -1; di <= 1; di++) {
            for (int dj = -1; dj <= 1; dj++) {

                int ni = fila + di;
                int nj = columna + dj;

                if (ni >= 0 && ni < FILAS && nj >= 0 && nj < COLUMNAS) {

                    Casilla vecino = casillas[ni][nj];

                    if (!vecino.estaDescubierta() && !vecino.tieneMina() && !vecino.estaMarcada()) {
                        vecino.descubrir();
                        casillasSegurasRestantes--;

                        // Si también es una casilla sin minas alrededor, continúa expandiendo
                        if (vecino.getMinasAdyacentes() == 0) {
                            expandirCasillasVacias(ni, nj);
                        }
                    }
                }
            }
        }
    }

    // Marca o desmarca una casilla
    public void marcarCasilla(int fila, int columna) {
        casillas[fila][columna].marcar();
    }

    // Devuelve una casilla específica
    public Casilla getCasilla(int fila, int columna) {
        return casillas[fila][columna];
    }

    // Se gana cuando no quedan casillas seguras por descubrir
    public boolean esVictoria() {
        return casillasSegurasRestantes == 0;
    }

    // Revela todo el tablero (generalmente al perder)
    public void revelarTodo() {
        for (int i = 0; i < FILAS; i++)
            for (int j = 0; j < COLUMNAS; j++)
                casillas[i][j].descubrir();
    }
}
